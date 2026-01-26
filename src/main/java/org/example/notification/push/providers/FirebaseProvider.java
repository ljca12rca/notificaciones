package org.example.notification.push.providers;

import org.example.notification.push.PushProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Implementación concreta para Firebase Cloud Messaging (FCM)
 */
public class FirebaseProvider implements PushProvider {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseProvider.class);

    private String apiKey;
    private String projectId;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        // Simular envío real
        logger.info("Firebase: Enviando notificación push al dispositivo {}: {}",
                metadata.get("deviceToken"),
                message);

        // Simular éxito
        return true;
    }

    @Override
    public String getProviderName() {
        return "FirebaseFCM";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.apiKey = (String) config.get("apiKey");
        this.projectId = (String) config.get("projectId");

        if (apiKey == null || projectId == null) {
            throw new IllegalArgumentException("Firebase requires apiKey and projectId in configuration");
        }

        logger.info("Firebase configurado para proyecto: {}", projectId);
    }
}