-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.4.3 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para db_jardin_catalogo
CREATE DATABASE IF NOT EXISTS `db_jardin_catalogo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_jardin_catalogo`;

-- Volcando estructura para tabla db_jardin_catalogo.categorias
DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla db_jardin_catalogo.categorias: ~2 rows (aproximadamente)
INSERT INTO `categorias` (`id`, `nombre`) VALUES
	(1, 'Flores y Rosas'),
	(2, 'Tierra e Insumos'),
	(3, 'Maceteros y Accesorios');

-- Volcando estructura para tabla db_jardin_catalogo.productos
DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `precio` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `categoria_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2fwq10nwymfv7fumctxt9vpgb` (`categoria_id`),
  CONSTRAINT `FK2fwq10nwymfv7fumctxt9vpgb` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`),
  CONSTRAINT `productos_chk_1` CHECK ((`precio` >= 1)),
  CONSTRAINT `productos_chk_2` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla db_jardin_catalogo.productos: ~31 rows (aproximadamente)
INSERT INTO `productos` (`precio`, `stock`, `categoria_id`, `id`, `nombre_producto`) VALUES
	(9000, 20, 1, 1, 'Rosa Darcey Bussell'),
	(3500, 50, 2, 2, 'Tierra de Hoja 5Kg'),
	(12000, 15, 3, 3, 'Macetero de Greda Grande'),
	(8500, 25, 1, 4, 'Rosa Chrysler Imperial (Roja)'),
	(7500, 30, 1, 5, 'Rosa Iceberg (Blanca)'),
	(8000, 15, 1, 6, 'Rosa Pascali (Blanca)'),
	(9000, 10, 1, 7, 'Rosa Queen Elizabeth (Rosada)'),
	(9500, 12, 1, 8, 'Rosa Double Delight (Bicolor)'),
	(8500, 18, 1, 9, 'Rosa Just Joey (Damasco)'),
	(10000, 8, 1, 10, 'Rosa Mister Lincoln (Roja)'),
	(9500, 20, 1, 11, 'Rosa Peace (Amarilla/Rosado)'),
	(11000, 5, 1, 12, 'Rosa Graham Thomas (Amarilla)'),
	(12000, 7, 1, 13, 'Rosa Blue Moon (Lavanda)'),
	(8000, 14, 1, 14, 'Rosa Lady Banks (Amarilla Pequeña)'),
	(13000, 6, 1, 15, 'Rosa Julio Iglesias (Rayada Roja/Blanca)'),
	(6500, 40, 2, 16, 'Tierra de Hoja 10Kg'),
	(4500, 25, 2, 17, 'Humus de Lombriz 2Kg'),
	(5500, 20, 2, 18, 'Sustrato para Rosas 5Kg'),
	(3000, 50, 2, 19, 'Abono Orgánico 1Kg'),
	(4000, 15, 2, 20, 'Perlita para Drenaje 5L'),
	(7000, 12, 2, 21, 'Turba Rubia 10L'),
	(9500, 10, 2, 22, 'Fertilizante Floración 1L'),
	(6000, 20, 3, 23, 'Macetero de Greda Mediano'),
	(3500, 35, 3, 24, 'Macetero de Greda Pequeño'),
	(15000, 5, 3, 25, 'Macetero Cerámica Esmaltada Azul'),
	(1500, 100, 3, 26, 'Macetero Plástico Café 20cm'),
	(8500, 12, 3, 27, 'Macetero Colgante Fibra Coco'),
	(12500, 10, 3, 28, 'Tijera de Poda Profesional'),
	(18000, 4, 3, 29, 'Regadera Zinc 5L'),
	(5500, 15, 3, 30, 'Guantes de Jardinería'),
	(9000, 8, 3, 31, 'Set de Herramientas (3 piezas)');


-- Volcando estructura de base de datos para db_jardin_ventas
CREATE DATABASE IF NOT EXISTS `db_jardin_ventas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_jardin_ventas`;

-- Volcando estructura para tabla db_jardin_ventas.ventas
DROP TABLE IF EXISTS `detalle_ventas`;
DROP TABLE IF EXISTS `ventas`;

CREATE TABLE IF NOT EXISTS `ventas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_venta` datetime(6) DEFAULT NULL,
  `precio_total` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos de prueba para la tabla ventas
INSERT INTO `ventas` (`id`, `fecha_venta`, `precio_total`) VALUES
    (1, '2026-06-21 23:30:00.000000', 21500),
    (2, '2026-06-21 23:45:00.000000', 15000);

-- Volcando estructura para tabla db_jardin_ventas.detalle_ventas
CREATE TABLE IF NOT EXISTS `detalle_ventas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `venta_id` bigint NOT NULL,
  `producto_id` bigint NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` double NOT NULL,
  `sub_total` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_detalle_venta` (`venta_id`),
  CONSTRAINT `fk_detalle_venta` FOREIGN KEY (`venta_id`) REFERENCES `ventas` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos de prueba para los renglones (detalle) de esas ventas
INSERT INTO `detalle_ventas` (`id`, `venta_id`, `producto_id`, `cantidad`, `precio_unitario`, `sub_total`) VALUES
    (1, 1, 1, 2, 9000, 18000),   -- Venta 1: 2x Rosa Darcey (9000c/u)
    (2, 1, 2, 1, 3500, 3500),    -- Venta 1: 1x Tierra de Hoja (3500c/u)
    (3, 2, 5, 2, 7500, 15000);   -- Venta 2: 2x Rosa Iceberg (7500c/u)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;