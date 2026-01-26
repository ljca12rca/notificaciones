package org.example.notification.factory;

import org.example.notification.*;
import org.example.notification.email.EmailNotification;
import org.example.notification.email.EmailProvider;
import org.example.notification.slack.SlackNotification;
import org.example.notification.slack.SlackProvider;
import org.example.notification.sms.SmsNotification;
import org.example.notification.sms.SmsProvider;
import org.example.notification.push.PushNotification;
import org.example.notification.push.PushProvider;

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
    public static Notification createNotification(String channelType, NotificationProvider provider) {
        NotificationChannel channel = NotificationChannel.valueOf(channelType.toUpperCase());

        return switch (channel) {
            case EMAIL -> new EmailNotification((EmailProvider) provider);
            case SLACK -> new SlackNotification((SlackProvider) provider);
            case SMS -> new SmsNotification((SmsProvider) provider);
            case PUSH -> new PushNotification((PushProvider) provider);
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
    public static Notification createNotification(String channelType, Class<? extends NotificationProvider> providerClass, Map<String, Object> providerConfig) {
        try {
            NotificationProvider provider = providerClass.getDeclaredConstructor().newInstance();
            provider.configure(providerConfig);
            return createNotification(channelType, provider);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create notification provider: " + e.getMessage(), e);
        }
    }
}