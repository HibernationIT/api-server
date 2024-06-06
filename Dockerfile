FROM openjdk:17-slim
WORKDIR /app
COPY target/api-server-0.0.1.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]