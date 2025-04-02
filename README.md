# SofDev Product Service

Este es el **Product Service** de **SofDev**, desarrollado con **Spring Boot** y arquitectura **Hexagonal**. Su propósito es gestionar los productos dentro del ecosistema de SofDev, proporcionando endpoints para la creación, actualización y consulta de productos.

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3.2.4**
- **Arquitectura Hexagonal**
- **MongoDB** (o el motor de base de datos que decidas usar)

## 📂 Estructura del Proyecto

```
sofdev-product-service/
├── src/main/java/com/sofdev/product
│   ├── application/
│   │   ├── usecases/
│   │   ├── dtos/
│   ├── domain/
│   │   ├── model/
│   │   ├── ports/
│   ├── infrastructure/
│   │   ├── adapters/
│   │   ├── configuration/
│   ├── SofdevProductServiceApplication.java
├── src/main/resources/
│   ├── application.yml
│   └── bootstrap.yml
├── pom.xml
└── README.md
```

## ⚙️ Configuración

El servicio de productos utiliza `application.properties` para configurar la conexión a la base de datos y definir sus propiedades:

```
  db_uri: mongodb://localhost:27017/sofdev-products
  port: 8083
```

## 🏗️ Instalación y Ejecución

### 1️⃣ Clonar el repositorio

```sh
git clone git@github.com:SofDev/sofdev-product-service.git
cd sofdev-product-service
```

### 2️⃣ Construir el proyecto con Maven

```sh
./mvnw clean install
```

### 3️⃣ Ejecutar el Product Service

```sh
./mvnw spring-boot:run
```

## 📜 Licencia

Este proyecto está bajo la **Licencia MIT**. Puedes leer más en el archivo [LICENSE](LICENSE).
