package org.example.notification.push;

import org.example.notification.Notification;
import org.example.notification.NotificationChannel;

import java.util.Map;

/**
 * Implementación concreta de notificación por Push
 */
public class PushNotification implements Notification {
    private final PushProvider provider;

    public PushNotification(PushProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Validar metadatos básicos para push
        if (metadata == null || !metadata.containsKey("deviceToken")) {
            throw new IllegalArgumentException("Push notification requires 'deviceToken' in metadata");
        }

        return provider.send(message, metadata);
    }

    @Override
    public String getChannelType() {
        return NotificationChannel.PUSH.getChannelName();
    }
}