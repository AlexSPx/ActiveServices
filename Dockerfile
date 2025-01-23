# Use the latest Maven image with Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Build the project and create the executable JAR for the `web` module
RUN mvn clean install -pl web -am -DskipTests

# Use an official Eclipse Temurin JRE image for Java 21
FROM eclipse-temurin:21-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/web/target/web-*.jar app.jar

# Copy the application.yml file to the classpath root
COPY /web/src/main/resources/application.yml ./application.yml

# Create the keystore and resources directories
RUN mkdir -p ./keystore ./resources

# Generate a keystore file and convert it to PKCS12 format directly in the resources directory
RUN keytool -genkeypair \
    -alias app-keystore \
    -keyalg RSA \
    -keysize 2048 \
    -validity 3650 \
    -keystore  ./keystore/app-keystore.jks \
    -storepass changeit \
    -dname "CN=example.com, OU=IT, O=Example Corp, L=City, ST=State, C=US" && \
    keytool -importkeystore \
    -srckeystore ./keystore/app-keystore.jks \
    -destkeystore ./resources/keystore.p12 \
    -deststoretype PKCS12 \
    -srcstorepass changeit \
    -deststorepass changeit

# Expose the default port used by the application
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
