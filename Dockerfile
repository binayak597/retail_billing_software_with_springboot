# Step 1: Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /app/target/retail-billing-software-0.0.1-SNAPSHOT.jar app.jar

# Set path where the cert will be created
ENV DB_SSL_CA_PATH=/app/ca.pem

EXPOSE 5454

ENTRYPOINT ["/bin/sh", "-c", "printf \"%s\" \"$CA_CERT\" > $DB_SSL_CA_PATH && java -jar app.jar"]
