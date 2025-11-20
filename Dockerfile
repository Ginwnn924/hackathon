# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml và download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code (KHÔNG CẦN download file ở đây nữa)
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Cài curl để download file
RUN apk add --no-cache curl

# Copy JAR từ stage build
COPY --from=builder /app/target/hackathon-0.0.1-SNAPSHOT.jar app.jar

# Download file vietnam.gol vào runtime image
RUN curl -fL 'https://drive.google.com/uc?export=download&id=1LWil3JUQcc2HCXd0Qw2Db5oH2Fnd3GR4' -o /app/vietnam.gol && \
    echo "File size: $(stat -c%s /app/vietnam.gol) bytes"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]