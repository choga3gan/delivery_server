FROM eclipse-temurin:21-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV DB_DDL_AUTO=create

ENTRYPOINT ["java","-DDB__URL=${DB_URL}", "-DDB__USERNAME=${DB_USERNAME}", "-DDB__PASSWORD=${DB_PASSWORD}","-DDB__DDL_AUTO=${DB_DDL_AUTO}", "-DJWT__SECRET=${JWT_SECRET}", "-DJWT__VALID_TIME=${JWT_VALID_TIME}", "-jar", "app.jar"]

EXPOSE 3000
