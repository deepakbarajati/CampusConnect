package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(NotificationDTO notificationDTO);

    NotificationDTO getNotificationById(String id);

    List<NotificationDTO> getAllNotifications();

    NotificationDTO updateNotification(String id, NotificationDTO notificationDTO);

    void deleteNotification(String id);
}
