# syntax=docker/dockerfile:1.3
ARG BASE_IMAGE=openjdk:24-jdk-slim

FROM ${BASE_IMAGE} AS build
WORKDIR /app

# Copiando Maven Wrapper e configs
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
RUN chmod +x mvnw

# Baixando dependências em cache
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw dependency:go-offline -B

# Copiando código e construindo jar
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw clean install -DskipTests

FROM ${BASE_IMAGE} AS runtime
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]