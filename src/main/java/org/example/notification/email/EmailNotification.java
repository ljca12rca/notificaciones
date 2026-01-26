package org.example.notification.email;

import org.example.notification.Notification;
import org.example.notification.NotificationChannel;

import java.util.Map;

/**
 * Implementación concreta de notificación por email
 */
public class EmailNotification implements Notification {
    private final EmailProvider provider;

    public EmailNotification(EmailProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Validar metadatos básicos para email
        if (metadata == null || !metadata.containsKey("to") || !metadata.containsKey("subject")) {
            throw new IllegalArgumentException("Email notification requires 'to' and 'subject' in metadata");
        }

        return provider.send(message, metadata);
    }

    @Override
    public String getChannelType() {
        return NotificationChannel.EMAIL.getChannelName();
    }
}