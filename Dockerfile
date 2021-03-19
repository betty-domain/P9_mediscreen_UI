FROM openjdk:8-jdk-alpine
COPY target/*.jar mediscreen-ui.jar
ENTRYPOINT ["java","-jar","/mediscreen-ui.jar"]