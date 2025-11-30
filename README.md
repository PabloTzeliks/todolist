# TodoList API

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Deploy](https://img.shields.io/badge/Deploy-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)

---

<details>
<summary><b>🇧🇷 Português (PT-BR)</b></summary>

## 📖 Sobre

**TodoList API** é uma API RESTful de gerenciamento de tarefas desenvolvida em **Java 17** com **Spring Boot 3**. Este projeto foi criado durante o curso de Spring Boot da **Rocketseat** e foi além do conteúdo original, implementando:

- **Dockerfile otimizado** com multi-stage build para produção
- **Deploy na nuvem** utilizando a plataforma Render

O objetivo principal foi consolidar conhecimentos em desenvolvimento Backend, aplicando boas práticas de arquitetura, segurança com autenticação via **Basic Auth** e hash de senhas com **BCrypt**.

---

## 🚀 Tecnologias

As principais tecnologias utilizadas neste projeto:

| Tecnologia | Descrição |
|------------|-----------|
| **Java 17** | Linguagem de programação principal |
| **Spring Boot 3** | Framework para desenvolvimento de aplicações Java |
| **Spring Data JPA** | Abstração para acesso a dados com JPA |
| **H2 Database** | Banco de dados em memória para desenvolvimento |
| **Lombok** | Redução de boilerplate com anotações |
| **BCrypt** | Hash seguro de senhas |
| **Docker & Docker Compose** | Containerização da aplicação |
| **Render** | Plataforma de deploy na nuvem |

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas, organizando o código de forma modular e escalável:

```
src/main/java/br/com/pablotzeliks/todolist/
├── controller/     # Camada de apresentação - Endpoints REST
├── model/          # Entidades JPA e modelos de dados
├── repository/     # Interfaces de acesso ao banco de dados
├── filter/         # Filtros de autenticação (Basic Auth)
├── errors/         # Tratamento global de exceções
└── utils/          # Classes utilitárias
```

| Camada | Responsabilidade |
|--------|------------------|
| **Controller** | Recebe requisições HTTP, valida dados de entrada e retorna respostas |
| **Model** | Define as entidades do domínio mapeadas para o banco de dados |
| **Repository** | Abstrai as operações de persistência usando Spring Data JPA |
| **Filter** | Intercepta requisições para validar autenticação Basic Auth |
| **Errors** | Centraliza o tratamento de exceções da aplicação |
| **Utils** | Contém funções auxiliares reutilizáveis |

---

## 📡 Endpoints

### Usuários

| Método | Rota | Descrição | Autenticação |
|--------|------|-----------|--------------|
| `POST` | `/users/create` | Criar novo usuário | ❌ Não |

### Tarefas

| Método | Rota | Descrição | Autenticação |
|--------|------|-----------|--------------|
| `POST` | `/tasks/create` | Criar nova tarefa | ✅ Basic Auth |
| `GET` | `/tasks/list` | Listar tarefas do usuário | ✅ Basic Auth |
| `PUT` | `/tasks/update/{id}` | Atualizar tarefa | ✅ Basic Auth |

> 💡 **Dica:** Para documentação interativa completa, rode o projeto localmente e acesse o **Swagger UI** (se disponível).

---

## ⚙️ Como Rodar

### 🐳 Via Docker (Recomendado)

```bash
# 1. Clone o repositório
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Build da imagem Docker
docker build -t todolist-api .

# 3. Execute o container
docker run -p 8080:8080 todolist-api
```

A API estará disponível em: `http://localhost:8080`

### 🔧 Via Maven/IDE

```bash
# 1. Clone o repositório
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Execute com Maven
./mvnw spring-boot:run
```

**Pré-requisitos:**
- Java 17+
- Maven 3.8+

---

## 🗺️ Próximos Passos (Roadmap)

- [ ] Migrar de H2 para **PostgreSQL** em produção
- [ ] Adicionar **testes unitários** e de integração
- [ ] Implementar pipeline de **CI/CD** com GitHub Actions
- [ ] Adicionar documentação com **Swagger/OpenAPI**
- [ ] Implementar autenticação com **JWT**

---

## 👨‍💻 Autor & Certificado

**Pablo Tzeliks**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablotzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)

