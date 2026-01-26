# Librería de Notificaciones Agnóstica de Framework

Una librería Java flexible y extensible para enviar notificaciones a través de múltiples canales (Email, Slack, SMS, etc.) con soporte para diferentes proveedores.

## Características

- **Agnóstica de Framework**: Diseñada para trabajar en cualquier proyecto Java, con o sin frameworks
- **Extensible**: Fácil de extender con nuevos canales y proveedores
- **Configuración Programática**: Todo se configura mediante código Java puro
- **Patrones de Diseño**: Factory, Strategy, y otros patrones para una arquitectura limpia
- **Manejo de Errores**: Sistema robusto de excepciones y logging
- **Testing**: Tests unitarios completos

## Arquitectura

La librería sigue una arquitectura basada en interfaces y patrones de diseño:

## Instalación

Agregue la dependencia a su proyecto Maven:

```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>notification-library</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Uso Básico

### 1. Configuración

```java
import org.example.notification.*;
import org.example.notification.config.NotificationConfig;
import org.example.notification.email.providers.SendGridProvider;
import org.example.notification.slack.providers.SlackWebhookProvider;
import org.example.notification.sms.providers.TwilioProvider;

import java.util.HashMap;
import java.util.Map;

// Configurar la librería
NotificationConfig config = new NotificationConfig();

// Registrar proveedores
config.registerProvider(NotificationChannel.EMAIL, SendGridProvider.class);
config.registerProvider(NotificationChannel.SLACK, SlackWebhookProvider.class);
config.registerProvider(NotificationChannel.SMS, TwilioProvider.class);

// Configurar cada canal
Map<String, Object> emailConfig = new HashMap<>();
emailConfig.put("apiKey", "your-sendgrid-api-key");
emailConfig.put("fromEmail", "notifications@yourdomain.com");
config.configureChannel(NotificationChannel.EMAIL, emailConfig);

Map<String, Object> slackConfig = new HashMap<>();
slackConfig.put("webhookUrl", "https://hooks.slack.com/services/your/webhook");
config.configureChannel(NotificationChannel.SLACK, slackConfig);

Map<String, Object> smsConfig = new HashMap<>();
smsConfig.put("accountSid", "your-twilio-account-sid");
smsConfig.put("authToken", "your-twilio-auth-token");
smsConfig.put("fromNumber", "+1234567890");
config.configureChannel(NotificationChannel.SMS, smsConfig);

// Crear el manager
NotificationManager notificationManager = new NotificationManager(config);
```

### 2. Enviar Notificaciones

```java
// Enviar email
Map<String, Object> emailMetadata = new HashMap<>();
emailMetadata.put("to", "user@example.com");
emailMetadata.put("subject", "Bienvenido a nuestra plataforma");
notificationManager.sendNotification("email", "Hola, este es un mensaje de bienvenida", emailMetadata);

// Enviar mensaje a Slack
Map<String, Object> slackMetadata = new HashMap<>();
slackMetadata.put("channel", "#general");
notificationManager.sendNotification("slack", "Nuevo usuario registrado", slackMetadata);

// Enviar SMS
Map<String, Object> smsMetadata = new HashMap<>();
smsMetadata.put("phoneNumber", "+573001234567");
notificationManager.sendNotification("sms", "Tu código de verificación es: 123456", smsMetadata);
```

## Extensión de la Librería

### 1. Crear un Nuevo Proveedor

Para agregar un nuevo proveedor de email (por ejemplo, Amazon SES):

```java
package org.example.notification.email.providers;

import org.example.notification.email.EmailProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AmazonSESProvider implements EmailProvider {
    private static final Logger logger = LoggerFactory.getLogger(AmazonSESProvider.class);

    private String accessKey;
    private String secretKey;
    private String region;
    private String fromEmail;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        logger.info("Amazon SES: Enviando email de {} a {} con asunto: {}",
                fromEmail, metadata.get("to"), metadata.get("subject"));
        logger.info("Contenido: {}", message);
        return true; // Simulación
    }

    @Override
    public String getProviderName() {
        return "AmazonSES";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.accessKey = (String) config.get("accessKey");
        this.secretKey = (String) config.get("secretKey");
        this.region = (String) config.get("region");
        this.fromEmail = (String) config.get("fromEmail");

        if (accessKey == null || secretKey == null || region == null || fromEmail == null) {
            throw new IllegalArgumentException("Amazon SES requires accessKey, secretKey, region and fromEmail");
        }

        logger.info("Amazon SES configurado para región: {}", region);
    }
}
```

### 2. Registrar el Nuevo Proveedor

```java
// Registrar el nuevo proveedor
config.registerProvider(NotificationChannel.EMAIL, AmazonSESProvider.class);

// Configurar el proveedor
Map<String, Object> amazonSESConfig = new HashMap<>();
amazonSESConfig.put("accessKey", "your-access-key");
amazonSESConfig.put("secretKey", "your-secret-key");
amazonSESConfig.put("region", "us-east-1");
amazonSESConfig.put("fromEmail", "notifications@yourdomain.com");
config.configureChannel(NotificationChannel.EMAIL, amazonSESConfig);
```

### 3. Crear un Nuevo Canal

Para agregar un nuevo canal (por ejemplo, Push Notifications):

```java
// 1. Crear la interfaz del proveedor
package org.example.notification.push;

import org.example.notification.NotificationProvider;

public interface PushProvider extends NotificationProvider {
}

// 2. Crear la implementación del canal
package org.example.notification.push;

import org.example.notification.Notification;
import org.example.notification.NotificationChannel;

import java.util.Map;

public class PushNotification implements Notification {
    private final PushProvider provider;

    public PushNotification(PushProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        if (metadata == null || !metadata.containsKey("deviceToken")) {
            throw new IllegalArgumentException("Push notification requires 'deviceToken' in metadata");
        }
        return provider.send(message, metadata);
    }

    @Override
    public String getChannelType() {
        return NotificationChannel.PUSH.getChannelName();
    }
}

// 3. Crear un proveedor concreto (ej: Firebase)
package org.example.notification.push.providers;

import org.example.notification.push.PushProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class FirebaseProvider implements PushProvider {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseProvider.class);

    private String apiKey;
    private String projectId;

    @Override
    public boolean send(String message, Map<String, Object> metadata) {
        logger.info("Firebase: Enviando push a {}: {}",
                metadata.get("deviceToken"), message);
        return true;
    }

    @Override
    public String getProviderName() {
        return "Firebase";
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.apiKey = (String) config.get("apiKey");
        this.projectId = (String) config.get("projectId");

        if (apiKey == null || projectId == null) {
            throw new IllegalArgumentException("Firebase requires apiKey and projectId");
        }
    }
}

// 4. Actualizar el NotificationFactory para soportar el nuevo canal
// (Agregar el case PUSH en el switch)
```

## Dependencias

La librería utiliza:

- **SLF4J**: Para logging (puede usar Logback, Log4j2, etc. como implementación)
- **JUnit 5**: Para testing (solo en pruebas)

## Principios de Diseño

- **SOLID**: Especialmente Open/Closed, Dependency Inversion y Single Responsibility
- **Patrones**: Factory, Strategy, Builder
- **Extensibilidad**: Diseñada para ser fácilmente extendible
- **Calidad**: Código limpio y buenas prácticas

## Ejemplo Completo

Consulte el archivo `src/main/java/org/example/Main.java` para un ejemplo completo de uso.

## Contribución

Las contribuciones son bienvenidas. Por favor, siga las buenas prácticas de código y asegúrese de que los tests pasen.

## Licencia

MIT