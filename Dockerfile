FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV DB_DDL_AUTO=create

ENTRYPOINT ["java","-DDB_URL=${DB_URL}", "-DDB_USERNAME=${DB_USERNAME}", "-DDB_PASSWORD=${DB_PASSWORD}","-DDB_DDL_AUTO=${DB_DDL_AUTO}", "-jar", "app.jar"]

EXPOSE 3000