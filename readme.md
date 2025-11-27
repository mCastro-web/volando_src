# VolandoUy - Backend

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-6.5.2-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.7.4-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![JPA](https://img.shields.io/badge/Jakarta%20JPA-3.1.0-007396?style=for-the-badge)
![JUnit](https://img.shields.io/badge/JUnit-5.10.2-25A162?style=for-the-badge&logo=junit5&logoColor=white)

Módulo de lógica de negocio del sistema de reservas de vuelos Volando UY.

## Descripción

Este proyecto contiene toda la capa de lógica de negocio y acceso a datos del sistema Volando UY. Implementa los modelos de dominio, repositorios, servicios y toda la funcionalidad core necesaria para gestionar reservas de vuelos, usuarios, rutas aéreas y más.

## 🛠️ Tecnologías

- **Java 21**
- **Hibernate ORM 6.5.2.Final** - Framework de persistencia
- **PostgreSQL 42.7.4** - Base de datos principal
- **Jakarta Persistence API (JPA) 3.1.0**
- **Jakarta Servlet API 6.0.0**
- **JAXB Runtime 4.0.4** - Para binding de XML
- **jBCrypt 0.4** - Para encriptación de contraseñas
- **FlatLaf 3.4** - Look and Feel moderno para interfaces de usuario
- **JUnit Jupiter 5.10.2** - Framework de testing
- **H2 Database 2.2.224** - Base de datos en memoria para tests
- **Maven 3.x** - Gestión de dependencias y construcción

## Requisitos Previos

- Java Development Kit (JDK) 21 o superior
- Maven 3.x
- PostgreSQL (para entorno de producción/desarrollo)

## 📥 Instalación

1. Clonar el repositorio:
```bash
git clone https://github.com/mCastro-web/volando_src.git
cd volando_src
```

2. Compilar e instalar el proyecto en el repositorio local de Maven:
```bash
mvn clean install
```

## Configuración de Base de Datos

### PostgreSQL

El proyecto utiliza PostgreSQL como base de datos principal. Asegúrate de configurar los siguientes parámetros en tu archivo de configuración de Hibernate:

- **Host**: localhost (o tu servidor PostgreSQL)
- **Puerto**: 5432 (por defecto)
- **Base de datos**: volando_uy
- **Usuario y contraseña**: según tu configuración

### H2 Database (Testing)

Para los tests se utiliza H2 Database en memoria, que no requiere configuración adicional.

## Estructura del Proyecto

```
volando_src/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── uy/
│   │   │       └── volandouy/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           └── util/
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml
│   └── test/
│       └── java/
├── pom.xml
└── README.md
```

## 📝 Características Principales

### Persistencia de Datos
- Implementación completa de JPA/Hibernate para gestión de entidades
- Soporte para PostgreSQL en producción
- Base de datos H2 para entorno de testing

### Seguridad
- Encriptación de contraseñas mediante jBCrypt
- Gestión segura de credenciales de usuario

### Arquitectura
- Separación clara entre capas (modelo, repositorio, servicio)
- Diseño modular y extensible
- Preparado para ser consumido por otros módulos (Web Services, aplicaciones cliente)

## Uso como Dependencia

Este módulo está diseñado para ser utilizado como dependencia en otros proyectos del ecosistema Volando UY. Para incluirlo en tu proyecto Maven:

```xml
<dependency>
    <groupId>uy.volandouy</groupId>
    <artifactId>volando-uy-logica</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Testing

El proyecto incluye configuración para JUnit 5. Sin embargo, los tests están actualmente deshabilitados en la configuración de Maven.

Para habilitar los tests, modifica el `pom.xml` eliminando o comentando:
```xml
<configuration>
    <skipTests>true</skipTests>
</configuration>
```

## Build y Empaquetado

### Compilar el proyecto
```bash
mvn clean compile
```

### Crear el JAR
```bash
mvn clean package
```

### Instalar en repositorio local
```bash
mvn clean install
```

## 🔧 Configuración de Maven

### Plugins Utilizados

- **Maven Compiler Plugin (3.13.0)**: Compila el código fuente con Java 21
- **Maven Surefire Plugin (3.2.5)**: Gestiona la ejecución de tests (actualmente configurado para saltarlos)

### Gestión de Versiones

Las versiones de las dependencias principales están centralizadas en properties:
- Hibernate: 6.5.2.Final
- PostgreSQL Driver: 42.7.4
- H2 Database: 2.2.224
- Jakarta Servlet API: 6.0.0
- JAXB: 4.0.4

## 📄 Licencia

Este proyecto cuenta con Licencia MIT.

## Proyectos Relacionados

- [volando_ws](https://github.com/mCastro-web/volando_ws) - Web Services SOAP
- Otros módulos del ecosistema VolandoUy

## Soporte

Para reportar problemas o solicitar nuevas características, por favor abre un issue en el repositorio de GitHub.

---

**Nota**: Este es un módulo de librería que debe ser instalado en el repositorio local de Maven antes de ser usado por otros proyectos del sistema Volando UY.