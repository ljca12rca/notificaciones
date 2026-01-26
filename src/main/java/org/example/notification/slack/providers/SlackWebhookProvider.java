package org.example.notification.slack.providers;

import org.example.notification.slack.SlackProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Implementación concreta para Slack usando Webhooks
 */
public class SlackWebhookProvider implements SlackProvider {
    private static final Logger logger = LoggerFactory.getLogger(SlackWebhookProvider.class);

    private String webhookUrl;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Simular envío real
        logger.info("Slack: Enviando mensaje al canal {}: {}",
                metadata.get("channel"),
                message);

        // Simular éxito
        return true;
    }

    @Override
    public String getProviderName() {
        return "SlackWebhook";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.webhookUrl = (String) config.get("webhookUrl");

        if (webhookUrl == null) {
            throw new IllegalArgumentException("SlackWebhook requires webhookUrl in configuration");
        }

        logger.info("SlackWebhook configurado con URL: {}", webhookUrl);
    }
}