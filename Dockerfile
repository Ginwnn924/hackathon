# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Copy pom.xml và download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Stage 2: Runtime - ĐỔI SANG DEBIAN BASE
FROM eclipse-temurin:17-jre

WORKDIR /app

# Cài Python và gdown
RUN apt-get update && \
    apt-get install -y python3 python3-pip wget && \
    pip3 install --no-cache-dir gdown && \
    rm -rf /var/lib/apt/lists/*

# Copy JAR từ stage build
COPY --from=builder /app/target/hackathon-0.0.1-SNAPSHOT.jar app.jar

# Download file bằng gdown
RUN gdown 1LWil3JUQcc2HCXd0Qw2Db5oH2Fnd3GR4 -O /app/vietnam.gol && \
    ls -lh /app/vietnam.gol && \
    FILE_SIZE=$(stat -c%s /app/vietnam.gol) && \
    echo "Downloaded: $FILE_SIZE bytes" && \
    if [ "$FILE_SIZE" -lt 300000000 ]; then \
        echo "ERROR: Download failed!"; \
        exit 1; \
    fi && \
    chmod 644 /app/vietnam.gol

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]