# TodoList API - Professional Edition 🚀

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Deploy](https://img.shields.io/badge/Deploy-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## 🌍 Language / Idioma

**Choose your language / Escolha seu idioma:**

<table>
  <tr>
    <td align="center" width="50%">
      <a href="README-pt-BR.md">
        <img src="https://img.shields.io/badge/🇧🇷_Português-Click_Here-green?style=for-the-badge" alt="Português"/>
      </a>
      <br/>
      <sub><b>Documentação completa em Português</b></sub>
    </td>
    <td align="center" width="50%">
      <a href="README-en.md">
        <img src="https://img.shields.io/badge/🇺🇸_English-Click_Here-blue?style=for-the-badge" alt="English"/>
      </a>
      <br/>
      <sub><b>Full documentation in English</b></sub>
    </td>
  </tr>
</table>

---

## 📖 Quick Overview

**TodoList API - Professional Edition** is an **enterprise-ready RESTful API** for task management, built with **Java 17** and **Spring Boot 3**.

### 🎯 From Learning to Production

This project evolved from a **Rocketseat** course into a **production-ready application**, implementing industry-standard architectural patterns and best practices.

> 📚 **Want to see the evolution?** Check [README-OLD.md](README-OLD.md) to understand how much this project has grown!

---

## 🌟 v2.0.0 Highlights

| Feature | Description |
|---------|-------------|
| 🏛️ **Layered Architecture** | Clean separation: Controller → Service → Repository |
| 📦 **DTO Pattern** | Request/Response DTOs isolating presentation layer |
| 🔄 **Mapper Pattern** | Dedicated DTO ↔ Entity conversion |
| 🛡️ **Global Error Handling** | RFC 7807 compliant JSON error responses |
| ✅ **Bean Validation** | Declarative validation with detailed error feedback |
| 📚 **Swagger UI** | Live interactive API documentation |
| 🔐 **Enhanced Security** | Basic Auth with integrated exception handling |
| 📝 **Complete JavaDoc** | Full Portuguese documentation in all classes |

---

## 🚀 Quick Start

### 🐳 Docker (Recommended)

```bash
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist
docker build -t todolist-api .
docker run -p 8080:8080 todolist-api
```

### 🔧 Maven

```bash
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist
./mvnw spring-boot:run
```

**API:** http://localhost:8080  
**Swagger UI:** http://localhost:8080/swagger-ui/index.html

---

## 📡 API Endpoints

### 👤 Users
- `POST /users/create` - Create user (no auth)

### 📋 Tasks
- `POST /tasks/create` - Create task (Basic Auth)
- `GET /tasks/list` - List tasks (Basic Auth)
- `PUT /tasks/update/{id}` - Update task (Basic Auth)

---

## 🛡️ Error Response Example

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

> 💡 The `errors` array makes frontend integration easy!

---

## 🏗️ Architecture

```
Controller (HTTP) → Service (Business Logic) → Repository (Data) → Database
         ↓                    ↓
       DTOs              Mappers (DTO ↔ Entity)
```

---

## 🚀 Tech Stack

**Core:** Java 17, Spring Boot 3.4.0, Spring Data JPA, H2  
**Security:** BCrypt, Basic Auth, Bean Validation  
**Docs:** SpringDoc OpenAPI (Swagger UI)  
**DevOps:** Docker, Lombok, Render

---

## 👨‍💻 Author

**Pablo Ruan Tzeliks**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablotzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)

### 🎓 Rocketseat Certificate

<img width="560" alt="certificate" src="https://github.com/user-attachments/assets/15a56c67-9a1f-4166-924f-2e332ebdd1ff" />

---

## 📄 License

MIT License - see [LICENSE](LICENSE) for details.

---

<p align="center">
  <b>📖 For full documentation, choose your language above</b><br/>
  <sub>Made with ❤️ and ☕ by <a href="https://github.com/PabloTzeliks">Pablo Ruan Tzeliks</a></sub>
</p>

<p align="center">
  <sub>From educational project to enterprise-ready application 🚀</sub>
</p>

