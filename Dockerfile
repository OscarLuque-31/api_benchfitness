# --- Etapa 1: Construcción (Build Stage) ---
# Usamos una imagen de Maven con Java 17 para compilar nuestro proyecto
FROM maven:3.9.6-eclipse-temurin-17-focal AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos primero el archivo pom.xml para aprovechar el cache de Docker
COPY pom.xml .

# Descargamos las dependencias
RUN mvn dependency:go-offline

# Copiamos el resto del código fuente de la aplicación
COPY src ./src

# Compilamos la aplicación y la empaquetamos en un archivo JAR, saltando los tests
RUN mvn clean package -DskipTests


# --- Etapa 2: Ejecución (Run Stage) ---
# Usamos una imagen de Java 17 mucho más ligera, solo con lo necesario para ejecutar
FROM eclipse-temurin:17-jre-focal

# Establecemos el directorio de trabajo
WORKDIR /app

# Exponemos el puerto en el que corre Spring Boot (por defecto 8080)
EXPOSE 8080

# Copiamos el archivo JAR que compilamos en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# El comando que se ejecutará para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]