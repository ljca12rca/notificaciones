package org.example.notification.exception;

/**
 * Excepción para errores al enviar notificaciones
 */
public class NotificationSendException extends NotificationException {
    public NotificationSendException(String message) {
        super(message);
    }

    public NotificationSendException(String message, Throwable cause) {
        super(message, cause);
    }
}