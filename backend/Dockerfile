#
# Build stage
#
FROM gradle:7.2.0-jdk17 as build-stage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN gradle build -x test

#
# Package stage
#
FROM alpine
RUN apk add --no-cache minisign
RUN apk add --no-cache openjdk17-jre-headless
EXPOSE 8080
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
ARG JAR_FILE=/home/gradle/source/build/libs/*.jar
COPY --from=build-stage ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
