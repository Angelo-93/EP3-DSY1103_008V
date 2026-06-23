# Proyecto de Jardinería - EP3

## Descripción del proyecto
Sistema de gestión para el catálogo de productos y ventas de un jardín, diseñado con microservicios (Catálogo, Ventas y Gateway) para una arquitectura escalable.

## Integrantes del equipo
* Angelo Pastene Acevedo

## Aporte realizado por cada integrante
* Angelo Pastene Acevedo: Desarrollo completo de microservicios, configuración de base de datos, implementación de API Gateway y despliegue de soluciones.

## APIs y endpoints disponibles
* Catálogo: `http://localhost:9080/api/v1/productos`

## Puertos y rutas del API Gateway
* Puerto: 9090
* Ruta base: `/api/v1/`

## Enlaces de Swagger
* Catálogo: `http://localhost:9080/swagger-ui.html`
* Ventas: `http://localhost:9081/swagger-ui.html`

## Instrucciones para ejecutar y probar el sistema
1. Clonar el repositorio.
2. Abrir los proyectos en IntelliJ IDEA.
3. Ejecutar los servicios en el orden: Catálogo -> Ventas -> Gateway.
4. Acceder a los endpoints a través del puerto 9090.