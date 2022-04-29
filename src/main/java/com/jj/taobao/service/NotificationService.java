package com.jj.taobao.service;

import com.jj.taobao.dto.NotificationDto;
import com.jj.taobao.enums.NotificationStatusEnum;
import com.jj.taobao.enums.NotificationTypeEnum;
import com.jj.taobao.exception.CustomizeErrorCode;
import com.jj.taobao.exception.CustomizeException;
import com.jj.taobao.mapper.NotificationMapper;
import com.jj.taobao.mapper.UserMapper;
import com.jj.taobao.model.Notification;
import com.jj.taobao.model.NotificationExample;
import com.jj.taobao.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public List<NotificationDto> list(List<Notification> notifications) {
        List<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDtos.add(notificationDto);
        }
        return notificationDtos;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }

}
