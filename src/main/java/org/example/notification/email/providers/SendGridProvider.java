package org.example.notification.email.providers;

import org.example.notification.email.EmailProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Implementación concreta para SendGrid
 */
public class SendGridProvider implements EmailProvider {
    private static final Logger logger = LoggerFactory.getLogger(SendGridProvider.class);

    private String apiKey;
    private String fromEmail;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Simular envío real
        logger.info("SendGrid: Enviando email de {} a {} con asunto: {}",
                fromEmail,
                metadata.get("to"),
                metadata.get("subject"));

        logger.info("Contenido: {}", message);

        // Simular éxito
        return true;
    }

    @Override
    public String getProviderName() {
        return "SendGrid";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.apiKey = (String) config.get("apiKey");
        this.fromEmail = (String) config.get("fromEmail");

        if (apiKey == null || fromEmail == null) {
            throw new IllegalArgumentException("SendGrid requires apiKey and fromEmail in configuration");
        }

        logger.info("SendGrid configurado con API Key: {} y email: {}", apiKey, fromEmail);
    }
}