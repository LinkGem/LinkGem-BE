FROM gradle:jdk17-alpine AS builder

WORKDIR /linkgem

COPY . .

RUN gradle bootJar -x test -i --stacktrace --parallel --no-daemon

FROM openjdk:17-jdk-slim

COPY --from=builder /linkgem/build/libs/*.jar /linkgem/app.jar

WORKDIR /linkgem

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
