-- =====================================================================
-- Sistema de Lectura y Procesamiento de Codigos QR
-- para Configuracion de Equipos (camara web USB externa)
-- Base de datos MySQL (XAMPP - phpMyAdmin)
-- =====================================================================
CREATE DATABASE IF NOT EXISTS bd_equipos
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_equipos;
-- ---------------------------------------------------------------------
-- Tabla principal: el ID de cada equipo es lo que va dentro del QR
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS equipo (
id_equipo INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
tipo VARCHAR(40) NOT NULL,
marca VARCHAR(50),
modelo VARCHAR(50),
numero_serie VARCHAR(60),
ubicacion VARCHAR(100),
responsable VARCHAR(100),
direccion_ip VARCHAR(15),
direccion_mac VARCHAR(17),
sistema_operativo VARCHAR(60),
configuracion TEXT,
estado VARCHAR(15) NOT NULL DEFAULT 'ACTIVO',
fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
fecha_baja DATETIME NULL,
motivo_baja VARCHAR(200) NULL
);
