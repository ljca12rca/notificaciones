package org.example.notification;

import org.example.notification.config.NotificationConfig;
import org.example.notification.email.providers.SendGridProvider;
import org.example.notification.slack.providers.SlackWebhookProvider;
import org.example.notification.sms.providers.TwilioProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationManagerTest {

    @Mock
    private SendGridProvider mockSendGridProvider;

    @Mock
    private SlackWebhookProvider mockSlackProvider;

    @Mock
    private TwilioProvider mockTwilioProvider;

    private NotificationManager notificationManager;

    @BeforeEach
    void setUp() {
        NotificationConfig config = new NotificationConfig();

        // Configurar proveedores mock
        config.registerProvider(NotificationChannel.EMAIL, SendGridProvider.class);
        config.registerProvider(NotificationChannel.SLACK, SlackWebhookProvider.class);
        config.registerProvider(NotificationChannel.SMS, TwilioProvider.class);

        // Configurar canales
        Map<String, Object> emailConfig = new HashMap<>();
        emailConfig.put("apiKey", "test-api-key");
        emailConfig.put("fromEmail", "test@example.com");
        config.configureChannel(NotificationChannel.EMAIL, emailConfig);

        Map<String, Object> slackConfig = new HashMap<>();
        slackConfig.put("webhookUrl", "https://hooks.slack.com/services/test");
        config.configureChannel(NotificationChannel.SLACK, slackConfig);

        Map<String, Object> smsConfig = new HashMap<>();
        smsConfig.put("accountSid", "test-sid");
        smsConfig.put("authToken", "test-token");
        smsConfig.put("fromNumber", "+1234567890");
        config.configureChannel(NotificationChannel.SMS, smsConfig);

        notificationManager = new NotificationManager(config);
    }

    @Test
    void testSendEmailNotification() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("to", "recipient@example.com");
        metadata.put("subject", "Test Subject");

        boolean result = notificationManager.sendNotification("email", "Test message", metadata);

        assertTrue(result);
    }

    @Test
    void testSendSlackNotification() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("channel", "#general");

        boolean result = notificationManager.sendNotification("slack", "Test message", metadata);

        assertTrue(result);
    }

    @Test
    void testSendSmsNotification() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("phoneNumber", "+1234567890");

        boolean result = notificationManager.sendNotification("sms", "Test message", metadata);

        assertTrue(result);
    }

    @Test
    void testSendNotificationWithInvalidChannel() {
        Map<String, Object> metadata = new HashMap<>();

        boolean result = notificationManager.sendNotification("invalid_channel", "Test message", metadata);

        assertFalse(result);
    }

    @Test
    void testSendEmailNotificationWithMissingMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        // Falta 'to' y 'subject'

        boolean result = notificationManager.sendNotification("email", "Test message", metadata);

        assertFalse(result);
    }
}