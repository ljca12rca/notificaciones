package com.agora.challenge.notification.config;

import com.agora.challenge.notification.TypeChannel;
import com.agora.challenge.notification.NotificationProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase de configuración para notificaciones
 * Permite configuración programática de proveedores y canales
 */
public class ChannelConfig {
    private final Map<TypeChannel, Class<? extends NotificationProvider>> channelProviders;
    private final Map<TypeChannel, Map<String, Object>> channelConfigurations;

    public ChannelConfig() {
        this.channelProviders = new HashMap<>();
        this.channelConfigurations = new HashMap<>();
    }

    /**
     * Registra un proveedor para un canal específico
     * @param channel Canal de notificación
     * @param providerClass Clase del proveedor
     */
    public void registerProvider(TypeChannel channel, Class<? extends NotificationProvider> providerClass) {
        channelProviders.put(channel, providerClass);
    }

    /**
     * Configura un canal con sus parámetros específicos
     * @param channel Canal de notificación
     * @param config Configuración específica del proveedor
     */
    public void configureChannel(TypeChannel channel, Map<String, Object> config) {
        channelConfigurations.put(channel, config);
    }

    /**
     * @param channel Canal de notificación
     * @return Clase del proveedor registrado para el canal
     */
    public Class<? extends NotificationProvider> getProviderClass(TypeChannel channel) {
        return channelProviders.get(channel);
    }

    /**
     * @param channel Canal de notificación
     * @return Configuración para el canal
     */
    public Map<String, Object> getChannelConfiguration(TypeChannel channel) {
        return channelConfigurations.get(channel);
    }

    /**
     * @return Todos los canales configurados
     */
    public Map<TypeChannel, Class<? extends NotificationProvider>> getAllProviders() {
        return new HashMap<>(channelProviders);
    }
}