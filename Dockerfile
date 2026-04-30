FROM amazoncorretto:21-alpine3.21-jdk

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/student_task_manager
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=postgres

COPY target/studenttaskmanager-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]