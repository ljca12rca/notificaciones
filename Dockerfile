# Dockerfile para empaquetar y ejecutar demos de la librería de notificaciones

# Etapa 1: Compilar la librería con Maven
FROM maven:3.8.6-openjdk-11 AS builder

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el archivo POM y descargar dependencias
COPY pom.xml .

# Copiar el código fuente
COPY src/ src/

# Compilar el proyecto y crear el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Crear imagen de ejecución con el JAR compilado
FROM openjdk:11-jre-slim

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado desde la etapa de construcción
COPY --from=builder /app/target/notification-library-1.0.0.jar /app/notification-demo.jar

# Copiar scripts de ejemplo
COPY src/main/java/org/example/Main.java /app/Main.java
COPY src/main/java/org/example/notification/ /app/notification/

# Crear script de entrada para ejecutar demos
RUN echo '#!/bin/bash\n\
\n\
# Compilar y ejecutar el ejemplo principal\n\
echo "Compilando ejemplo..."\n\
javac -cp "notification-demo.jar:." Main.java\n\
echo "Ejecutando demo de notificaciones..."\n\
java -cp "notification-demo.jar:." org.example.Main\n\
\n\
# Mantener el contenedor vivo para exploración\n\
echo "Demo completada. Contenedor permanecerá activo para exploración."\n\
tail -f /dev/null' > /app/run-demo.sh && \
    chmod +x /app/run-demo.sh

# Puerto para posibles extensiones futuras
EXPOSE 8080

# Comando por defecto: ejecutar el script de demo
CMD ["/app/run-demo.sh"]