FROM openjdk:17-jdk-slim

WORKDIR /app

COPY springboot-backend/pom.xml springboot-backend/target/ /app/

RUN mvn clean install -f springboot-backend/pom.xml

COPY springboot-backend/target/springboot-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]