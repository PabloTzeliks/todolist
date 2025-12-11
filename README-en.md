# TodoList API - Professional Edition 🚀

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Deploy](https://img.shields.io/badge/Deploy-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## 📖 About the Project

**TodoList API - Professional Edition** is an enterprise-ready RESTful task management API, built with **Java 17** and **Spring Boot 3**. 

### 🎯 Evolution Journey

This project was born during the **Rocketseat** Spring Boot course, but **evolved far beyond the original content**. What started as an educational project was transformed into a production-ready application, applying industry architectural patterns and best practices.

> 📚 **Want to see where we started?** Check out the [old README](README-OLD.md) to understand the complete evolution of the project and how much it has grown!

---

## 🌟 What Changed in v2.0.0?

This version represents a **complete architectural refactoring**, transforming the project into a corporate-level application:

### ✨ Implemented Improvements

| Evolution | Description |
|----------|-----------|
| **🏛️ Layered Architecture** | Clear separation of concerns: Controller → Service → Repository |
| **📦 DTO Pattern** | Data Transfer Objects for requests and responses, isolating the presentation layer |
| **🔄 Mapper Pattern** | Dedicated conversion between DTOs and Entities, keeping code clean |
| **🛡️ Global Error Handling** | `@ControllerAdvice` with standardized JSON responses (RFC 7807) |
| **✅ Declarative Validation** | Bean Validation (Hibernate Validator) on DTOs |
| **📚 Living Documentation** | Swagger UI (OpenAPI) with interactive examples |
| **🔐 Enhanced Security** | Integration of authentication filter with Exception Handler |
| **📝 Complete JavaDoc** | Detailed documentation in PT-BR across all classes |

---

## 🏗️ Project Architecture

The project follows a modern and scalable **Layered Architecture**:

```
src/main/java/br/com/pablotzeliks/todolist/
├── 📋 task/
│   ├── controller/      # HTTP entry point (TaskController)
│   ├── dto/             # TaskRequestDTO, TaskResponseDTO
│   ├── mapper/          # TaskMapper (DTO ↔ Entity conversion)
│   ├── model/           # Task (JPA Entity), Priority (Enum)
│   ├── repository/      # ITaskRepository (Spring Data JPA)
│   └── service/         # TaskService (Business Rules)
│
├── 👤 user/
│   ├── controller/      # UserController
│   ├── dto/             # UserRequestDTO, UserResponseDTO
│   ├── mapper/          # UserMapper
│   ├── model/           # User (JPA Entity)
│   ├── repository/      # IUserRepository
│   ├── service/         # UserService (includes BCrypt hashing)
│   └── exception/       # UserNotAuthorizedException
│
├── ⚠️ exception/
│   ├── GlobalExceptionHandler.java   # @ControllerAdvice
│   ├── dto/             # ErrorResponseDTO, ValidationErrorDTO
│   └── general/         # Custom exceptions
│
├── 🔒 security/
│   └── FilterTaskAuth.java   # Basic Auth Filter + HandlerExceptionResolver
│
└── ⚙️ config/
    └── SwaggerConfig.java    # OpenAPI Configuration
```

### 📐 Responsibilities of Each Layer

| Layer | What it does | What it does NOT do |
|--------|-----------|---------------|
| **Controller** | Receives HTTP requests, validates input (`@Valid`), delegates to Service | ❌ Business rules, DB access |
| **Service** | Implements all business logic, orchestrates operations | ❌ HTTP manipulation, DTO ↔ Entity conversion |
| **Mapper** | Converts DTOs to Entities and vice versa | ❌ Business logic, persistence |
| **Repository** | Abstracts database operations (Spring Data JPA) | ❌ Business logic |
| **DTO** | Transfers data between layers, defines validations (`@NotBlank`, `@Size`) | ❌ Business logic |

---

## 🚀 Technologies

### Core

| Technology | Version | Description |
|------------|--------|-----------|
| **Java** | 17 | Main language (LTS) |
| **Spring Boot** | 3.4.0 | Productivity framework |
| **Spring Data JPA** | - | Persistence abstraction |
| **H2 Database** | - | In-memory database (dev) |

### Security & Validation

| Technology | Description |
|------------|-----------|
| **BCrypt** | Secure password hashing (cost 12) |
| **Bean Validation** | Declarative DTO validation |
| **Basic Auth** | HTTP authentication (custom filter) |

### Documentation & DevOps

| Technology | Description |
|------------|-----------|
| **SpringDoc OpenAPI** | Automatic Swagger documentation generation |
| **Lombok** | Boilerplate reduction |
| **Docker** | Multi-stage build for production |
| **Render** | Cloud deployment platform |

---

## 📡 API Endpoints

### 👤 Users

| Method | Route | Description | Auth |
|--------|------|-----------|------|
| `POST` | `/users/create` | Create new user | ❌ No |

**Request Example:**
```json
{
  "name": "John Doe",
  "username": "john.doe",
  "password": "password123"
}
```

**Response Example (201):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "John Doe",
  "username": "john.doe",
  "createdAt": "2025-12-11T22:00:00"
}
```

---

### 📋 Tasks

| Method | Route | Description | Auth |
|--------|------|-----------|------|
| `POST` | `/tasks/create` | Create new task | ✅ Basic Auth |
| `GET` | `/tasks/list` | List user's tasks | ✅ Basic Auth |
| `PUT` | `/tasks/update/{id}` | Update task | ✅ Basic Auth |

**Request Example (POST /tasks/create):**
```json
{
  "title": "Study Spring Boot",
  "description": "Review Spring Data JPA concepts",
  "startAt": "2025-12-12T09:00:00",
  "endAt": "2025-12-12T11:00:00",
  "priority": "HIGH"
}
```

**Response Example (201):**
```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "title": "Study Spring Boot",
  "description": "Review Spring Data JPA concepts",
  "startAt": "2025-12-12T09:00:00",
  "endAt": "2025-12-12T11:00:00",
  "priority": "HIGH",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2025-12-11T22:00:00",
  "updatedAt": "2025-12-11T22:00:00"
}
```

---

## 🛡️ Error Handling

One of the differentiators of v2.0.0 is the **global error handling** that returns standardized JSON, facilitating Frontend integration.

### Example: Validation Error (400)

**Invalid request:**
```json
{
  "name": "",
  "username": "jo",
  "password": "123"
}
```

**API Response:**
```json
{
  "message": "Error on Field Validation",
  "status": 400,
  "statusError": "Bad Request",
  "errors": [
    {
      "field": "name",
      "message": "You must insert a name"
    },
    {
      "field": "password",
      "message": "The password must have between 6 and 20 characters"
    }
  ]
}
```

### Example: Resource Not Found (404)

```json
{
  "message": "Tarefa não encontrada.",
  "status": 404,
  "statusError": "Not Found"
}
```

### Example: Conflict (409)

```json
{
  "message": "Username john.doe já está em uso.",
  "status": 409,
  "statusError": "Conflict"
}
```

> 💡 **Frontend Benefit:** The `errors` list allows displaying error messages next to each form field!

---

## 📚 Interactive Documentation (Swagger)

The API has **live and interactive** documentation automatically generated by SpringDoc OpenAPI.

### How to access:

1. Run the project locally (see instructions below)
2. Access: **http://localhost:8080/swagger-ui/index.html**

### What you can do in Swagger:

- ✅ View all available endpoints
- ✅ Test requests directly in the browser
- ✅ See examples of requests and responses
- ✅ Understand possible error codes
- ✅ Visualize DTO schemas

![Swagger UI](https://img.shields.io/badge/Documentation-Interactive-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

---

## ⚙️ How to Run the Project

### 🐳 Via Docker (Recommended for Production)

```bash
# 1. Clone the repository
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Build Docker image
docker build -t todolist-api .

