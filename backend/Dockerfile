FROM alpine
RUN apk add --no-cache minisign
RUN apk add --no-cache openjdk17-jre-headless
RUN ./gradlew build
EXPOSE 8080
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
