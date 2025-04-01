FROM openjdk:17-jdk-slim

WORKDIR /app

COPY springboot-backend/ /app/springboot-backend/

RUN apt-get update && apt-get install -y maven

RUN mvn clean install -f /app/springboot-backend/pom.xml

COPY springboot-backend/target/springboot-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]