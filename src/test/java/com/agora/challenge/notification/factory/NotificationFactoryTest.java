package com.agora.challenge.notification.factory;

import com.agora.challenge.notification.Channel;
import com.agora.challenge.notification.email.EmailChannel;
import com.agora.challenge.notification.email.providers.SendGridProvider;
import com.agora.challenge.notification.slack.SlackChannel;
import com.agora.challenge.notification.slack.providers.SlackWebhookProvider;
import com.agora.challenge.notification.sms.SmsChannel;
import com.agora.challenge.notification.sms.providers.TwilioProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationFactoryTest {

    @Mock
    private SendGridProvider mockSendGridProvider;

    @Mock
    private SlackWebhookProvider mockSlackProvider;

    @Mock
    private TwilioProvider mockTwilioProvider;

    @Test
    void testCreateEmailChannel() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", "test-api-key");
        config.put("fromEmail", "test@example.com");

        Channel channel = NotificationFactory.createNotification(
                "email",
                SendGridProvider.class,
                config
        );

        assertInstanceOf(EmailChannel.class, channel);
        assertEquals("email", channel.getChannelType());
    }

    @Test
    void testCreateSlackChannel() {
        Map<String, Object> config = new HashMap<>();
        config.put("webhookUrl", "https://hooks.slack.com/services/test");

        Channel channel = NotificationFactory.createNotification(
                "slack",
                SlackWebhookProvider.class,
                config
        );

        assertInstanceOf(SlackChannel.class, channel);
        assertEquals("slack", channel.getChannelType());
    }

    @Test
    void testCreateSmsChannel() {
        Map<String, Object> config = new HashMap<>();
        config.put("accountSid", "test-sid");
        config.put("authToken", "test-token");
        config.put("fromNumber", "+1234567890");

        Channel channel = NotificationFactory.createNotification(
                "sms",
                TwilioProvider.class,
                config
        );

        assertInstanceOf(SmsChannel.class, channel);
        assertEquals("sms", channel.getChannelType());
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