# TodoList API - Professional Edition 🚀

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)
![Deploy](https://img.shields.io/badge/Deploy-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## 📖 Sobre o Projeto

**TodoList API - Professional Edition** é uma API RESTful enterprise-ready de gerenciamento de tarefas, desenvolvida em **Java 17** com **Spring Boot 3**. 

### 🎯 Jornada de Evolução

Este projeto nasceu durante o curso de Spring Boot da **Rocketseat**, mas **evoluiu muito além do conteúdo original**. O que começou como um projeto didático foi transformado em uma aplicação pronta para produção, aplicando padrões arquiteturais e boas práticas da indústria.

> 📚 **Quer ver de onde partimos?** Confira o [README antigo](README-OLD.md) para entender a evolução completa do projeto e o quanto ele cresceu!

---

## 🌟 O que mudou na v2.0.0?

Esta versão representa uma **refatoração arquitetural completa**, transformando o projeto em uma aplicação de nível corporativo:

### ✨ Evoluções Implementadas

| Evolução | Descrição |
|----------|-----------|
| **🏛️ Arquitetura em Camadas** | Separação clara de responsabilidades: Controller → Service → Repository |
| **📦 Pattern DTO** | Data Transfer Objects para requests e responses, isolando a camada de apresentação |
| **🔄 Pattern Mapper** | Conversão dedicada entre DTOs e Entidades, mantendo o código limpo |
| **🛡️ Tratamento Global de Erros** | `@ControllerAdvice` com respostas JSON padronizadas (RFC 7807) |
| **✅ Validação Declarativa** | Bean Validation (Hibernate Validator) nos DTOs |
| **📚 Documentação Viva** | Swagger UI (OpenAPI) com exemplos interativos |
| **🔐 Segurança Aprimorada** | Integração do filtro de autenticação com o Exception Handler |
| **📝 JavaDoc Completo** | Documentação detalhada em PT-BR em todas as classes |

---

## 🏗️ Arquitetura do Projeto

O projeto segue uma **Arquitetura em Camadas (Layered Architecture)** moderna e escalável:

```
src/main/java/br/com/pablotzeliks/todolist/
├── 📋 task/
│   ├── controller/      # Porta de entrada HTTP (TaskController)
│   ├── dto/             # TaskRequestDTO, TaskResponseDTO
│   ├── mapper/          # TaskMapper (conversão DTO ↔ Entity)
│   ├── model/           # Task (Entidade JPA), Priority (Enum)
│   ├── repository/      # ITaskRepository (Spring Data JPA)
│   └── service/         # TaskService (Regras de Negócio)
│
├── 👤 user/
│   ├── controller/      # UserController
│   ├── dto/             # UserRequestDTO, UserResponseDTO
│   ├── mapper/          # UserMapper
│   ├── model/           # User (Entidade JPA)
│   ├── repository/      # IUserRepository
│   ├── service/         # UserService (inclui hash BCrypt)
│   └── exception/       # UserNotAuthorizedException
│
├── ⚠️ exception/
│   ├── GlobalExceptionHandler.java   # @ControllerAdvice
│   ├── dto/             # ErrorResponseDTO, ValidationErrorDTO
│   └── general/         # Exceções customizadas
│
├── 🔒 security/
│   └── FilterTaskAuth.java   # Filtro Basic Auth + HandlerExceptionResolver
│
└── ⚙️ config/
    └── SwaggerConfig.java    # Configuração OpenAPI
```

### 📐 Responsabilidades de Cada Camada

| Camada | O que faz | O que NÃO faz |
|--------|-----------|---------------|
| **Controller** | Recebe requisições HTTP, valida entrada (`@Valid`), delega ao Service | ❌ Regras de negócio, acesso ao BD |
| **Service** | Implementa toda a lógica de negócio, orquestra operações | ❌ Manipulação HTTP, conversão DTO ↔ Entity |
| **Mapper** | Converte DTOs em Entidades e vice-versa | ❌ Lógica de negócio, persistência |
| **Repository** | Abstrai operações de banco de dados (Spring Data JPA) | ❌ Lógica de negócio |
| **DTO** | Transfere dados entre camadas, define validações (`@NotBlank`, `@Size`) | ❌ Lógica de negócio |

---

## 🚀 Tecnologias

### Core

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 17 | Linguagem principal (LTS) |
| **Spring Boot** | 3.4.0 | Framework de produtividade |
| **Spring Data JPA** | - | Abstração de persistência |
| **H2 Database** | - | Banco em memória (dev) |

### Segurança & Validação

| Tecnologia | Descrição |
|------------|-----------|
| **BCrypt** | Hash seguro de senhas (custo 12) |
| **Bean Validation** | Validação declarativa de DTOs |
| **Basic Auth** | Autenticação HTTP (filtro customizado) |

### Documentação & DevOps

| Tecnologia | Descrição |
|------------|-----------|
| **SpringDoc OpenAPI** | Geração automática de documentação Swagger |
| **Lombok** | Redução de boilerplate |
| **Docker** | Multi-stage build para produção |
| **Render** | Plataforma de deploy em nuvem |

---

## 📡 Endpoints da API

### 👤 Usuários

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `POST` | `/users/create` | Criar novo usuário | ❌ Não |

**Exemplo de Requisição:**
```json
{
  "name": "João Silva",
  "username": "joao.silva",
  "password": "senha123"
}
```

**Exemplo de Resposta (201):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "username": "joao.silva",
  "createdAt": "2025-12-11T22:00:00"
}
```

---

### 📋 Tarefas

| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| `POST` | `/tasks/create` | Criar nova tarefa | ✅ Basic Auth |
| `GET` | `/tasks/list` | Listar tarefas do usuário | ✅ Basic Auth |
| `PUT` | `/tasks/update/{id}` | Atualizar tarefa | ✅ Basic Auth |

**Exemplo de Requisição (POST /tasks/create):**
```json
{
  "title": "Estudar Spring Boot",
  "description": "Revisar os conceitos de Spring Data JPA",
  "startAt": "2025-12-12T09:00:00",
  "endAt": "2025-12-12T11:00:00",
  "priority": "HIGH"
}
```

**Exemplo de Resposta (201):**
```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "title": "Estudar Spring Boot",
  "description": "Revisar os conceitos de Spring Data JPA",
  "startAt": "2025-12-12T09:00:00",
  "endAt": "2025-12-12T11:00:00",
  "priority": "HIGH",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2025-12-11T22:00:00",
  "updatedAt": "2025-12-11T22:00:00"
}
```

---

## 🛡️ Tratamento de Erros

Um dos diferenciais da v2.0.0 é o **tratamento global de erros** que retorna JSON padronizado, facilitando a integração com Frontends.

### Exemplo: Erro de Validação (400)

**Requisição inválida:**
```json
{
  "name": "",
  "username": "jo",
  "password": "123"
}
```

**Resposta da API:**
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

### Exemplo: Recurso Não Encontrado (404)

```json
{
  "message": "Tarefa não encontrada.",
  "status": 404,
  "statusError": "Not Found"
}
```

### Exemplo: Conflito (409)

```json
{
  "message": "Username joao.silva já está em uso.",
  "status": 409,
  "statusError": "Conflict"
}
```

> 💡 **Benefício para o Frontend:** A lista `errors` permite exibir mensagens de erro ao lado de cada campo do formulário!

---

## 📚 Documentação Interativa (Swagger)

A API possui documentação **viva e interativa** gerada automaticamente pelo SpringDoc OpenAPI.

### Como acessar:

1. Rode o projeto localmente (veja instruções abaixo)
2. Acesse: **http://localhost:8080/swagger-ui/index.html**

### O que você pode fazer no Swagger:

- ✅ Ver todos os endpoints disponíveis
- ✅ Testar requisições diretamente no navegador
- ✅ Ver exemplos de requests e responses
- ✅ Entender os códigos de erro possíveis
- ✅ Visualizar os schemas de DTOs

![Swagger UI](https://img.shields.io/badge/Documentação-Interativa-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

---

## ⚙️ Como Rodar o Projeto

### 🐳 Via Docker (Recomendado para Produção)

```bash
# 1. Clone o repositório
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Build da imagem Docker
docker build -t todolist-api .

