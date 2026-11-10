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
-- ---------------------------------------------------------------------
-- Historial de lecturas de codigos QR
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS lectura_qr (
id_lectura INT AUTO_INCREMENT PRIMARY KEY,
id_equipo INT NOT NULL,
fecha_lectura DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_lectura_equipo FOREIGN KEY (id_equipo)
REFERENCES equipo(id_equipo)
);
-- ---------------------------------------------------------------------
-- Equipos de prueba (IGNORE: si ya existen, no se duplican)
-- ---------------------------------------------------------------------
INSERT IGNORE INTO equipo (id_equipo, nombre, tipo, marca, modelo, numero_serie, ubicacion,
responsable, direccion_ip, direccion_mac, sistema_operativo, configuracion)
VALUES
(1, 'PC Laboratorio 1 - Puesto 01', 'Computador de escritorio', 'Dell', 'OptiPlex 7090',
'DL7090-A1B2', 'Laboratorio 1 - FISEI', 'Ing. Encargado de Laboratorio', '192.168.10.11',
'A4:BB:6D:12:34:01', 'Windows 11 Pro',
'Procesador: Intel Core i7-10700\nRAM: 16 GB DDR4\nDisco: SSD 512 GB\nRed: VLAN 10 - Laboratorios\nSoftware: NetBeans, JDK, XAMPP, Packet Tracer'),
(2, 'Laptop Docente', 'Laptop', 'Lenovo', 'ThinkPad E14',
'LN-E14-77X9', 'Sala de Docentes', 'Docente de turno', '192.168.20.25',
'C8:5A:CF:98:76:02', 'Windows 10 Pro',
'Procesador: AMD Ryzen 5 5500U\nRAM: 8 GB\nDisco: SSD 256 GB\nRed: Wi-Fi SSID UTA-Docentes'),
(3, 'Impresora Secretaria', 'Impresora', 'HP', 'LaserJet Pro M404dn',
'HP-M404-55Q1', 'Secretaria FISEI', 'Secretaria', '192.168.30.50',
'3C:52:82:AA:BB:03', 'Firmware HP',
'Conexion: Ethernet\nPuerto: 9100 (RAW)\nDuplex automatico: Si\nBandeja: A4'),
(4, 'Switch Acceso Piso 2', 'Switch', 'Cisco', 'Catalyst 2960-24TT',
'FOC1234X5YZ', 'Rack Piso 2', 'Administrador de red', '192.168.99.2',
'00:1A:2B:3C:4D:04', 'Cisco IOS 15.0',
'VLAN de gestion: 99\nVLANs: 10, 20, 30\nVTP: Cliente\nSTP: Rapid-PVST\nTroncales: Gi0/1, Gi0/2');