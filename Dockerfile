FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/retail-billing-software-0.0.1-SNAPSHOT.jar app.jar

# Set path where the cert will be created
ENV DB_SSL_CA_PATH=/app/ca.pem

EXPOSE 5454

# Create ca.pem from the CA_CERT env var at runtime, then run the jar
ENTRYPOINT ["/bin/sh", "-c", "echo \"$CA_CERT\" > $DB_SSL_CA_PATH && java -jar app.jar"]
