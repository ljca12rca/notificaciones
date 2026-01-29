package com.agora.challenge.notification;

import java.util.Map;

/**
 * Interfaz para proveedores de notificaciones
 * Define el contrato para proveedores específicos (SendGrid, Mailgun, Twilio, etc.)
 */
public interface NotificationProvider {
    /**
     * Envía una notificación usando el proveedor específico
     * @param message El mensaje a enviar
     * @param metadata Metadatos adicionales específicos del proveedor
     * @return true si la notificación fue enviada exitosamente, false en caso contrario
     */
    boolean send(String message, Map<String, Object> metadata);

    /**
     * @return El nombre del proveedor
     */
    String getProviderName();

    /**
     * Configura el proveedor con credenciales y configuración específica
     * @param config Configuración específica del proveedor
     */
    void configure(Map<String, Object> config);
}