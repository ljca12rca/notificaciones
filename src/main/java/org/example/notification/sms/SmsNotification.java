package org.example.notification.sms;

import org.example.notification.Notification;
import org.example.notification.NotificationChannel;

import java.util.Map;

/**
 * Implementación concreta de notificación por SMS
 */
public class SmsNotification implements Notification {
    private final SmsProvider provider;

    public SmsNotification(SmsProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Validar metadatos básicos para SMS
        if (metadata == null || !metadata.containsKey("phoneNumber")) {
            throw new IllegalArgumentException("SMS notification requires 'phoneNumber' in metadata");
        }

        return provider.send(message, metadata);
    }

    @Override
    public String getChannelType() {
        return NotificationChannel.SMS.getChannelName();
    }
}