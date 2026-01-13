#FROM gradle:8.10-jdk23 AS build
#
#WORKDIR /app
#
#COPY . .
#
#RUN gradle build -x test

FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY ./build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT  ["java", "-jar", "app.jar"]