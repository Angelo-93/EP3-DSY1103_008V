# Evaluación Parcial 3 - Sistema de Ventas y Catálogo "Jardín" 🌿

**Institución:** Duoc UC  
**Autor:** Angelo Pastene Acevedo  
**Rol:** Desarrollo Full Stack / Arquitectura de Microservicios  

## 📌 Descripción del Proyecto
Este proyecto implementa una arquitectura de microservicios para la gestión de un catálogo de productos botánicos y el procesamiento de ventas (Carrito de Compras). El sistema asegura la consistencia de datos, comunicación síncrona entre servicios, y un enrutamiento centralizado.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Framework:** Spring Boot (v4.0.6 LTS)
* **Arquitectura:** Spring Cloud Gateway, Spring Cloud OpenFeign
* **Persistencia:** Spring Data JPA, MySQL 8
* **Documentación:** Swagger / OpenAPI
* **Testing:** JUnit 5, Mockito

## 🏗️ Arquitectura y Puertos
El ecosistema está compuesto por tres servicios principales:

1. **API Gateway (`ms-jardin-gateway`) -> Puerto: 9090**
   * Actúa como proxy inverso y punto de entrada único.
   * Incluye filtros de cabecera (`X-Servicio-Destino`) para trazabilidad.
   * Rutas configuradas: `/api/v1/productos/**` y `/api/v1/ventas/**`

2. **Microservicio Catálogo (`ms-jardin-catalogo`) -> Puerto: 9080**
   * Gestiona las entidades `Categoria` y `Producto` (Relación Bidireccional).
   * Expone el endpoint de reducción de stock utilizado por Ventas.

3. **Microservicio Ventas (`ms-jardin-ventas`) -> Puerto: 9081**
   * Gestiona el flujo Maestro-Detalle (`Venta` y `DetalleVenta`).
   * Actúa como cliente Feign (`CatalogoClient`) para validar y descontar stock remotamente antes de procesar una transacción.

## 🚀 Instrucciones de Ejecución

1. **Base de Datos:**
   * Ejecutar el script adjunto `script_db_vivero.sql` en MySQL (HeidiSQL/Workbench). 
   * Esto creará automáticamente las bases de datos `db_jardin_catalogo` y `db_jardin_ventas` con sus respectivas tablas normalizadas y datos de prueba.

2. **Levantar los Microservicios:**
   * Iniciar los servicios desde el IDE en el siguiente orden estricto:
     1. `ms-jardin-catalogo`
     2. `ms-jardin-ventas`
     3. `ms-jardin-gateway`

## 📚 Documentación de las APIs (Swagger)
Una vez levantados los servicios, la documentación interactiva está disponible en:
* **Catálogo:** [http://localhost:9080/swagger-ui/index.html](http://localhost:9080/swagger-ui/index.html)
* **Ventas:** [http://localhost:9081/swagger-ui/index.html](http://localhost:9081/swagger-ui/index.html)

## 🧪 Cobertura de Pruebas
Se implementaron pruebas unitarias aisladas con Mockito para asegurar la resiliencia de las reglas de negocio críticas, incluyendo:
* Validación y actualización transaccional de Stock.
* Simulación de comunicación de red (Feign) para el procesamiento del Carrito de Compras.