FROM maven:3.9.11-amazoncorretto-25 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:25.0.0-alpine3.22

RUN addgroup -S app && adduser -S app -G app
USER app:app

WORKDIR /app

COPY --from=builder /app/target/*.jar technical-test.jar

EXPOSE 8080

ENTRYPOINT ["java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75","-jar","technical-test.jar"]
