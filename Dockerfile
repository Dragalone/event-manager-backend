FROM openjdk:17-oracle
WORKDIR /app
COPY build/libs/event-manager-backend-0.0.1-SNAPSHOT.jar /app/event-manager-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","event-manager-backend.jar"]