# syntax=docker/dockerfile:1

#Maven Build
#FROM --platform=linux/amd64 gradle:7.5.1-jdk17-alpine AS builder
#COPY --chown=gradle:gradle . /home/gradle/src
#WORKDIR /home/gradle/src
#RUN gradle build --no-daemon --debug

#Run
#FROM --platform=linux/amd64 openjdk:17-alpine
#COPY --from=builder /home/gradle/src/build/libs/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM --platform=linux/arm64/v8  eclipse-temurin:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN ls -la
ENTRYPOINT ["java","-jar","/app.jar"]
