package org.example.notification.factory;

import org.example.notification.Notification;
import org.example.notification.email.EmailNotification;
import org.example.notification.email.providers.SendGridProvider;
import org.example.notification.slack.SlackNotification;
import org.example.notification.slack.providers.SlackWebhookProvider;
import org.example.notification.sms.SmsNotification;
import org.example.notification.sms.providers.TwilioProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationFactoryTest {

    @Mock
    private SendGridProvider mockSendGridProvider;

    @Mock
    private SlackWebhookProvider mockSlackProvider;

    @Mock
    private TwilioProvider mockTwilioProvider;

    @Test
    void testCreateEmailNotification() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", "test-api-key");
        config.put("fromEmail", "test@example.com");

        Notification notification = NotificationFactory.createNotification(
                "email",
                SendGridProvider.class,
                config
        );

        assertInstanceOf(EmailNotification.class, notification);
        assertEquals("email", notification.getChannelType());
    }

    @Test
    void testCreateSlackNotification() {
        Map<String, Object> config = new HashMap<>();
        config.put("webhookUrl", "https://hooks.slack.com/services/test");

        Notification notification = NotificationFactory.createNotification(
                "slack",
                SlackWebhookProvider.class,
                config
        );

        assertInstanceOf(SlackNotification.class, notification);
        assertEquals("slack", notification.getChannelType());
    }

    @Test
    void testCreateSmsNotification() {
        Map<String, Object> config = new HashMap<>();
        config.put("accountSid", "test-sid");
        config.put("authToken", "test-token");
        config.put("fromNumber", "+1234567890");

        Notification notification = NotificationFactory.createNotification(
                "sms",
                TwilioProvider.class,
                config
        );

        assertInstanceOf(SmsNotification.class, notification);
        assertEquals("sms", notification.getChannelType());
    }

    @Test
    void testCreateNotificationWithInvalidChannel() {
        Map<String, Object> config = new HashMap<>();

        assertThrows(RuntimeException.class, () -> {
            NotificationFactory.createNotification(
                    "invalid_channel",
                    SendGridProvider.class,
                    config
            );
        });
    }
}