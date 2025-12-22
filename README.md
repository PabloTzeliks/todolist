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
| **v2.0.0** | **The Architecture Update** | • Layered architecture implementation<br/>• DTO & Mapper patterns<br/>• Global exception handling (RFC 7807)<br/>• Bean validation integration | 2025 |
| **v1.0.0-course** | **The MVP** | • Initial RESTful API structure<br/>• Basic CRUD operations<br/>• Swagger/OpenAPI documentation | 2025 |

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
         ├── Exception Handlers: Global error responses (RFC 7807)
         ├── Security Filters: Authentication & Authorization
         └── Configuration: Swagger, JPA, Profiles
```

### 📦 Package Structure & Responsibilities

The application follows a **modular package-by-feature** organization, promoting high cohesion and low coupling:

```
src/main/java/br/com/pablotzeliks/todolist/
│
├── 📋 task/                          # Task Management Module
│   ├── controller/                   # REST endpoints for tasks
│   │   └── TaskController.java      # POST /create, GET /list, PUT /update/{id}
│   ├── service/                      # Business logic & validation
│   │   └── TaskService.java         # Task CRUD operations, authorization checks
│   ├── repository/                   # Data access layer
│   │   └── ITaskRepository.java     # JPA repository interface
│   ├── model/                        # Domain entities
│   │   ├── Task.java                # JPA entity with relationships
│   │   └── Priority.java            # Enum (LOW, MEDIUM, HIGH, URGENT)
│   ├── dto/                          # Data Transfer Objects
│   │   ├── TaskRequestDTO.java      # Request payload for creation
│   │   ├── TaskUpdateDTO.java       # Request payload for updates
│   │   └── TaskResponseDTO.java     # Response payload
│   └── mapper/                       # DTO ↔ Entity conversion
│       └── TaskMapper.java          # Manual mapping logic
│
├── 👤 user/                          # User Management Module
│   ├── controller/                   # REST endpoints for users
│   │   └── UserController.java      # POST /create
│   ├── service/                      # Business logic & password hashing
│   │   └── UserService.java         # User creation, BCrypt integration
│   ├── repository/                   # Data access layer
│   │   └── IUserRepository.java     # JPA repository with custom queries
│   ├── model/                        # Domain entities
│   │   └── User.java                # JPA entity
│   ├── dto/                          # Data Transfer Objects
│   │   ├── UserRequestDTO.java      # Request payload
│   │   └── UserResponseDTO.java     # Response payload (no password)
│   ├── mapper/                       # DTO ↔ Entity conversion
│   │   └── UserMapper.java          # Manual mapping logic
│   └── exception/                    # User-specific exceptions
│       └── UserNotAuthorizedException.java
│
├── 🛡️ exception/                     # Global Exception Handling
│   ├── GlobalExceptionHandler.java  # @ControllerAdvice for all exceptions
│   ├── dto/                          # Error response DTOs
│   │   ├── ErrorResponseDTO.java    # RFC 7807 compliant error structure
│   │   └── ValidationErrorDTO.java  # Field validation errors
│   └── general/                      # Reusable exception classes
│       ├── ResourceNotFoundException.java
│       ├── ResourceAlreadyExistsException.java
│       ├── BusinessRuleException.java
│       └── AuthenticationException.java
│
├── 🔒 security/                      # Security & Authentication
│   └── FilterTaskAuth.java          # Custom filter for Basic Auth validation
│
└── ⚙️ config/                        # Application Configuration
    └── SwaggerConfig.java            # OpenAPI/Swagger documentation setup
```

### Key Design Patterns

- **DTO Pattern**: Request/Response objects decouple API contracts from internal models, preventing over-exposure and tight coupling
- **Mapper Pattern**: Dedicated classes handle object transformations, centralizing conversion logic
- **Repository Pattern**: JPA repositories abstract data access, providing a clean separation from business logic
- **Global Exception Handling**: Centralized `@ControllerAdvice` ensures consistent error responses across all endpoints (RFC 7807)
- **Filter Chain Pattern**: Custom security filter (`FilterTaskAuth`) validates authentication before reaching controllers

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

#### **POST** `/users/create` - Register a new user
**Authentication**: ❌ Not required (public endpoint)

**Request Body**:
```json
{
  "name": "John Silva",
  "username": "john.silva",
  "password": "securepass123"
}
```

**Validations**:
- `name`: Required, cannot be blank
- `username`: Required, cannot be blank, must be unique
- `password`: Required, 6-20 characters

**Success Response (201 Created)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "John Silva",
  "username": "john.silva",
  "createdAt": "2025-12-22T10:30:00"
}
```

