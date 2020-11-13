package com.social.app.service;

import com.social.app.entity.Notification;
import com.social.app.model.NotificationDto;
import com.social.app.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = "queue")
    public void handleMessage(NotificationDto notificationDto) {
        Notification notification = new Notification(notificationDto.getMessage());
        log.info("received a notification:" + notification.toString());
        notificationRepository.save(notification);
    }
}
