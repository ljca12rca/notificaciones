package org.example.notification.sms.providers;

import org.example.notification.sms.SmsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Implementación concreta para Twilio
 */
public class TwilioProvider implements SmsProvider {
    private static final Logger logger = LoggerFactory.getLogger(TwilioProvider.class);

    private String accountSid;
    private String authToken;
    private String fromNumber;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Simular envío real
        logger.info("Twilio: Enviando SMS de {} a {}: {}",
                fromNumber,
                metadata.get("phoneNumber"),
                message);

        // Simular éxito
        return true;
    }

    @Override
    public String getProviderName() {
        return "Twilio";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.accountSid = (String) config.get("accountSid");
        this.authToken = (String) config.get("authToken");
        this.fromNumber = (String) config.get("fromNumber");

        if (accountSid == null || authToken == null || fromNumber == null) {
            throw new IllegalArgumentException("Twilio requires accountSid, authToken and fromNumber in configuration");
        }

        logger.info("Twilio configurado con número: {}", fromNumber);
    }
}