FROM amazoncorretto:21

ARG JAR_FILE=target/app.jar
COPY build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
