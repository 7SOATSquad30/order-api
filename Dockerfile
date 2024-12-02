# Use a base image with Java 21
FROM gradle:8.7-jdk-21-and-22-alpine

# Create a directory in the container
WORKDIR /app

# Copy the rest of the application to the container
COPY . .

# Build application Java
RUN gradle :spotlessApply
RUN gradle build

# Copy the built JAR file to the root directory
RUN cp build/libs/order-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]