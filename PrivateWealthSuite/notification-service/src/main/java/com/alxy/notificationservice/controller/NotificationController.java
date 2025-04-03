package com.alxy.notificationservice.controller;

import com.alxy.notificationservice.entity.Notification;
import com.alxy.notificationservice.repository.NotificationRepository;
import com.alxy.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 通知控制器
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 根据客户ID获取通知列表
     * @param clientId 客户ID
     * @return 通知列表
     */
    @GetMapping("/{clientId}")
    public List<Notification> getNotificationsByClientId(@PathVariable String clientId, String type) {
        return notificationService.getNotifications(clientId, type);
    }

    /**
     * 标记通知为已读
     * @param notificationId 通知ID
     * @return 更新后的通知
     */
    @PutMapping("/{notificationId}/read")
    public Notification markNotificationAsRead(@PathVariable String notificationId) {
        return notificationService.markNotificationAsRead(notificationId);
    }
}