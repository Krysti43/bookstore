package com.example.bookstore.strategy;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {

    @Override
    public String sendNotification(String message) {
        return "EMAIL: " + message;
    }
}