package com.campusConnect.chatService.service.impl;

import com.campusConnect.chatService.dto.NotificationDTO;
import com.campusConnect.chatService.document.Notification;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.NotificationRepository;
import com.campusConnect.chatService.service.NotificationService;
import com.campusConnect.chatService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final MapperUtils mapperUtils;

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        Notification notification = mapperUtils.map(notificationDTO, Notification.class);
        Notification saved = notificationRepository.save(notification);
        return mapperUtils.map(saved, NotificationDTO.class);
    }

    @Override
    public NotificationDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return mapperUtils.map(notification, NotificationDTO.class);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return mapperUtils.mapList(notifications, NotificationDTO.class);
    }

    @Override
    public NotificationDTO updateNotification(Long id, NotificationDTO notificationDTO) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        if (notificationDTO.getMessage() != null) notification.setMessage(notificationDTO.getMessage());
        if (notificationDTO.getType() != null) notification.setType(notificationDTO.getType());
        if (notificationDTO.getLink() != null) notification.setLink(notificationDTO.getLink());
        notification.setRead(notificationDTO.isRead());

        Notification updated = notificationRepository.save(notification);
        return mapperUtils.map(updated, NotificationDTO.class);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notificationRepository.delete(notification);
    }
}
