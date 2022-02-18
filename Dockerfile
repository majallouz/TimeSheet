<<<<<<< HEAD
FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/timesheet-*.jar timesheet.jar
ENTRYPOINT ["java","-jar","/timesheet.jar"]

=======
FROM openjdk:8-jdk-alpine3.8
EXPOSE 8180
EXPOSE 3306
ARG JAR_FILE=target/timesheet-*.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
>>>>>>> 3c18ba37c9eb1887eb66b5ab0a4c9ffe7af2f9cd
