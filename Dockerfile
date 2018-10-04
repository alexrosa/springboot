FROM openjdk:8-jdk-alpine

LABEL maintainer="alexrosa@gmail.com"

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/rezzyraunt-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} rezzyraunt.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rezzyraunt.jar"]

