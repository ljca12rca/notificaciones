package com.agora.challenge.notification.email;

import com.agora.challenge.notification.Channel;
import com.agora.challenge.notification.TypeChannel;

import java.util.Map;

/**
 * Implementación concreta de notificación por email
 */
public class EmailNotification implements Channel {
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
        return TypeChannel.EMAIL.getChannelName();
    }
}