package com.agora.challenge.notification.email.providers;

import com.agora.challenge.notification.email.EmailProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Implementación concreta para Mailgun
 */
public class MailgunProvider implements EmailProvider {
    private static final Logger logger = LoggerFactory.getLogger(MailgunProvider.class);

    private String apiKey;
    private String domain;
    private String fromEmail;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Simular envío real
        logger.info("Mailgun: Enviando email de {} a {} con asunto: {}",
                fromEmail,
                metadata.get("to"),
                metadata.get("subject"));

        logger.info("Contenido: {}", message);

        // Simular éxito
        return true;
    }

    @Override
    public String getProviderName() {
        return "Mailgun";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.apiKey = (String) config.get("apiKey");
        this.domain = (String) config.get("domain");
        this.fromEmail = (String) config.get("fromEmail");

        if (apiKey == null || domain == null || fromEmail == null) {
            throw new IllegalArgumentException("Mailgun requires apiKey, domain and fromEmail in configuration");
        }

        logger.info("Mailgun configurado con dominio: {} y email: {}", domain, fromEmail);
    }
}