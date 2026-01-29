package com.agora.challenge.notification.push.providers;

import com.agora.challenge.notification.push.PushProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Implementación concreta para Apple Push Notification Service (APNS)
 */
public class AppleProvider implements PushProvider {
    private static final Logger logger = LoggerFactory.getLogger(AppleProvider.class);

    private String authKey;
    private String keyId;
    private String teamId;
    private String bundleId;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Simular envío real
        logger.info("APNS: Enviando notificación push al dispositivo {}: {}",
                metadata.get("deviceToken"),
                message);

        // Simular éxito
        return true;
    }

    @Override
    public String getProviderName() {
        return "AppleAPNS";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.authKey = (String) config.get("authKey");
        this.keyId = (String) config.get("keyId");
        this.teamId = (String) config.get("teamId");
        this.bundleId = (String) config.get("bundleId");

        if (authKey == null || keyId == null || teamId == null || bundleId == null) {
            throw new IllegalArgumentException("APNS requires authKey, keyId, teamId and bundleId in configuration");
        }

        logger.info("APNS configurado para bundle: {}", bundleId);
    }
}