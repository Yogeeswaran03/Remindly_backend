# Use an official Java 22 runtime as a parent image
FROM openjdk:22-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the Maven build file (pom.xml) into the container
COPY pom.xml .

# Download the Maven dependencies (avoiding rebuilding them each time)
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Package the application (spring-boot-maven-plugin will create the JAR file)
RUN mvn clean package -DskipTests

# Use the openjdk:22 as the runtime image
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage into the container
COPY --from=build /app/target/hivemindbackend-0.0.1-SNAPSHOT.jar /app/hivemindbackend.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/hivemindbackend.jar"]
