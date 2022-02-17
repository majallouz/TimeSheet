FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/timesheet-*.jar timesheet.jar
<<<<<<< HEAD
ENTRYPOINT ["java","-jar","/timesheet.jar"]
=======
ENTRYPOINT ["java","-jar","/timesheet.jar"]
>>>>>>> a86e2a7838fa608550982bc3f78ac45e2cedf561
