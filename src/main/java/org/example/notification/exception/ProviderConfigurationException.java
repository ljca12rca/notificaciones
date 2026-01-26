package org.example.notification.exception;

/**
 * Excepción para errores de configuración de proveedores
 */
public class ProviderConfigurationException extends NotificationException {
    public ProviderConfigurationException(String message) {
        super(message);
    }

    public ProviderConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}