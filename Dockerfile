# =========================
# 🔹 Stage 1: Build the JAR
# =========================
FROM maven:3.8.7-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy only the Maven project files (helps with Docker caching)
COPY springboot-backend/pom.xml .
RUN mvn dependency:go-offline

# Copy the full backend source code
COPY springboot-backend/ .

# Build the application (this generates the JAR file inside /target)
RUN mvn clean package -DskipTests

# =========================
# 🔹 Stage 2: Run the App
# =========================
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR from the first stage
COPY --from=builder /app/target/springboot-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the backend port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
