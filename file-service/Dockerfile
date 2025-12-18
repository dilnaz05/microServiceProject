# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Copy the parent pom.xml
COPY pom.xml .

# Copy all modules to satisfy Maven's project structure
COPY common/ ./common/
COPY api-gateway/ ./api-gateway/
COPY file-service/ ./file-service/
# Build only the file-service module
RUN mvn -pl file-service -am clean package -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/file-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
