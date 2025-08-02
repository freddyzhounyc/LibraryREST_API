FROM openjdk:24
MAINTAINER freddyzhounyc.com
COPY libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]