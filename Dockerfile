FROM openjdk:21-slim
LABEL authors="luismartinbenitez"
WORKDIR /app

COPY target/burger-0.0.1-SNAPSHOT.jar /app/burger.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/burger.jar"]