package com.agora.challenge.notification;

/**
 * Enumeración de los tipos de canales de notificación soportados
 */
public enum TypeChannel {
    EMAIL("email"),
    SLACK("slack"),
    SMS("sms"),
    PUSH("push");

    private final String channelName;

    TypeChannel(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    @Override
    public String toString() {
        return channelName;
    }
}