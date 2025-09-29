package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(NotificationDTO notificationDTO);

    NotificationDTO getNotificationById(Long id);

    List<NotificationDTO> getAllNotifications();

    NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO);

    void deleteNotification(Long id);
}