# 3. Run the container
docker run -p 8080:8080 todolist-api
```

The API will be available at: **http://localhost:8080**

---

### 🔧 Via Maven (Local Development)

**Prerequisites:**
- Java 17 or higher
- Maven 3.8 or higher

```bash
# 1. Clone the repository
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Run with Maven Wrapper
./mvnw spring-boot:run
```

**Or with Maven globally installed:**
```bash
mvn spring-boot:run
```

The API will be available at: **http://localhost:8080**

---

### 🧪 Run Tests

```bash
./mvnw test
```

---

## 🧰 Development Tools

### Testing the API with cURL

**Create user:**
```bash
curl -X POST http://localhost:8080/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "username": "john.doe",
    "password": "password123"
  }'
```

**Create task (with authentication):**
```bash
curl -X POST http://localhost:8080/tasks/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n 'john.doe:password123' | base64)" \
  -d '{
    "title": "Study Spring Boot",
    "description": "Review Spring Data JPA concepts",
    "startAt": "2025-12-12T09:00:00",
    "endAt": "2025-12-12T11:00:00",
    "priority": "HIGH"
  }'
```

**List tasks:**
```bash
curl -X GET http://localhost:8080/tasks/list \
  -H "Authorization: Basic $(echo -n 'john.doe:password123' | base64)"
```

---

## 🗺️ Future Roadmap

Planned upcoming improvements:

- [ ] Migrate from H2 to **PostgreSQL** in production
- [ ] Implement authentication with **JWT** (OAuth2)
- [ ] Add **integration tests** (TestContainers)
- [ ] Implement **pagination** in task listing
- [ ] Add **advanced filters** (by priority, date, status)
- [ ] Implement **soft delete** on tasks
- [ ] Configure **CI/CD** pipeline with GitHub Actions
- [ ] Add **metrics** with Micrometer/Prometheus
- [ ] Implement **cache** with Redis

---

## 👨‍💻 Author & Certificate

**Pablo Ruan Tzeliks**

Backend developer passionate about software architecture and development best practices.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablotzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)

### 🎓 Rocketseat Certificate - Java & Spring Boot

This project was started during the Rocketseat Java course, which provided me with the solid foundation to evolve the application to a professional level.

<img width="1122" height="792" alt="png-certificate" src="https://github.com/user-attachments/assets/15a56c67-9a1f-4166-924f-2e332ebdd1ff" />

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Feel free to:

1. Fork the project
2. Create a branch for your feature (`git checkout -b feature/MyFeature`)
3. Commit your changes (`git commit -m 'Add MyFeature'`)
4. Push to the branch (`git push origin feature/MyFeature`)
5. Open a Pull Request

---

<p align="center">
  Made with ❤️ and lots of ☕ by <a href="https://github.com/PabloTzeliks">Pablo Ruan Tzeliks</a>
</p>

<p align="center">
  <sub>From an educational project to an enterprise-ready application 🚀</sub>
</p>
