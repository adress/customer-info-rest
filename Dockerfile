# Usar una imagen base con JDK 21
FROM eclipse-temurin:21-jdk as build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y las dependencias del proyecto
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Descargar las dependencias necesarias
RUN ./mvnw dependency:go-offline -B

# Copiar todo el código fuente del proyecto
COPY src ./src

# Construir el proyecto
RUN ./mvnw clean package -DskipTests

# Segunda etapa: Crear una imagen más ligera con el JRE
FROM eclipse-temurin:21-jre

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=build /app/target/customer-info-rest-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto que usa la aplicación
EXPOSE 8090

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
