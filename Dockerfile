# ==========================
# Stage 1: Build the application
# ==========================
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies first (to use Docker cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the jar (skip tests)
RUN mvn clean package -DskipTests

# ==========================
# Stage 2: Runtime image
# ==========================
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/retail-billing-software-0.0.1-SNAPSHOT.jar app.jar

# Set path where the cert will be created
ENV DB_SSL_CA_PATH=/app/ca.pem

# Expose port
EXPOSE 5454

# At runtime, create ca.pem from CA_CERT env variable, then run the jar
ENTRYPOINT ["/bin/sh", "-c", "printf \"%s\" \"$CA_CERT\" > $DB_SSL_CA_PATH && java -jar app.jar"]
