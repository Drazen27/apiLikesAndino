FROM openjdk:21

COPY target/springboot-eks.jar app.jar

ENTRYPOINT [ "java" , "-jar", ¨/app.jar¨  ]