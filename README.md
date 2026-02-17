# PruebaJava - Sistema de Gestión de Productos

## Descripción

Aplicación web desarrollada como prueba técnica que implementa un sistema de gestión de productos con autenticación de usuarios. El proyecto está construido con Spring Boot 4.0.2 y utiliza arquitectura MVC con vistas Thymeleaf.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 4.0.2**
  - Spring Data JPA
  - Spring Web MVC
  - Spring Boot DevTools
- **Thymeleaf** - Motor de plantillas
- **MySQL** - Base de datos
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias
- **JUnit & Mockito** - Testing

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/mp/code/pruebajava/
│   │   ├── PruebaJavaApplication.java
│   │   ├── config/              # Configuraciones
│   │   ├── controller/          # Controladores MVC
│   │   │   ├── LoginController.java
│   │   │   └── ProductoController.java
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── LoginDto.java
│   │   │   ├── ProductoDto.java
│   │   │   ├── ProductosFamiliaDto.java
│   │   │   └── UsuarioDto.java
│   │   ├── exceptions/          # Manejo de excepciones
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── ValidationException.java
│   │   ├── mapper/              # Mappers entre entidades y DTOs
│   │   ├── model/               # Entidades JPA
│   │   │   ├── Producto.java
│   │   │   ├── ProductosFamilia.java
│   │   │   └── Usuario.java
│   │   ├── repository/          # Repositorios JPA
│   │   ├── service/             # Lógica de negocio
│   │   └── validators/          # Validadores personalizados
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       ├── static/              # Recursos estáticos
│       └── templates/           # Plantillas Thymeleaf
│           ├── login/
│           └── producto/
└── test/                        # Tests unitarios e integración
```

## Configuración

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code con Java Extension Pack)

### Variables de Entorno

Crear un archivo `.env` en la raíz del proyecto con las siguientes variables:

```properties
DATABASE_URL=jdbc:mysql://localhost:3306/nombre_base_datos?useSSL=false&serverTimezone=UTC
DATABASE_USERNAME=tu_usuario
DATABASE_PASSWORD=tu_contraseña
```

### Base de Datos

El proyecto requiere una base de datos MySQL con las siguientes tablas:

- **USUARIOS**: Almacena los usuarios del sistema
- **PRODUCTOS**: Almacena la información de productos
- **PRODUCTOS_FAMILIA**: Categorías o familias de productos

##  Ejecución

### Usando Maven

```bash
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

### Usando Maven Wrapper (Windows)

```bash
# Compilar el proyecto
.\mvnw.cmd clean install

# Ejecutar la aplicación
.\mvnw.cmd spring-boot:run
```

### Usando Maven Wrapper (Linux/Mac)

```bash
# Compilar el proyecto
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn clean test jacoco:report
```

## Funcionalidades Principales

### 1. Sistema de Autenticación
- Login de usuarios con sesión
- Validación de credenciales
- Logout con invalidación de sesión
- Redirección automática si el usuario ya está autenticado

### 2. Gestión de Productos
- **Listado de productos paginado** (10 productos por página)
- **Crear nuevos productos** con validación de datos
- **Eliminar productos** existentes
- **Asociación con familias de productos**
- Mensajes de feedback para operaciones (éxito/error)

### 3. Seguridad
- Protección de rutas (requiere autenticación)
- Validación de sesión en cada petición
- Manejo centralizado de excepciones

##  Endpoints Principales

### Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/` | Página de login |
| POST | `/login` | Procesar login |
| GET | `/logout` | Cerrar sesión |

### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/productos` | Listado de productos (paginado) |
| GET | `/productos?page={n}` | Página específica de productos |
| POST | `/productos/save` | Guardar nuevo producto |
| POST | `/productos/delete/{id}` | Eliminar producto por ID |

##  Arquitectura

El proyecto sigue una arquitectura en capas:

1. **Capa de Presentación (Controller)**: Maneja las peticiones HTTP y renderiza las vistas
2. **Capa de Servicio (Service)**: Contiene la lógica de negocio
3. **Capa de Acceso a Datos (Repository)**: Interacción con la base de datos mediante JPA
4. **Capa de Modelo (Model/Entity)**: Entidades JPA que mapean las tablas
5. **DTOs**: Objetos de transferencia de datos entre capas
6. **Mappers**: Conversión entre entidades y DTOs

## Notas Adicionales

- El proyecto utiliza **Lombok** para reducir código boilerplate. Asegúrate de tener el plugin instalado en tu IDE
- Las propiedades de la aplicación se gestionan mediante perfiles (dev)
- El naming strategy de Hibernate está configurado como `PhysicalNamingStrategyStandardImpl` para mantener los nombres de columnas tal como están en la base de datos
- Se incluyen tests unitarios para los servicios principales

##  Troubleshooting

### Error de conexión a la base de datos
- Verificar que MySQL esté ejecutándose
- Confirmar las credenciales en el archivo `.env`
- Verificar que la base de datos exista

### Error de compilación Lombok
- Instalar el plugin de Lombok en el IDE
- Habilitar el procesamiento de anotaciones en el IDE

### Puerto 8080 en uso
- Cambiar el puerto en `application.properties` agregando: `server.port=8081`

