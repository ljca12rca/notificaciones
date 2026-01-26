package org.example.notification.exception;

/**
 * Excepción base para errores de notificación
 */
public class NotificationException extends RuntimeException {
    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }
}