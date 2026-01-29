package com.agora.challenge.notification.email;

import com.agora.challenge.notification.email.providers.SendGridProvider;
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
class SendGridProviderTest {

    @Mock
    private SendGridProvider provider;

    @BeforeEach
    void setUp() {
        provider = new SendGridProvider();
    }

    @Test
    void testConfigureWithValidConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", "test-api-key");
        config.put("fromEmail", "test@example.com");

        assertDoesNotThrow(() -> provider.configure(config));
    }

    @Test
    void testConfigureWithMissingApiKey() {
        Map<String, Object> config = new HashMap<>();
        config.put("fromEmail", "test@example.com");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> provider.configure(config));

        assertTrue(exception.getMessage().contains("apiKey"));
    }

    @Test
    void testConfigureWithMissingFromEmail() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", "test-api-key");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> provider.configure(config));

        assertTrue(exception.getMessage().contains("fromEmail"));
    }

    @Test
    void testSendNotification() {
        Map<String, Object> config = new HashMap<>();
        config.put("apiKey", "test-api-key");
        config.put("fromEmail", "test@example.com");

        provider.configure(config);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("to", "recipient@example.com");
        metadata.put("subject", "Test Subject");

        // Mock the send method to return true
        SendGridProvider spyProvider = spy(provider);
        doReturn(true).when(spyProvider).send(anyString(), anyMap());

        boolean result = spyProvider.send("Test message", metadata);

        assertTrue(result);
    }

    @Test
    void testGetProviderName() {
        assertEquals("SendGrid", provider.getProviderName());
    }
}