package com.agora.challenge.notification;

import com.agora.challenge.notification.config.ChannelConfig;
import com.agora.challenge.notification.email.providers.SendGridProvider;
import com.agora.challenge.notification.slack.providers.SlackWebhookProvider;
import com.agora.challenge.notification.sms.providers.TwilioProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
        ChannelConfig config = new ChannelConfig();

        // Configurar proveedores mock
        config.registerProvider(TypeChannel.EMAIL, SendGridProvider.class);
        config.registerProvider(TypeChannel.SLACK, SlackWebhookProvider.class);
        config.registerProvider(TypeChannel.SMS, TwilioProvider.class);

        // Configurar canales
        Map<String, Object> emailConfig = new HashMap<>();
        emailConfig.put("apiKey", "test-api-key");
        emailConfig.put("fromEmail", "test@example.com");
        config.configureChannel(TypeChannel.EMAIL, emailConfig);

        Map<String, Object> slackConfig = new HashMap<>();
        slackConfig.put("webhookUrl", "https://hooks.slack.com/services/test");
        config.configureChannel(TypeChannel.SLACK, slackConfig);

        Map<String, Object> smsConfig = new HashMap<>();
        smsConfig.put("accountSid", "test-sid");
        smsConfig.put("authToken", "test-token");
        smsConfig.put("fromNumber", "+1234567890");
        config.configureChannel(TypeChannel.SMS, smsConfig);

        notificationManager = new NotificationManager(config);
    }

    @Test
    void testSendEmailChannel() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("to", "recipient@example.com");
        metadata.put("subject", "Test Subject");

        boolean result = notificationManager.sendNotification("email", "Test message", metadata);

        assertTrue(result);
    }

    @Test
    void testSendSlackChannel() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("channel", "#general");

        boolean result = notificationManager.sendNotification("slack", "Test message", metadata);

        assertTrue(result);
    }

    @Test
    void testSendSmsChannel() {
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
    void testSendEmailChannelWithMissingMetadata() {
        Map<String, Object> metadata = new HashMap<>();
        // Falta 'to' y 'subject'

        boolean result = notificationManager.sendNotification("email", "Test message", metadata);

        assertFalse(result);
    }
}