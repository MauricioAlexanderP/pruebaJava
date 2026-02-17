-- 1. CREACIÓN DE LA BASE DE DATOS
DROP DATABASE IF EXISTS PRUEBA_JAVA;
CREATE DATABASE PRUEBA_JAVA;
USE PRUEBA_JAVA;

-- 2. CREACIÓN DE TABLAS
-- Tabla de Usuarios
CREATE TABLE USUARIOS (
    ID_USUARIO VARCHAR(30) PRIMARY KEY,
    NOMBRE_USUARIO VARCHAR(255) NOT NULL,
    CONTRASENA VARCHAR(30) NOT NULL
);

-- Tabla de Familias (Categorías)
CREATE TABLE PRODUCTOS_FAMILIAS (
    ID_FAMILIA INT(11) PRIMARY KEY,
    NOMBRE_FAMILIA VARCHAR(255) NOT NULL
);

-- Tabla de Productos
CREATE TABLE PRODUCTOS (
    ID_PRODUCTO INT(11) PRIMARY KEY,
    NOMBRE_PRODUCTO VARCHAR(255) NOT NULL,
    ID_FAMILIA INT(11),
    PRECIO DECIMAL(10,2),
    CONSTRAINT fk_familia FOREIGN KEY (ID_FAMILIA) REFERENCES PRODUCTOS_FAMILIAS(ID_FAMILIA)
);

-- 3. INSERCIONES DE DATOS
-- Familias (Requeridas primero por la Llave Foránea)
INSERT INTO PRODUCTOS_FAMILIAS (ID_FAMILIA, NOMBRE_FAMILIA) VALUES
    (1, 'Electrónica'),
    (2, 'Software'),
    (3, 'Accesorios');

-- Primer bloque de Productos (IDs 101-105)
INSERT INTO PRODUCTOS (ID_PRODUCTO, NOMBRE_PRODUCTO, ID_FAMILIA, PRECIO) VALUES
    (101, 'Laptop Gaming Pro', 1, 1250.50),
    (102, 'Monitor 24 pulgadas 4K', 1, 300.00),
    (103, 'Licencia Antivirus Anual', 2, 45.99),
    (104, 'Mouse Inalámbrico Ergonómico', 3, 25.50),
    (105, 'Teclado Mecánico RGB', 3, 85.00),
    (107, 'Smartphone de Gama Media', 1, 450.00),
    (108, 'Suscripción Office 365', 2, 69.99),
    (109, 'Hub USB-C 7 en 1', 3, 35.00),
    (110, 'Audífonos Bluetooth Noise Cancelling', 1, 180.00),
    (111, 'Tarjeta Gráfica RTX 4060', 1, 350.00),
    (112, 'Licencia Windows 11 Pro', 2, 120.00),
    (113, 'Soporte para Laptop Ventilado', 3, 22.50),
    (114, 'Cámara Web 1080p', 3, 55.00),
    (115, 'Disco Duro Externo 2TB', 1, 95.00),
    (116, 'Editor de Video Profesional (Suscripción)', 2, 210.00);

-- Registro opcional para USUARIOS
INSERT INTO USUARIOS (ID_USUARIO, NOMBRE_USUARIO, CONTRASENA) VALUES
    ('1234', 'pruebaJava', 'pruebaJava');