**Error Response (409 Conflict)** - User already exists:
```json
{
  "message": "User already exists",
  "status": 409,
  "statusError": "Conflict"
}
```

---

### 📋 Task Management

All task endpoints require **HTTP Basic Authentication** with username and password.

#### **POST** `/tasks/create` - Create a new task
**Authentication**: ✅ Required (Basic Auth)

**Request Headers**:
```
Authorization: Basic am9obi5zaWx2YTpzZWN1cmVwYXNzMTIz
```

**Request Body**:
```json
{
  "title": "Study Spring Boot",
  "description": "Review Spring Data JPA concepts and best practices",
  "startAt": "2025-12-23T09:00:00",
  "endAt": "2025-12-23T11:00:00",
  "priority": "HIGH"
}
```

**Validations**:
- `title`: Required, max 50 characters
- `description`: Optional, max 255 characters
- `startAt`: Required, must be present or future date
- `endAt`: Required, must be future date
- `priority`: Enum (`LOW`, `MEDIUM`, `HIGH`, `URGENT`)

**Success Response (201 Created)**:
```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "title": "Study Spring Boot",
  "description": "Review Spring Data JPA concepts and best practices",
  "startAt": "2025-12-23T09:00:00",
  "endAt": "2025-12-23T11:00:00",
  "priority": "HIGH",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2025-12-22T10:35:00",
  "updatedAt": "2025-12-22T10:35:00"
}
```

---

#### **GET** `/tasks/list` - List all user tasks
**Authentication**: ✅ Required (Basic Auth)

**Request Headers**:
```
Authorization: Basic am9obi5zaWx2YTpzZWN1cmVwYXNzMTIz
```

**Success Response (200 OK)**:
```json
[
  {
    "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
    "title": "Study Spring Boot",
    "description": "Review Spring Data JPA concepts and best practices",
    "startAt": "2025-12-23T09:00:00",
    "endAt": "2025-12-23T11:00:00",
    "priority": "HIGH",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "createdAt": "2025-12-22T10:35:00",
    "updatedAt": "2025-12-22T10:35:00"
  },
  {
    "id": "8d0f7780-8536-51ef-b058-f18ed2e01bf8",
    "title": "Project Meeting",
    "description": "Discuss v3.0.0 features and roadmap",
    "startAt": "2025-12-24T14:00:00",
    "endAt": "2025-12-24T15:30:00",
    "priority": "URGENT",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "createdAt": "2025-12-22T11:00:00",
    "updatedAt": "2025-12-22T11:00:00"
  }
]
```

**Empty List Response (200 OK)**:
```json
[]
```

---

#### **PUT** `/tasks/update/{id}` - Update an existing task
**Authentication**: ✅ Required (Basic Auth)

**URL Parameters**:
- `id`: UUID of the task to update (e.g., `7c9e6679-7425-40de-944b-e07fc1f90ae7`)

**Request Headers**:
```
Authorization: Basic am9obi5zaWx2YTpzZWN1cmVwYXNzMTIz
```

**Request Body** (all fields optional - partial update):
```json
{
  "title": "Study Spring Boot & Security",
  "description": "Review Spring Data JPA and Spring Security",
  "priority": "URGENT"
}
```

**Validations**:
- `title`: Optional, max 50 characters
- `description`: Optional, max 255 characters
- `startAt`: Optional, must be present or future date
- `endAt`: Optional, must be future date
- `priority`: Optional, enum (`LOW`, `MEDIUM`, `HIGH`, `URGENT`)

**Success Response (200 OK)**:
```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "title": "Study Spring Boot & Security",
  "description": "Review Spring Data JPA and Spring Security",
  "startAt": "2025-12-23T09:00:00",
  "endAt": "2025-12-23T11:00:00",
  "priority": "URGENT",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2025-12-22T10:35:00",
  "updatedAt": "2025-12-22T11:15:00"
}
```

**Error Response (404 Not Found)** - Task doesn't exist:
```json
{
  "message": "Task not found",
  "status": 404,
  "statusError": "Not Found"
}
```

**Error Response (403 Forbidden)** - User doesn't own the task:
```json
{
  "message": "User not authorized to access this resource",
  "status": 403,
  "statusError": "Forbidden"
}
```

---

### 📚 Documentation
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html` - Interactive API explorer with live testing
- **OpenAPI JSON**: `http://localhost:8080/api-docs` - Machine-readable API specification

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

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablo-ruan-tzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:arq.pabloo@gmail.com)

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

