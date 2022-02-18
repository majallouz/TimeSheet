FROM openjdk:8-jdk-alpine
ENV MYSQL_HOST=mysql
EXPOSE 8083
ADD target/timesheet-*.jar timesheet.jar
ENTRYPOINT ["java","-jar","/timesheet.jar"]

