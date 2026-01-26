package org.example.notification;

import org.example.notification.config.NotificationConfig;
import org.example.notification.factory.NotificationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase principal para gestionar notificaciones
 * Proporciona una interfaz unificada para enviar notificaciones a través de diferentes canales
 */
public class NotificationManager {
    private static final Logger logger = LoggerFactory.getLogger(NotificationManager.class);

    private final NotificationConfig config;
    private final Map<String, Notification> notificationCache;

    public NotificationManager(NotificationConfig config) {
        this.config = config;
        this.notificationCache = new HashMap<>();
    }

    /**
     * Envía una notificación a través del canal especificado
     * @param channelType Tipo de canal (email, slack, sms, etc.)
     * @param message Mensaje a enviar
     * @param metadata Metadatos específicos del canal
     * @return true si la notificación fue enviada exitosamente
     */
    public boolean sendNotification(String channelType, String message, Map<String, Object> metadata) {
        try {
            Notification notification = getOrCreateNotification(channelType);
            return notification.send(message, metadata);
        } catch (Exception e) {
            logger.error("Error al enviar notificación por {}: {}", channelType, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Obtiene o crea una notificación del cache
     */
    private Notification getOrCreateNotification(String channelType) {
        return notificationCache.computeIfAbsent(channelType, key -> {
            NotificationChannel channel = NotificationChannel.valueOf(key.toUpperCase());
            Class<? extends NotificationProvider> providerClass = config.getProviderClass(channel);
            Map<String, Object> channelConfig = config.getChannelConfiguration(channel);

            if (providerClass == null) {
                throw new IllegalStateException("No provider registered for channel: " + channelType);
            }

            if (channelConfig == null) {
                throw new IllegalStateException("No configuration found for channel: " + channelType);
            }

            return NotificationFactory.createNotification(channelType, providerClass, channelConfig);
        });
    }

    /**
     * Cierra recursos y limpia el cache
     */
    public void shutdown() {
        notificationCache.clear();
        logger.info("NotificationManager shutdown completed");
    }
}