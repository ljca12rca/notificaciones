package com.agora.challenge.notification.factory;

import com.agora.challenge.notification.Channel;
import com.agora.challenge.notification.NotificationProvider;
import com.agora.challenge.notification.TypeChannel;
import com.agora.challenge.notification.email.EmailChannel;
import com.agora.challenge.notification.email.EmailProvider;
import com.agora.challenge.notification.push.PushChannel;
import com.agora.challenge.notification.push.PushProvider;
import com.agora.challenge.notification.slack.SlackChannel;
import com.agora.challenge.notification.slack.SlackProvider;
import com.agora.challenge.notification.sms.SmsChannel;
import com.agora.challenge.notification.sms.SmsProvider;

import java.util.Map;

/**
 * Factory para crear notificadores basados en el canal y proveedor
 */
public class NotificationFactory {

    /**
     * Crea un notificador basado en el canal y proveedor
     * @param channelType Tipo de canal (email, slack, sms, etc.)
     * @param provider Proveedor específico para el canal
     * @return Instancia de Notification configurada
     */
    public static Channel createNotification(String channelType, NotificationProvider provider) {
        TypeChannel channel = TypeChannel.valueOf(channelType.toUpperCase());

        return switch (channel) {
            case EMAIL -> new EmailChannel((EmailProvider) provider);
            case SLACK -> new SlackChannel((SlackProvider) provider);
            case SMS -> new SmsChannel((SmsProvider) provider);
            case PUSH -> new PushChannel((PushProvider) provider);
            default -> throw new IllegalArgumentException("Unsupported channel type: " + channelType);
        };
    }

    /**
     * Crea un notificador con configuración automática del proveedor
     * @param channelType Tipo de canal
     * @param providerClass Clase del proveedor
     * @param providerConfig Configuración para el proveedor
     * @return Instancia de Notification configurada
     */
    public static Channel createNotification(String channelType, Class<? extends NotificationProvider> providerClass, Map<String, Object> providerConfig) {
        try {
            NotificationProvider provider = providerClass.getDeclaredConstructor().newInstance();
            provider.configure(providerConfig);
            return createNotification(channelType, provider);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create notification provider: " + e.getMessage(), e);
        }
    }
}