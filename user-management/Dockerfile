FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21_35-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 9004
CMD ["java", "-jar", "app.jar"]