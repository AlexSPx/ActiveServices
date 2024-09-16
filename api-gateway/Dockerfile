# Use the official Java 17 image
FROM arm64v8/openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/api-gateway-1.0-SNAPSHOT.jar /app/api-gateway.jar

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