### 🎓 Certificado Rocketseat

<img width="1122" height="792" alt="png-certificate" src="https://github.com/user-attachments/assets/15a56c67-9a1f-4166-924f-2e332ebdd1ff" />

---

</details>

<details>
<summary><b>🇺🇸 English (EN)</b></summary>

## 📖 About

**TodoList API** is a RESTful task management API built with **Java 17** and **Spring Boot 3**. This project was created during the **Rocketseat** Spring Boot course and went beyond the original content by implementing:

- **Optimized Dockerfile** with multi-stage build for production
- **Cloud deployment** using the Render platform

The main goal was to consolidate Backend development knowledge, applying best practices in architecture, security with **Basic Auth** authentication, and password hashing with **BCrypt**.

---

## 🚀 Technologies

The main technologies used in this project:

| Technology | Description |
|------------|-------------|
| **Java 17** | Main programming language |
| **Spring Boot 3** | Framework for Java application development |
| **Spring Data JPA** | Data access abstraction with JPA |
| **H2 Database** | In-memory database for development |
| **Lombok** | Boilerplate reduction with annotations |
| **BCrypt** | Secure password hashing |
| **Docker & Docker Compose** | Application containerization |
| **Render** | Cloud deployment platform |

---

## 🏗️ Architecture

The project follows a layered architecture, organizing code in a modular and scalable way:

```
src/main/java/br/com/pablotzeliks/todolist/
├── controller/     # Presentation layer - REST Endpoints
├── model/          # JPA entities and data models
├── repository/     # Database access interfaces
├── filter/         # Authentication filters (Basic Auth)
├── errors/         # Global exception handling
└── utils/          # Utility classes
```

| Layer | Responsibility |
|-------|----------------|
| **Controller** | Receives HTTP requests, validates input data, and returns responses |
| **Model** | Defines domain entities mapped to the database |
| **Repository** | Abstracts persistence operations using Spring Data JPA |
| **Filter** | Intercepts requests to validate Basic Auth authentication |
| **Errors** | Centralizes application exception handling |
| **Utils** | Contains reusable helper functions |

---

## 📡 Endpoints

### Users

| Method | Route | Description | Authentication |
|--------|-------|-------------|----------------|
| `POST` | `/users/create` | Create new user | ❌ No |

### Tasks

| Method | Route | Description | Authentication |
|--------|-------|-------------|----------------|
| `POST` | `/tasks/create` | Create new task | ✅ Basic Auth |
| `GET` | `/tasks/list` | List user's tasks | ✅ Basic Auth |
| `PUT` | `/tasks/update/{id}` | Update task | ✅ Basic Auth |

> 💡 **Tip:** For complete interactive documentation, run the project locally and access **Swagger UI** (if available).

---

## ⚙️ How to Run

### 🐳 Via Docker (Recommended)

```bash
# 1. Clone the repository
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Build Docker image
docker build -t todolist-api .

# 3. Run the container
docker run -p 8080:8080 todolist-api
```

The API will be available at: `http://localhost:8080`

### 🔧 Via Maven/IDE

```bash
# 1. Clone the repository
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Run with Maven
./mvnw spring-boot:run
```

**Prerequisites:**
- Java 17+
- Maven 3.8+

---

## 🗺️ Roadmap

- [ ] Migrate from H2 to **PostgreSQL** in production
- [ ] Add **unit and integration tests**
- [ ] Implement **CI/CD** pipeline with GitHub Actions
- [ ] Add documentation with **Swagger/OpenAPI**
- [ ] Implement **JWT** authentication

---

## 👨‍💻 Author & Certificate

**Pablo Tzeliks**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablotzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)

### 🎓 Rocketseat Certificate

<img width="1122" height="792" alt="png-certificate" src="https://github.com/user-attachments/assets/fa8c5e46-bf91-4d46-89f2-8b436442249f" />

---

</details>

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/PabloTzeliks">Pablo Tzeliks</a>
</p>
