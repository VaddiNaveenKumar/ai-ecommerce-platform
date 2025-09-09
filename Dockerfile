FROM openjdk:17-jre-slim

WORKDIR /app

COPY web/target/web-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]