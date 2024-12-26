# Use the Eclipse Temurin image as the base
FROM eclipse-temurin:17-jdk-focal

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/app-1.0.0-SNAPSHOT.jar /app/app-1.0.0-SNAPSHOT.jar

# Set environment variables for Spring Boot configuration
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080

# Expose the port that the Spring Boot app will run on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app-1.0.0-SNAPSHOT.jar"]
