# TodoList API - Enterprise Edition 🚀

[![Java CI with Maven](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml/badge.svg)](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

> **A robust, tested, and CI-integrated RESTful API built with Spring Boot 3, demonstrating Modern Java Best Practices and Enterprise-Grade Architecture.**

---

## 🌍 Language / Idioma

<table>
  <tr>
    <td align="center" width="50%">
      <a href="README-en.md">
        <img src="https://img.shields.io/badge/🇺🇸_English-Full_Documentation-blue?style=for-the-badge" alt="English"/>
      </a>
    </td>
    <td align="center" width="50%">
      <a href="README-pt-BR.md">
        <img src="https://img.shields.io/badge/🇧🇷_Português-Documentação_Completa-green?style=for-the-badge" alt="Português"/>
      </a>
    </td>
  </tr>
</table>

---

## 📖 Overview

**TodoList API - Enterprise Edition** is a production-ready task management API showcasing professional software engineering practices. Originally created as part of a Rocketseat course, this project has evolved into a comprehensive demonstration of **architectural patterns**, **automated testing**, **continuous integration**, and **environment-specific configurations**.

This repository serves as a **Senior Java Developer Portfolio Project**, highlighting technical maturity from initial concept to enterprise-grade deployment.

---

## 🚀 Project Evolution & Versioning

This project has undergone significant transformations, evolving through distinct phases of maturity:

| Version | Name | Key Features | Release |
|---------|------|-------------|---------|
| [**v3.0.0**](https://github.com/PabloTzeliks/todolist/releases/tag/v3.0.0) | **The DevOps & QA Update** | • PostgreSQL integration for production<br/>• Spring Profiles (Dev/Prod environments)<br/>• Comprehensive unit tests (JUnit 5 + Mockito)<br/>• GitHub Actions CI/CD pipeline | *Current* |
| **v2.0.0** | **The Architecture Update** | • Layered architecture implementation<br/>• DTO & Mapper patterns<br/>• Global exception handling (RFC 7807)<br/>• Bean validation integration | 2024 |
| **v1.0.0-course** | **The MVP** | • Initial RESTful API structure<br/>• Basic CRUD operations<br/>• Swagger/OpenAPI documentation | 2024 |

### 🎯 What Makes v3.0.0 "Enterprise-Grade"

This version represents a significant leap in software maturity, implementing:
- ✅ **Production-Ready Persistence** with environment-specific database strategies
- ✅ **Quality Assurance** through automated testing at multiple layers
- ✅ **Continuous Integration** ensuring code integrity on every commit
- ✅ **Professional DevOps Practices** with containerization and deployment automation

---

## 💡 Technical Highlights

### 🧪 Testing Strategy

Comprehensive test coverage demonstrating professional quality assurance:

- **Unit Tests for Services**: Business logic tested in isolation using Mockito mocks
- **Unit Tests for Controllers**: HTTP layer tested with `@WebMvcTest` for fast feedback
- **Unit Tests for Mappers**: DTO ↔ Entity conversion logic validated
- **Framework**: JUnit 5 with Mockito for dependency mocking

```bash
# Run all tests
./mvnw test
```

**Why This Matters**: Automated tests catch regressions early, enable confident refactoring, and serve as living documentation of system behavior.

### 🔄 CI/CD Pipeline

Every push to the main branch triggers an **automated GitHub Actions workflow** that:
1. Checks out the code
2. Configures JDK 17 with Maven caching
3. Builds the application (`mvn package`)
4. Executes the full test suite

This ensures **code integrity** and prevents broken builds from reaching production.

**View CI Status**: [![Java CI](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml/badge.svg)](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml)

### 🌐 Environment Profiles

The application intelligently adapts to different deployment contexts:

| Profile | Database | Use Case | Configuration |
|---------|----------|----------|---------------|
| **dev** | H2 (in-memory) | Local development & CI/CD | Fast startup, no external dependencies |
| **prod** | PostgreSQL | Production deployment | Persistent, scalable, ACID-compliant |

**How It Works**: Spring Boot's profile mechanism (`application-dev.properties` / `application-prod.properties`) allows seamless switching without code changes.

```bash
# Run in dev mode (default)
./mvnw spring-boot:run

# Run in prod mode (requires PostgreSQL)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

**Why This Matters**: Separating environments prevents "works on my machine" issues and allows optimized configurations for each context (e.g., SQL logging in dev, performance tuning in prod).

---

## 🏗️ Architecture & Patterns

This project implements a **Clean Layered Architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│                     Client (HTTP)                       │
└────────────────────────┬────────────────────────────────┘
                         │
              ┌──────────▼──────────┐
              │   Controller Layer  │ ◄── Request/Response DTOs
              │  (HTTP/REST Logic)  │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │   Service Layer     │ ◄── Business Logic
              │  (Validation, etc)  │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │  Repository Layer   │ ◄── Data Access (JPA)
              │    (Persistence)    │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │      Database       │
              │   (H2 / PostgreSQL) │
              └─────────────────────┘

         Supporting Components:
         ├── Mappers: DTO ↔ Entity conversion
         └── Exception Handlers: Global error responses (RFC 7807)
```

### Key Design Patterns

- **DTO Pattern**: Request/Response objects decouple API contracts from internal models
- **Mapper Pattern**: Dedicated classes handle object transformations
- **Repository Pattern**: JPA repositories abstract data access
- **Global Exception Handling**: Centralized `@ControllerAdvice` for consistent error responses

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+** (or use included wrapper)
- **Docker** (optional, for containerized deployment)
- **PostgreSQL** (only required for prod profile)

### Option 1: Quick Start (Dev Mode - Recommended for Local Development)

```bash
# Clone the repository
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# Run with embedded H2 database (dev profile is default)
./mvnw spring-boot:run

# Access the application
# API Base URL: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui/index.html
# H2 Console: http://localhost:8080/h2-console
```

### Option 2: Production Mode with PostgreSQL

```bash
# Ensure PostgreSQL is running and configure environment variables
export DATABASE_URL=jdbc:postgresql://localhost:5432/todolist_db
export DATABASE_USERNAME=your_username
export DATABASE_PASSWORD=your_password

# Run with prod profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Option 3: Docker Deployment

```bash
# Build and run with Docker
docker build -t todolist-api .
docker run -p 8080:8080 todolist-api

# Or use docker-compose
docker-compose up
```

### Running Tests

```bash
# Execute all unit tests
./mvnw test

# Run tests with coverage report
./mvnw test jacoco:report
```

---

## 📡 API Endpoints

### 👤 User Management
- **POST** `/users/create` - Register a new user (public)

### 📋 Task Management (Requires Basic Auth)
- **POST** `/tasks/create` - Create a new task
- **GET** `/tasks/list` - List all user tasks
- **PUT** `/tasks/update/{id}` - Update an existing task

### 📚 Documentation
- **Swagger UI**: `/swagger-ui/index.html` - Interactive API explorer
- **OpenAPI JSON**: `/api-docs` - Machine-readable API specification

---

## 🛡️ Security & Error Handling

### Authentication
- **BCrypt** password hashing for secure credential storage
- **HTTP Basic Authentication** for protected endpoints

### Error Responses

All errors follow **RFC 7807 (Problem Details for HTTP APIs)** for consistent, machine-readable responses:

```json
{
  "message": "Error on Field Validation",
  "status": 400,
  "statusError": "Bad Request",
  "errors": [
    {
      "field": "password",
      "message": "The password must have between 6 and 20 characters"
    }
  ]
}
```

**Benefits**: Structured errors simplify frontend integration and API debugging.

---

## 🛠️ Tech Stack

### Core Technologies
- **Java 17** - Modern LTS version with enhanced language features
- **Spring Boot 3.4.0** - Latest framework for rapid development
- **Spring Data JPA** - Simplified data access with Hibernate ORM

### Database Support
- **H2 Database** - In-memory database for dev/testing
- **PostgreSQL** - Production-grade RDBMS

### Security & Validation
- **BCrypt** - Industry-standard password hashing
- **Bean Validation (JSR 380)** - Declarative input validation

### Documentation & Testing
- **SpringDoc OpenAPI** - Automated API documentation (Swagger UI)
- **JUnit 5** - Modern testing framework
- **Mockito** - Mocking framework for unit tests

### DevOps & Tooling
- **GitHub Actions** - Continuous Integration pipeline
- **Docker** - Containerization for consistent deployments
- **Lombok** - Reduces boilerplate code
- **Maven** - Dependency management and build automation

---

## 👨‍💻 Author

**Pablo Ruan Tzeliks**

Senior Java Developer | DevOps Enthusiast | Open Source Contributor

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablotzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)

### 🎓 Origins

This project originated from the [Rocketseat Java Course](https://www.rocketseat.com.br/), evolving far beyond its initial scope to demonstrate enterprise-level engineering practices.

<details>
<summary>View Completion Certificate</summary>

<img width="560" alt="Rocketseat Java Certificate" src="https://github.com/user-attachments/assets/15a56c67-9a1f-4166-924f-2e332ebdd1ff" />

</details>

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

<p align="center">
  <sub>Built with ❤️ and ☕ by <a href="https://github.com/PabloTzeliks">Pablo Ruan Tzeliks</a></sub>
  <br/>
  <sub>From educational assignment to enterprise-grade portfolio project 🚀</sub>
</p>

