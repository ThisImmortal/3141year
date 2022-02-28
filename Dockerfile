FROM openjdk:8
EXPOSE 3141
ARG JAR_FILE=target/3141year-1.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]