# 3. Execute o container
docker run -p 8080:8080 todolist-api
```

A API estará disponível em: **http://localhost:8080**

---

### 🔧 Via Maven (Desenvolvimento Local)

**Pré-requisitos:**
- Java 17 ou superior
- Maven 3.8 ou superior

```bash
# 1. Clone o repositório
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# 2. Execute com Maven Wrapper
./mvnw spring-boot:run
```

**Ou com Maven instalado globalmente:**
```bash
mvn spring-boot:run
```

A API estará disponível em: **http://localhost:8080**

---

### 🧪 Executar Testes

```bash
./mvnw test
```

---

## 🧰 Ferramentas de Desenvolvimento

### Testando a API com cURL

**Criar usuário:**
```bash
curl -X POST http://localhost:8080/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "username": "joao.silva",
    "password": "senha123"
  }'
```

**Criar tarefa (com autenticação):**
```bash
curl -X POST http://localhost:8080/tasks/create \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n 'joao.silva:senha123' | base64)" \
  -d '{
    "title": "Estudar Spring Boot",
    "description": "Revisar os conceitos de Spring Data JPA",
    "startAt": "2025-12-12T09:00:00",
    "endAt": "2025-12-12T11:00:00",
    "priority": "HIGH"
  }'
```

**Listar tarefas:**
```bash
curl -X GET http://localhost:8080/tasks/list \
  -H "Authorization: Basic $(echo -n 'joao.silva:senha123' | base64)"
```

---

## 🗺️ Roadmap Futuro

Próximas evoluções planejadas:

- [ ] Migrar de H2 para **PostgreSQL** em produção
- [ ] Implementar autenticação com **JWT** (OAuth2)
- [ ] Adicionar **testes de integração** (TestContainers)
- [ ] Implementar **paginação** na listagem de tarefas
- [ ] Adicionar **filtros avançados** (por prioridade, data, status)
- [ ] Implementar **soft delete** nas tarefas
- [ ] Configurar pipeline de **CI/CD** com GitHub Actions
- [ ] Adicionar **métricas** com Micrometer/Prometheus
- [ ] Implementar **cache** com Redis

---

## 👨‍💻 Autor & Certificado

**Pablo Ruan Tzeliks**

Desenvolvedor Backend apaixonado por arquitetura de software e boas práticas de desenvolvimento.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablotzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)

### 🎓 Certificado Rocketseat - Java & Spring Boot

Este projeto foi iniciado durante o curso de Java da Rocketseat, que me forneceu a base sólida para evoluir a aplicação para o nível profissional.

<img width="1122" height="792" alt="png-certificate" src="https://github.com/user-attachments/assets/15a56c67-9a1f-4166-924f-2e332ebdd1ff" />

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer um Fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abrir um Pull Request

---

<p align="center">
  Feito com ❤️ e muito ☕ por <a href="https://github.com/PabloTzeliks">Pablo Ruan Tzeliks</a>
</p>

<p align="center">
  <sub>De um projeto didático a uma aplicação enterprise-ready 🚀</sub>
</p>
