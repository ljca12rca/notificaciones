package org.example.notification.slack;

import org.example.notification.Notification;
import org.example.notification.NotificationChannel;

import java.util.Map;

/**
 * Implementación concreta de notificación por Slack
 */
public class SlackNotification implements Notification {
    private final SlackProvider provider;

    public SlackNotification(SlackProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Validar metadatos básicos para Slack
        if (metadata == null || !metadata.containsKey("channel")) {
            throw new IllegalArgumentException("Slack notification requires 'channel' in metadata");
        }

        return provider.send(message, metadata);
    }

    @Override
    public String getChannelType() {
        return NotificationChannel.SLACK.getChannelName();
    }
}