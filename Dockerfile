FROM gradle:jdk17-alpine as cache

RUN mkdir -p /home/gradle/cache
ENV GRADLE_USER_HOME /home/gradle/cache

COPY build.gradle /home/gradle/java-code/

WORKDIR /home/gradle/java-code

RUN gradle clean build -i --stacktrace

FROM gradle:jdk17-alpine AS builder

COPY --from=cache /home/gradle/cache /home/gradle/.gradle

WORKDIR /linkgem

COPY . .

RUN gradle bootJar -i --stacktrace

FROM openjdk:17-jdk-slim

COPY --from=builder /linkgem/build/libs/*.jar /linkgem/app.jar

WORKDIR /linkgem

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
