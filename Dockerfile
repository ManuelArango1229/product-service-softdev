FROM maven:3.9.8-eclipse-temurin-21 AS builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM   eclipse-temurin:21-jre

COPY --from=builder /app/target/product_service-0.0.1-SNAPSHOT.jar /app/product_service.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/product_service.jar"]
