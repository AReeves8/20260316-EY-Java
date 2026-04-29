FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src/ src/
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# SETTING ENVIRONMENT VARIABLES IN DOCKER
ENV APP_PROFILE=prod
ENV PROD_DB_URL=jdbc:postgresql://localhost:5432/movies        
ENV PROD_DB_USERNAME=postgres                        
ENV PROD_DB_PASSWORD=root

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]