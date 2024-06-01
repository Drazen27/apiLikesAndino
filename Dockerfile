FROM maven:3.9.5-amazoncorretto-21-debian AS build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine-jdk
COPY --from=build /target/ApiRestLikes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]