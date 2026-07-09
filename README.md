# 🌿 El Jardín del Ángel - EFT DSY1103

**Institución:** Duoc UC  
**Asignatura:** Desarrollo Full Stack I - DSY1103_008V  
**Evaluación:** Examen Final Transversal (EFT)

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
| Angelo Pastene Acevedo | Desarrollo Full Stack - Grupo 7 (trabajo individual autorizado) |

---

## 🔧 Aporte Realizado por Cada Integrante
**Angelo Pastene Acevedo:**
- Diseño e implementación de los microservicios ms-jardin-catalogo y ms-jardin-ventas
- Configuración del API Gateway (ms-jardin-gateway)
- Modelado de base de datos relacional (Categoria, Producto, Venta, DetalleVenta)
- Implementación de relaciones bidireccionales JPA (@OneToMany / @ManyToOne)
- Comunicación entre servicios mediante OpenFeign (CatalogoClient)
- Reglas de negocio: validación y descuento de stock al registrar ventas
- Documentación técnica con Swagger/OpenAPI (endpoints, parámetros, códigos de respuesta)
- Pruebas unitarias con JUnit 5 y Mockito — cobertura medida con JaCoCo (84%+ en catálogo y ventas)
- Configuración de Dockerfiles multi-stage para los 3 servicios
- Orquestación completa con Docker Compose (MySQL + los 3 microservicios conectados en red)

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Java 17
- **Framework:** Spring Boot 4.0.6 (catálogo/ventas) / Spring Boot 3.2.5 (Gateway)
- **Arquitectura:** Spring Cloud Gateway, Spring Cloud OpenFeign
- **Persistencia:** Spring Data JPA, MySQL 8, H2 (perfil de test)
- **Documentación:** Swagger / OpenAPI (springdoc 2.8.5)
- **Testing:** JUnit 5, Mockito, JaCoCo (cobertura)
- **Contenedores:** Docker, Docker Compose

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
| POST | /api/v1/ventas | Registrar nueva venta (descuenta stock vía Feign) |
| PUT | /api/v1/ventas/{id} | Actualizar venta (regla de negocio: no permitido, retorna 400) |
| DELETE | /api/v1/ventas/{id} | Eliminar venta |

---

## 🚪 Rutas del API Gateway (Puerto 9090)

| Ruta Gateway | Redirige a |
|---|---|
| /api/v1/productos/** | ms-jardin-catalogo:9080 |
| /api/v1/ventas/** | ms-jardin-ventas:9081 |

**Filtro activo:** `X-Servicio-Destino` (trazabilidad de cabeceras)

---

## 📚 Enlaces de Swagger (local)
- **Catálogo:** http://localhost:9080/swagger-ui/index.html
- **Ventas:** http://localhost:9081/swagger-ui/index.html

---

## 🐳 Ejecución con Docker Compose (recomendado)

Levanta MySQL + los 3 microservicios ya conectados entre sí, con un solo comando.

**Requisitos:** Docker Desktop instalado y corriendo.

```bash
docker-compose up --build
```

Esto construye las imágenes desde el código fuente (Maven multi-stage) y levanta:
- MySQL 8 con las bases `db_jardin_catalogo` y `db_jardin_ventas` ya creadas
- `ms-jardin-catalogo` en el puerto 9080
- `ms-jardin-ventas` en el puerto 9081
- `ms-jardin-gateway` en el puerto 9090

Para apagar todo:
```bash
docker-compose down
```
Para apagar y borrar también los datos (reinicia la base de datos desde cero):
```bash
docker-compose down -v
```

---

## 🚀 Ejecución Local (desde el IDE, sin Docker)

### 1. Base de Datos
Tener MySQL corriendo localmente (ej. Laragon, XAMPP). Las bases y tablas se crean automáticamente al levantar cada servicio (`ddl-auto`), con datos de prueba cargados por `DataLoader`.

### 2. Levantar los Servicios (orden estricto)
1. Iniciar MySQL local
2. Ejecutar `ms-jardin-catalogo` → espera que levante en el puerto 9080
3. Ejecutar `ms-jardin-ventas` → espera que levante en el puerto 9081
4. Ejecutar `ms-jardin-gateway` → levanta en el puerto 9090

### 3. Probar el Sistema
- Swagger Catálogo: http://localhost:9080/swagger-ui/index.html
- Swagger Ventas: http://localhost:9081/swagger-ui/index.html
- Gateway: GET http://localhost:9090/api/v1/productos

---

## 🧪 Pruebas Unitarias y Cobertura

| Microservicio | Tests | Cobertura (JaCoCo) |
|---|---|---|
| ms-jardin-catalogo | `ProductoServiceTest` (6) + `ProductoControllerTest` (7) + contexto (1) | 84% |
| ms-jardin-ventas | `VentaServiceTest` (7) + `VentaControllerTest` (7) + contexto (1) | 84% |
| ms-jardin-gateway | Test de contexto | Sin lógica de negocio propia que testear |

Para generar el reporte de cobertura de un microservicio:
```bash
mvn test
```
El reporte queda en `target/site/jacoco/index.html` de cada microservicio.