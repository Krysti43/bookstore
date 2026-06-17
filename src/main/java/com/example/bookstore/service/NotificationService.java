package com.example.bookstore.service;

import com.example.bookstore.strategy.NotificationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final List<NotificationStrategy> strategies;

    public NotificationService(List<NotificationStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<String> sendNotification(String message) {
        return strategies.stream()
                .map(strategy -> strategy.sendNotification(message))
                .toList();
    }
}