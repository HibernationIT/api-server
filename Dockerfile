FROM openjdk:17-alpine
WORKDIR /app
COPY target/api-server-0.0.1.jar app.jar
CMD ["java", "-jar", "app.jar"]