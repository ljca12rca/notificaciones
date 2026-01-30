package com.agora.challenge.notification.slack;

import com.agora.challenge.notification.Channel;
import com.agora.challenge.notification.TypeChannel;

import java.util.Map;

/**
 * Implementación concreta de notificación por Slack
 */
public class SlackChannel implements Channel {
    private final SlackProvider provider;

    public SlackChannel(SlackProvider provider) {
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
        return TypeChannel.SLACK.getChannelName();
    }
}