# STAGE 1: BUILD
#   - each "FROM" statement begins a new stage. each stage acts as a temporary image. 

# FROM: gives us a base image to work with
#   - rather than having to install a filesystem, then install java, then install maven
#   - using AS to give this stage a name so it can be referenced later
FROM maven:3.9-eclipse-temurin-21 AS build

# WORKDIR: create a working directory in the container. all following commands will be executed from that directory
WORKDIR /app

# COPY: copies files from the build context (local file system for now) into the container
#   - COPY [source location] [destination]
#       - . just means the current directory (which is /app in our container)
COPY pom.xml .

# each command (or layer) in your dockerfile is cached. so if there's no changes between image builds, commands can be skipped.


# RUN: executes some shell command
#   - mvn dependency: maven command to grab dependencies from maven central
#   - go-offline: tells maven that after the command is executed, it will have everything it needs, 
#       and therefore, doesn't need internet access going forward
#   - -B: runs in batch mode so it is faster
RUN mvn dependency:go-offline -B

# copy over source code to a new app/src folder in the container
COPY src ./src

# clean the existing executable (if there is one) and create a new one
#   - skip tests in container to save time
RUN mvn clean package -DskipTests


#################################################################################################
# STAGE 2: RUN
#   - maven, jdk, build tools, etc. are no longer needed. start fresh with just the JRE
FROM eclipse-temurin:21-jre

WORKDIR /app


# groupadd --system appgroup    -> creates a system group (GID < 1000 -> no login, no home dir) named appgroup
# useradd --system              -> create a system user (UID < 1000 -> no password, limited login)
#   --gid appgroup              -> adds the user to the group created above
#   --no-create-home            -> doesn't create a /home/appuser directory
#   appuser                     -> name of the system user
RUN groupadd --system appgroup && useradd --system --gid appgroup --no-create-home appuser

# copies the JAR file from the build image to the run image
COPY --from=build /app/target/*.jar app.jar

# need to make our new user the owner of the jar so they can run it
RUN chown appuser:appgroup app.jar

# switch to new user
USER appuser

# EXPOSE: opens a port in the container to allow traffic through
EXPOSE 8080


# ENTRYPOINT: command to exuecute as soon as the CONTAINER is built
#   - you *can* do ENTRYPOINT java -jar app.jar but you *shouldn't*
#       - running without the array, the container will spin up its own shell to run the command in
#       - you want your app to be the main process so it has full priority, and can handle shutdowns gracefully
ENTRYPOINT [ "java", "-jar", "app.jar" ]