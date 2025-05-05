# -------- Build Stage --------
    FROM maven:3.9.6-eclipse-temurin-21 AS build
    WORKDIR /app
    
    # Copy pom and download dependencies first (cache-friendly)
    COPY pom.xml .
    RUN mvn dependency:go-offline -B
    
    # Now copy the source and build
    COPY . .
    RUN mvn clean package -DskipTests
    
    # -------- Runtime Stage --------
    FROM eclipse-temurin:21-jdk
    WORKDIR /app
    
    # Copy only the built JAR and cert into the runtime container
    COPY --from=build /app/target/Recipe-Vault-0.0.1-SNAPSHOT.jar app.jar

    
    EXPOSE 8080
    ENTRYPOINT ["java", "-jar", "app.jar"]
    