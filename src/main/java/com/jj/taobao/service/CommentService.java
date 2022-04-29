package com.jj.taobao.service;

import com.jj.taobao.dto.CommentDto;
import com.jj.taobao.enums.CommentTypeEnum;
import com.jj.taobao.enums.NotificationStatusEnum;
import com.jj.taobao.enums.NotificationTypeEnum;
import com.jj.taobao.exception.CustomizeErrorCode;
import com.jj.taobao.exception.CustomizeException;
import com.jj.taobao.mapper.*;
import com.jj.taobao.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductExtMapper productExtMapper;
    @Autowired
    UserMapper usermapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            // 回复商品
            Product product = productMapper.selectByPrimaryKey(dbComment.getParentId());
            if (product == null) {
                throw new CustomizeException(CustomizeErrorCode.Product_NOT_FOUND);
            }

            commentMapper.insert(comment);
            dbComment.setCommentCount(1);

            // 增加评论的回复数
            commentExtMapper.inCommentCount(dbComment);


            // 创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getUsername(), product.getTitle(), NotificationTypeEnum.REPLY_COMMENT, product.getId());
        } else {
            // 回复商品
            Product product = productMapper.selectByPrimaryKey(comment.getParentId());
            if (product == null) {
                throw new CustomizeException(CustomizeErrorCode.Product_NOT_FOUND);
            }

            comment.setCommentCount(0);
            commentMapper.insert(comment);
            product.setCommentCount(1);
            // 增加回复数
            productExtMapper.inCommentCount(product);
            // 创建通知
            createNotify(comment, product.getCreator(), commentator.getUsername(), product.getTitle(), NotificationTypeEnum.REPLY_PRODUCT, product.getId());
        }

    }

    public List<CommentDto> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");

        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        // 获取评论人并转换为map
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdIn(userIds);
        List<User> users = usermapper.selectByExample(example);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 community 为 commentDto
        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());

        return commentDtos;
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outTitle);
        notificationMapper.insert(notification);
    }


}
