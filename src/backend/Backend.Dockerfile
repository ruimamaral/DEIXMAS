# Build stage
FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /usr/app
COPY ./.mvn/ .mvn
COPY ./mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

# Final
FROM eclipse-temurin:17-jre-jammy
WORKDIR /usr/app
COPY --from=build /usr/app/target/*.jar /usr/app/*.jar
ENTRYPOINT ["java", "-jar", "/usr/app/*.jar" ]
