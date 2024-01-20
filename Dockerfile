
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /api-service/target/*.war app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.war"]
