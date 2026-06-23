# 🌿 El Jardín del Ángel - EP3 DSY1103

**Institución:** Duoc UC  
**Asignatura:** Desarrollo Full Stack I - DSY1103_008V  
**Evaluación:** Parcial 3

---

## 📌 Descripción del Proyecto
Sistema de gestión de catálogo de productos botánicos y ventas para el vivero
"El Jardín del Ángel". Implementado con arquitectura de microservicios
independientes, comunicación REST, API Gateway centralizado, documentación
Swagger y pruebas unitarias con JUnit y Mockito.

---

## 👤 Integrantes del Equipo
| Nombre | Rol |
|---|---|
| Angelo Pastene Acevedo | Desarrollo Full Stack - Grupo 7 |

---

## 🔧 Aporte Realizado por Cada Integrante
**Angelo Pastene Acevedo:**
- Diseño e implementación de los microservicios ms-jardin-catalogo y ms-jardin-ventas
- Configuración del API Gateway (ms-jardin-gateway)
- Modelado de base de datos relacional (Categoria, Producto, Venta, DetalleVenta)
- Implementación de relaciones bidireccionales JPA (@OneToMany / @ManyToOne)
- Comunicación entre servicios mediante OpenFeign (CatalogoClient)
- Reglas de negocio: validación y descuento de stock al registrar ventas
- Documentación técnica con Swagger/OpenAPI
- Pruebas unitarias con JUnit 5 y Mockito
- Configuración de Dockerfiles para los 3 servicios

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Framework:** Spring Boot 4.0.6 / Spring Boot 3.2.5 (Gateway)
- **Arquitectura:** Spring Cloud Gateway, Spring Cloud OpenFeign
- **Persistencia:** Spring Data JPA, MySQL 8
- **Documentación:** Swagger / OpenAPI (springdoc 2.8.5)
- **Testing:** JUnit 5, Mockito
- **Contenedores:** Docker

---

## 🏗️ Arquitectura y Puertos

| Servicio | Puerto | Descripción |
|---|---|---|
| ms-jardin-gateway | 9090 | Punto de entrada único (API Gateway) |
| ms-jardin-catalogo | 9080 | Gestión de productos y categorías |
| ms-jardin-ventas | 9081 | Gestión de ventas y detalle de ventas |

---

## 🔗 APIs y Endpoints Disponibles

### ms-jardin-catalogo (Puerto 9080)
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/productos | Listar todos los productos |
| GET | /api/v1/productos/{id} | Buscar producto por ID |
| POST | /api/v1/productos | Crear producto |
| PUT | /api/v1/productos/{id} | Actualizar producto |
| DELETE | /api/v1/productos/{id} | Eliminar producto |
| PUT | /api/v1/productos/{id}/stock | Reducir stock |
| GET | /api/v1/categorias | Listar categorías |

### ms-jardin-ventas (Puerto 9081)
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /api/v1/ventas | Listar todas las ventas |
| GET | /api/v1/ventas/{id} | Buscar venta por ID |
| POST | /api/v1/ventas | Registrar nueva venta |

---

## 🚪 Puertos y Rutas del API Gateway (Puerto 9090)

| Ruta Gateway | Redirige a |
|---|---|
| /api/v1/productos/** | ms-jardin-catalogo:9080 |
| /api/v1/ventas/** | ms-jardin-ventas:9081 |

**Filtro activo:** `X-Servicio-Destino` (trazabilidad de cabeceras)

---

## 📚 Enlaces de Swagger
- **Catálogo:** http://localhost:9080/swagger-ui/index.html
- **Ventas:** http://localhost:9081/swagger-ui/index.html

---

## 🚀 Instrucciones para Ejecutar y Probar el Sistema

### 1. Base de Datos
Ejecutar el script `script_db_vivero.sql` en MySQL (HeidiSQL o Workbench).  
Esto crea automáticamente `db_jardin_catalogo` y `db_jardin_ventas` con datos de prueba.

### 2. Levantar los Servicios (orden estricto)
1. Iniciar MySQL (Laragon u otro)
2. Ejecutar `ms-jardin-catalogo` → esperar que levante en puerto 9080
3. Ejecutar `ms-jardin-ventas` → esperar que levante en puerto 9081
4. Ejecutar `ms-jardin-gateway` → levanta en puerto 9090

### 3. Probar el Sistema
- Swagger Catálogo: http://localhost:9080/swagger-ui/index.html
- Swagger Ventas: http://localhost:9081/swagger-ui/index.html
- Gateway: GET http://localhost:9090/api/v1/productos

---

## 🧪 Pruebas Unitarias
- `ProductoServiceTest`: 6 tests (listar, buscar, guardar, actualizar, eliminar, reducirStock)
- `VentaServiceTest`: 2 tests (buscar por ID, guardar venta con Feign mockeado)