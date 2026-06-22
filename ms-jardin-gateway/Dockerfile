# Usamos una imagen de Java 17 (que es la que tienes en tu proyecto)
FROM eclipse-temurin:17-jdk-alpine

# Copiamos el archivo .jar que genera Maven al compilar
COPY target/*.jar app.jar

# Ejecutamos la aplicación cuando el contenedor arranque
ENTRYPOINT ["java","-jar","/app.jar"]