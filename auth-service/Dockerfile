# Use the official Java 17 image
FROM arm64v8/openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/auth-service-1.0-SNAPSHOT.jar /app/auth-service.jar

# Copy the keystore file into the container
COPY src/main/resources/keystore.p12 /app/keystore.p12

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the Spring Boot application with keystore configurations
ENTRYPOINT ["java", "-jar", "/app/auth-service.jar"]
