# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests -Dspring.profiles.active=no-db

# Use OpenJDK 24 as the base image
FROM openjdk:24-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/cfc-london-library-0.0.1-SNAPSHOT.jar /app/cfc-london-library-0.0.1-SNAPSHOT.jar

# Expose the port on which the application will run (e.g., 8080)
EXPOSE 8080

# Set the entry point for the application
ENTRYPOINT ["java", "-jar", "cfc-london-library-0.0.1-SNAPSHOT.jar"]
