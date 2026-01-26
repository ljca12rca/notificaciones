package org.example.notification;

/**
 * Enumeración de los tipos de canales de notificación soportados
 */
public enum NotificationChannel {
    EMAIL("email"),
    SLACK("slack"),
    SMS("sms"),
    PUSH("push");

    private final String channelName;

    NotificationChannel(String channelName) {
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