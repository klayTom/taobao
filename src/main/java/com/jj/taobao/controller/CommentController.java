package com.jj.taobao.controller;

import com.jj.taobao.dto.CommentCreateDto;
import com.jj.taobao.dto.CommentDto;
import com.jj.taobao.dto.ResultDto;
import com.jj.taobao.enums.CommentTypeEnum;
import com.jj.taobao.exception.CustomizeErrorCode;
import com.jj.taobao.model.Comment;
import com.jj.taobao.model.User;
import com.jj.taobao.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post (@RequestBody CommentCreateDto commentCreateDto,
                        HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if ( user==null ) {
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())) {
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setParentId(commentCreateDto.getParentId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setCommentCount(0);
        commentService.insert(comment,user);
        return ResultDto.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comment (@PathVariable(name = "id") Long id) {
        List<CommentDto> commentDtos = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDto.okOf(commentDtos);
    }

}
