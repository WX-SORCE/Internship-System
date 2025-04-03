package com.alxy.notificationservice.service;

import com.alxy.notificationservice.entity.Notification;


import java.util.List;

public interface NotificationService {
    void saveNotification(String clientId, String type, String content);

    List<Notification> getNotifications(String clientId, String type);

    Notification markNotificationAsRead(String notificationId);
}