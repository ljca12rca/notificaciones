package org.example.notification;

import java.util.Map;

/**
 * Interfaz base para notificaciones
 * Define el contrato para enviar notificaciones a través de diferentes canales
 */
public interface Notification {
    /**
     * Envía una notificación
     * @param message El mensaje a enviar
     * @param metadata Metadatos adicionales específicos del canal
     * @return true si la notificación fue enviada exitosamente, false en caso contrario
     */
    boolean send(String message, Map<String, Object> metadata);

    /**
     * @return El tipo de canal de notificación
     */
    String getChannelType();
}