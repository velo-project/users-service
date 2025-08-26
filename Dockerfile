FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY . .

RUN gradle :presentations:build -x test

FROM amazoncorretto:21

WORKDIR /app
COPY --from=builder /app/presentations/build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-cp", "app.jar", "com.github.veloproject.userservices.presentations.Main"]
