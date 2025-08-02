FROM openjdk:24-jdk-alpine
MAINTAINER freddyzhounyc.com
COPY libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]