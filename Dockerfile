# Base image
FROM gradle:9.1.0-jdk21-alpine AS builder

WORKDIR /home/gradle

# Copy only necessary root source code files
COPY settings.gradle.kts gradlew build.gradle.kts ./
COPY gradle gradle
# Copy the entire backend directory
COPY backend/ backend/

# Compile with gradle
RUN ./gradlew :backend:bootJar

# rename and move the resulting JAR file
RUN mv backend/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar

# This will be the base image for the running application
FROM eclipse-temurin:21-alpine

# creating a new user to avoid running the app as root
RUN addgroup -g 1000 app
RUN adduser -G app -D -u 1000 -h /app app

# switch into the newly created user and directory
USER app
WORKDIR /app

# copy the app from the builder image
COPY --from=builder --chown=1000:1000 /home/gradle/app.jar .

# Indicate a port the image will expose
EXPOSE 8080

# Start the Spring Boot Java application
CMD ["java", "-jar", "app.jar"]