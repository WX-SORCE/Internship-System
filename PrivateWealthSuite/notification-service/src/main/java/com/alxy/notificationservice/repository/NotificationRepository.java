package com.alxy.notificationservice.repository;

import com.alxy.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    /**
     *
     * @param clientId
     * @return
     */
    List<Notification> findByClientId(String clientId);

    /**
     *
     * @param clientId
     * @param type
     * @return
     */
    List<Notification> findByClientIdAndType(String clientId, String type);

}