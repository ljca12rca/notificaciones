package org.example.notification.email;

import org.example.notification.NotificationProvider;

import java.util.Map;

/**
 * Interfaz para proveedores de email (SendGrid, Mailgun, etc.)
 */
public interface EmailProvider extends NotificationProvider {
    // Métodos específicos de email pueden ser añadidos aquí en el futuro
}