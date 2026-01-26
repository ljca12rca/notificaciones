package org.example;

import org.example.notification.*;
import org.example.notification.config.NotificationConfig;
import org.example.notification.email.providers.SendGridProvider;
import org.example.notification.slack.providers.SlackWebhookProvider;
import org.example.notification.sms.providers.TwilioProvider;
import org.example.notification.push.providers.FirebaseProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demo: Librería de Notificaciones ===");

        // 1. Configurar la librería
        NotificationConfig config = new NotificationConfig();

        // Registrar proveedores
        config.registerProvider(NotificationChannel.EMAIL, SendGridProvider.class);
        config.registerProvider(NotificationChannel.SLACK, SlackWebhookProvider.class);
        config.registerProvider(NotificationChannel.SMS, TwilioProvider.class);
        config.registerProvider(NotificationChannel.PUSH, FirebaseProvider.class);

        // Configurar cada canal
        Map<String, Object> emailConfig = new HashMap<>();
        emailConfig.put("apiKey", "sendgrid-api-key-demo");
        emailConfig.put("fromEmail", "notifications@example.com");
        config.configureChannel(NotificationChannel.EMAIL, emailConfig);

        Map<String, Object> slackConfig = new HashMap<>();
        slackConfig.put("webhookUrl", "https://hooks.slack.com/services/demo");
        config.configureChannel(NotificationChannel.SLACK, slackConfig);

        Map<String, Object> smsConfig = new HashMap<>();
        smsConfig.put("accountSid", "twilio-account-sid-demo");
        smsConfig.put("authToken", "twilio-auth-token-demo");
        smsConfig.put("fromNumber", "+1234567890");
        config.configureChannel(NotificationChannel.SMS, smsConfig);

        Map<String, Object> pushConfig = new HashMap<>();
        pushConfig.put("apiKey", "firebase-api-key-demo");
        pushConfig.put("projectId", "my-firebase-project");
        config.configureChannel(NotificationChannel.PUSH, pushConfig);

        // Crear el manager
        NotificationManager notificationManager = new NotificationManager(config);

        // 2. Enviar notificaciones por diferentes canales
        System.out.println("\nEnviando notificaciones...");

        // Enviar email
        Map<String, Object> emailMetadata = new HashMap<>();
        emailMetadata.put("to", "user@example.com");
        emailMetadata.put("subject", "Bienvenido a nuestra plataforma");
        boolean emailSent = notificationManager.sendNotification("email",
                "Hola, este es un mensaje de bienvenida a nuestra plataforma.", emailMetadata);
        System.out.println("Email enviado: " + emailSent);

        // Enviar mensaje a Slack
        Map<String, Object> slackMetadata = new HashMap<>();
        slackMetadata.put("channel", "#general");
        boolean slackSent = notificationManager.sendNotification("slack",
                "Nuevo usuario registrado en la plataforma!", slackMetadata);
        System.out.println("Mensaje a Slack enviado: " + slackSent);

        // Enviar SMS
        Map<String, Object> smsMetadata = new HashMap<>();
        smsMetadata.put("phoneNumber", "+573001234567");
        boolean smsSent = notificationManager.sendNotification("sms",
                "Tu código de verificación es: 123456", smsMetadata);
        System.out.println("SMS enviado: " + smsSent);

        // Enviar notificación push
        Map<String, Object> pushMetadata = new HashMap<>();
        pushMetadata.put("deviceToken", "device-token-demo-12345");
        boolean pushSent = notificationManager.sendNotification("push",
                "Tienes un nuevo mensaje importante!", pushMetadata);
        System.out.println("Notificación push enviada: " + pushSent);

        // 3. Demostrar cambio de proveedor (transparente para el usuario)
        System.out.println("\nCambiar proveedor de email a Mailgun...");

        // Cambiar proveedor de email
        config.registerProvider(NotificationChannel.EMAIL, org.example.notification.email.providers.MailgunProvider.class);

        Map<String, Object> mailgunConfig = new HashMap<>();
        mailgunConfig.put("apiKey", "mailgun-api-key-demo");
        mailgunConfig.put("domain", "example.com");
        mailgunConfig.put("fromEmail", "notifications@example.com");
        config.configureChannel(NotificationChannel.EMAIL, mailgunConfig);

        // El código para enviar email sigue siendo el mismo
        boolean emailSentWithMailgun = notificationManager.sendNotification("email",
                "Este mensaje se envía usando Mailgun en lugar de SendGrid", emailMetadata);
        System.out.println("Email con Mailgun enviado: " + emailSentWithMailgun);

        // 4. Cerrar recursos
        notificationManager.shutdown();
        System.out.println("\nDemo completada. Todos los recursos liberados.");
    }
}