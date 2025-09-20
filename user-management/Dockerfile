FROM gradle:8.14.3-jdk21 AS build
WORKDIR /user-management
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21_35-jdk-alpine
WORKDIR /app
COPY --from=build /user-management/build/libs/*.jar user-management-0.0.1-SNAPSHOT.jar
EXPOSE 9004
CMD ["java", "-jar", "user-management-0.0.1-SNAPSHOT.jar"]