ARG MSVC_NAME=student-service

FROM openjdk:21 AS builder

ARG MSVC_NAME

WORKDIR /app/$MSVC_NAME

COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests


FROM openjdk:21

ARG MSVC_NAME

WORKDIR /app

ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/student-service-0.0.1-SNAPSHOT.jar .
ARG APP_PORT=8080
ENV PORT $APP_PORT
EXPOSE $PORT

CMD ["java", "-jar", "student-service-0.0.1-SNAPSHOT.jar"]