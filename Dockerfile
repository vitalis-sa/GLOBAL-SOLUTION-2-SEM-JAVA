FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml ./pom.xml
COPY .mvn ./.mvn
COPY mvnw ./mvnw
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw package -DskipTests -B

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/quarkus-app/ /app/

EXPOSE 8080

ENTRYPOINT ["java", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.http.port=${PORT:-8080}", "-jar", "quarkus-run.jar"]