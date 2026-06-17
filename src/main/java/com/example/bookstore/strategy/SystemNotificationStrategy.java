package com.example.bookstore.strategy;

import org.springframework.stereotype.Component;

@Component
public class SystemNotificationStrategy implements NotificationStrategy {

    @Override
    public String sendNotification(String message) {
        return "SYSTEM: " + message;
    }
}