# TodoList API - Enterprise Edition 🚀

[![Java CI with Maven](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml/badge.svg)](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

> **Uma API RESTful robusta, testada e integrada com CI/CD, construída com Spring Boot 3, demonstrando Melhores Práticas Modernas de Java e Arquitetura de Nível Enterprise.**

---

## 🌍 Idioma / Language

<table>
  <tr>
    <td align="center" width="50%">
      <a href="README-pt-BR-v3.md">
        <img src="https://img.shields.io/badge/🇧🇷_Português-Documentação_Completa-green?style=for-the-badge" alt="Português"/>
      </a>
    </td>
    <td align="center" width="50%">
      <a href="README.md">
        <img src="https://img.shields.io/badge/🇺🇸_English-Full_Documentation-blue?style=for-the-badge" alt="English"/>
      </a>
    </td>
  </tr>
</table>

---

## 📖 Visão Geral

**TodoList API - Enterprise Edition** é uma API de gerenciamento de tarefas pronta para produção que demonstra práticas profissionais de engenharia de software. Originalmente criada como parte de um curso da Rocketseat, este projeto evoluiu para uma demonstração abrangente de **padrões arquiteturais**, **testes automatizados**, **integração contínua** e **configurações específicas por ambiente**.

Este repositório serve como um **Projeto de Portfólio para Desenvolvedor Java Sênior**, destacando a maturidade técnica desde o conceito inicial até a implantação em nível enterprise.

---

## 🚀 Evolução do Projeto & Versionamento

Este projeto passou por transformações significativas, evoluindo através de fases distintas de maturidade:

| Versão | Nome | Recursos Principais | Lançamento |
|---------|------|---------------------|------------|
| [**v3.0.0**](https://github.com/PabloTzeliks/todolist/releases/tag/v3.0.0) | **A Atualização DevOps & QA** | • Integração com PostgreSQL para produção<br/>• Spring Profiles (ambientes Dev/Prod)<br/>• Testes unitários abrangentes (JUnit 5 + Mockito)<br/>• Pipeline CI/CD com GitHub Actions | *Atual* |
| **v2.0.0** | **A Atualização de Arquitetura** | • Implementação de arquitetura em camadas<br/>• Padrões DTO & Mapper<br/>• Tratamento global de exceções (RFC 7807)<br/>• Integração de Bean Validation | 2025 |
| **v1.0.0-course** | **O MVP** | • Estrutura inicial da API RESTful<br/>• Operações CRUD básicas<br/>• Documentação Swagger/OpenAPI | 2025 |

### 🎯 O Que Torna a v3.0.0 "Nível Enterprise"

Esta versão representa um salto significativo em maturidade de software, implementando:
- ✅ **Persistência Pronta para Produção** com estratégias de banco de dados específicas por ambiente
- ✅ **Garantia de Qualidade** através de testes automatizados em múltiplas camadas
- ✅ **Integração Contínua** garantindo integridade do código em cada commit
- ✅ **Práticas Profissionais de DevOps** com containerização e automação de deployment

---

## 💡 Destaques Técnicos

### 🧪 Estratégia de Testes

Cobertura de testes abrangente demonstrando garantia de qualidade profissional:

- **Testes Unitários para Services**: Lógica de negócio testada isoladamente usando mocks do Mockito
- **Testes Unitários para Controllers**: Camada HTTP testada com `@WebMvcTest` para feedback rápido
- **Testes Unitários para Mappers**: Lógica de conversão DTO ↔ Entity validada
- **Framework**: JUnit 5 com Mockito para criação de mocks de dependências

```bash
# Executar todos os testes
./mvnw test
```

**Por Que Isso Importa**: Testes automatizados detectam regressões cedo, permitem refatoração confiante e servem como documentação viva do comportamento do sistema.

### 🔄 Pipeline CI/CD

Cada push para a branch principal dispara um **workflow automatizado do GitHub Actions** que:
1. Faz checkout do código
2. Configura o JDK 17 com cache do Maven
3. Compila a aplicação (`mvn package`)
4. Executa a suíte completa de testes

Isso garante **integridade do código** e previne que builds quebrados cheguem à produção.

**Ver Status do CI**: [![Java CI](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml/badge.svg)](https://github.com/PabloTzeliks/todolist/actions/workflows/maven.yml)

### 🌐 Perfis de Ambiente

A aplicação se adapta inteligentemente a diferentes contextos de deployment:

| Perfil | Banco de Dados | Caso de Uso | Configuração |
|---------|----------------|-------------|--------------|
| **dev** | H2 (em memória) | Desenvolvimento local & CI/CD | Inicialização rápida, sem dependências externas |
| **prod** | PostgreSQL | Deployment em produção | Persistente, escalável, compatível com ACID |

**Como Funciona**: O mecanismo de perfis do Spring Boot (`application-dev.properties` / `application-prod.properties`) permite troca perfeita sem mudanças de código.

```bash
# Executar em modo dev (padrão)
./mvnw spring-boot:run

# Executar em modo prod (requer PostgreSQL)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

**Por Que Isso Importa**: Separar ambientes previne problemas de "funciona na minha máquina" e permite configurações otimizadas para cada contexto (ex: log SQL em dev, tuning de performance em prod).

---

## 🏗️ Arquitetura & Padrões

Este projeto implementa uma **Arquitetura em Camadas Limpa** com clara separação de responsabilidades:

```
┌─────────────────────────────────────────────────────────┐
│                   Cliente (HTTP)                        │
└────────────────────────┬────────────────────────────────┘
                         │
              ┌──────────▼──────────┐
              │   Camada Controller │ ◄── DTOs Request/Response
              │  (Lógica HTTP/REST) │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │   Camada Service    │ ◄── Lógica de Negócio
              │   (Validação, etc)  │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │ Camada Repository   │ ◄── Acesso a Dados (JPA)
              │   (Persistência)    │
              └──────────┬──────────┘
                         │
              ┌──────────▼──────────┐
              │   Banco de Dados    │
              │   (H2 / PostgreSQL) │
              └─────────────────────┘

         Componentes de Suporte:
         ├── Mappers: Conversão DTO ↔ Entity
         ├── Exception Handlers: Respostas de erro globais (RFC 7807)
         ├── Filtros de Segurança: Autenticação & Autorização
         └── Configuração: Swagger, JPA, Profiles
```

### 📦 Estrutura de Pacotes & Responsabilidades

A aplicação segue uma organização **modular por feature**, promovendo alta coesão e baixo acoplamento:

```
src/main/java/br/com/pablotzeliks/todolist/
│
├── 📋 task/                          # Módulo de Gerenciamento de Tarefas
│   ├── controller/                   # Endpoints REST para tarefas
│   │   └── TaskController.java      # POST /create, GET /list, PUT /update/{id}
│   ├── service/                      # Lógica de negócio & validação
│   │   └── TaskService.java         # Operações CRUD de tarefas, checks de autorização
│   ├── repository/                   # Camada de acesso a dados
│   │   └── ITaskRepository.java     # Interface de repositório JPA
│   ├── model/                        # Entidades de domínio
│   │   ├── Task.java                # Entidade JPA com relacionamentos
│   │   └── Priority.java            # Enum (LOW, MEDIUM, HIGH, URGENT)
│   ├── dto/                          # Data Transfer Objects
│   │   ├── TaskRequestDTO.java      # Payload de requisição para criação
│   │   ├── TaskUpdateDTO.java       # Payload de requisição para atualizações
│   │   └── TaskResponseDTO.java     # Payload de resposta
│   └── mapper/                       # Conversão DTO ↔ Entity
│       └── TaskMapper.java          # Lógica de mapeamento manual
│
├── 👤 user/                          # Módulo de Gerenciamento de Usuários
│   ├── controller/                   # Endpoints REST para usuários
│   │   └── UserController.java      # POST /create
│   ├── service/                      # Lógica de negócio & hashing de senha
│   │   └── UserService.java         # Criação de usuários, integração BCrypt
│   ├── repository/                   # Camada de acesso a dados
│   │   └── IUserRepository.java     # Repositório JPA com queries customizadas
│   ├── model/                        # Entidades de domínio
│   │   └── User.java                # Entidade JPA
│   ├── dto/                          # Data Transfer Objects
│   │   ├── UserRequestDTO.java      # Payload de requisição
│   │   └── UserResponseDTO.java     # Payload de resposta (sem senha)
│   ├── mapper/                       # Conversão DTO ↔ Entity
│   │   └── UserMapper.java          # Lógica de mapeamento manual
│   └── exception/                    # Exceções específicas de usuário
│       └── UserNotAuthorizedException.java
│
├── 🛡️ exception/                     # Tratamento Global de Exceções
│   ├── GlobalExceptionHandler.java  # @ControllerAdvice para todas as exceções
│   ├── dto/                          # DTOs de resposta de erro
│   │   ├── ErrorResponseDTO.java    # Estrutura de erro compatível com RFC 7807
│   │   └── ValidationErrorDTO.java  # Erros de validação de campo
│   └── general/                      # Classes de exceção reutilizáveis
│       ├── ResourceNotFoundException.java
│       ├── ResourceAlreadyExistsException.java
│       ├── BusinessRuleException.java
│       └── AuthenticationException.java
│
├── 🔒 security/                      # Segurança & Autenticação
│   └── FilterTaskAuth.java          # Filtro customizado para validação Basic Auth
│
└── ⚙️ config/                        # Configuração da Aplicação
    └── SwaggerConfig.java            # Configuração de documentação OpenAPI/Swagger
```

### Padrões de Design Principais

- **Padrão DTO**: Objetos de Request/Response desacoplam contratos da API de modelos internos, prevenindo sobre-exposição e acoplamento forte
- **Padrão Mapper**: Classes dedicadas lidam com transformações de objetos, centralizando a lógica de conversão
- **Padrão Repository**: Repositórios JPA abstraem o acesso a dados, fornecendo uma separação limpa da lógica de negócio
- **Tratamento Global de Exceções**: `@ControllerAdvice` centralizado garante respostas de erro consistentes em todos os endpoints (RFC 7807)
- **Padrão Filter Chain**: Filtro de segurança customizado (`FilterTaskAuth`) valida autenticação antes de alcançar os controllers

---

## 🚀 Começando

### Pré-requisitos

- **Java 17** ou superior
- **Maven 3.6+** (ou use o wrapper incluído)
- **Docker** (opcional, para deployment containerizado)
- **PostgreSQL** (necessário apenas para perfil prod)

### Opção 1: Início Rápido (Modo Dev - Recomendado para Desenvolvimento Local)

```bash
# Clonar o repositório
git clone https://github.com/PabloTzeliks/todolist.git
cd todolist

# Executar com banco de dados H2 embarcado (perfil dev é padrão)
./mvnw spring-boot:run

# Acessar a aplicação
# URL Base da API: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui/index.html
# Console H2: http://localhost:8080/h2-console
```

### Opção 2: Modo Produção com PostgreSQL

```bash
# Garantir que o PostgreSQL está rodando e configurar variáveis de ambiente
export DATABASE_URL=jdbc:postgresql://localhost:5432/todolist_db
export DATABASE_USERNAME=seu_usuario
export DATABASE_PASSWORD=sua_senha

# Executar com perfil prod
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Opção 3: Deployment com Docker

```bash
# Construir e executar com Docker
docker build -t todolist-api .
docker run -p 8080:8080 todolist-api

# Ou usar docker-compose
docker-compose up
```

### Executando Testes

```bash
# Executar todos os testes unitários
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

---

## 📡 Endpoints da API

### 👤 Gerenciamento de Usuários

#### **POST** `/users/create` - Registrar um novo usuário
**Autenticação**: ❌ Não requerida (endpoint público)

**Corpo da Requisição**:
```json
{
  "name": "João Silva",
  "username": "joao.silva",
  "password": "securepass123"
}
```

**Validações**:
- `name`: Obrigatório, não pode estar em branco
- `username`: Obrigatório, não pode estar em branco, deve ser único
- `password`: Obrigatório, 6-20 caracteres

**Resposta de Sucesso (201 Created)**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "username": "joao.silva",
  "createdAt": "2025-12-22T10:30:00"
}
```

**Resposta de Erro (409 Conflict)** - Usuário já existe:
```json
{
  "message": "User already exists",
  "status": 409,
  "statusError": "Conflict"
}
```

---

### 📋 Gerenciamento de Tarefas

Todos os endpoints de tarefas requerem **Autenticação HTTP Basic** com username e password.

#### **POST** `/tasks/create` - Criar uma nova tarefa
**Autenticação**: ✅ Requerida (Basic Auth)

**Headers da Requisição**:
```
Authorization: Basic am9hby5zaWx2YTpzZWN1cmVwYXNzMTIz
```

**Corpo da Requisição**:
```json
{
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de Spring Data JPA e melhores práticas",
  "startAt": "2025-12-23T09:00:00",
  "endAt": "2025-12-23T11:00:00",
  "priority": "HIGH"
}
```

**Validações**:
- `title`: Obrigatório, máximo 50 caracteres
- `description`: Opcional, máximo 255 caracteres
- `startAt`: Obrigatório, deve ser data presente ou futura
- `endAt`: Obrigatório, deve ser data futura
- `priority`: Enum (`LOW`, `MEDIUM`, `HIGH`, `URGENT`)

**Resposta de Sucesso (201 Created)**:
```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "title": "Estudar Spring Boot",
  "description": "Revisar conceitos de Spring Data JPA e melhores práticas",
  "startAt": "2025-12-23T09:00:00",
  "endAt": "2025-12-23T11:00:00",
  "priority": "HIGH",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2025-12-22T10:35:00",
  "updatedAt": "2025-12-22T10:35:00"
}
```

---

#### **GET** `/tasks/list` - Listar todas as tarefas do usuário
**Autenticação**: ✅ Requerida (Basic Auth)

**Headers da Requisição**:
```
Authorization: Basic am9hby5zaWx2YTpzZWN1cmVwYXNzMTIz
```

**Resposta de Sucesso (200 OK)**:
```json
[
  {
    "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
    "title": "Estudar Spring Boot",
    "description": "Revisar conceitos de Spring Data JPA e melhores práticas",
    "startAt": "2025-12-23T09:00:00",
    "endAt": "2025-12-23T11:00:00",
    "priority": "HIGH",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "createdAt": "2025-12-22T10:35:00",
    "updatedAt": "2025-12-22T10:35:00"
  },
  {
    "id": "8d0f7780-8536-51ef-b058-f18ed2e01bf8",
    "title": "Reunião do Projeto",
    "description": "Discutir funcionalidades da v3.0.0 e roadmap",
    "startAt": "2025-12-24T14:00:00",
    "endAt": "2025-12-24T15:30:00",
    "priority": "URGENT",
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "createdAt": "2025-12-22T11:00:00",
    "updatedAt": "2025-12-22T11:00:00"
  }
]
```

**Resposta de Lista Vazia (200 OK)**:
```json
[]
```

---

#### **PUT** `/tasks/update/{id}` - Atualizar uma tarefa existente
**Autenticação**: ✅ Requerida (Basic Auth)

**Parâmetros da URL**:
- `id`: UUID da tarefa a ser atualizada (ex: `7c9e6679-7425-40de-944b-e07fc1f90ae7`)

**Headers da Requisição**:
```
Authorization: Basic am9hby5zaWx2YTpzZWN1cmVwYXNzMTIz
```

**Corpo da Requisição** (todos os campos opcionais - atualização parcial):
```json
{
  "title": "Estudar Spring Boot & Security",
  "description": "Revisar Spring Data JPA e Spring Security",
  "priority": "URGENT"
}
```

**Validações**:
- `title`: Opcional, máximo 50 caracteres
- `description`: Opcional, máximo 255 caracteres
- `startAt`: Opcional, deve ser data presente ou futura
- `endAt`: Opcional, deve ser data futura
- `priority`: Opcional, enum (`LOW`, `MEDIUM`, `HIGH`, `URGENT`)

**Resposta de Sucesso (200 OK)**:
```json
{
  "id": "7c9e6679-7425-40de-944b-e07fc1f90ae7",
  "title": "Estudar Spring Boot & Security",
  "description": "Revisar Spring Data JPA e Spring Security",
  "startAt": "2025-12-23T09:00:00",
  "endAt": "2025-12-23T11:00:00",
  "priority": "URGENT",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "createdAt": "2025-12-22T10:35:00",
  "updatedAt": "2025-12-22T11:15:00"
}
```

**Resposta de Erro (404 Not Found)** - Tarefa não existe:
```json
{
  "message": "Task not found",
  "status": 404,
  "statusError": "Not Found"
}
```

**Resposta de Erro (403 Forbidden)** - Usuário não é dono da tarefa:
```json
{
  "message": "User not authorized to access this resource",
  "status": 403,
  "statusError": "Forbidden"
}
```

---

### 📚 Documentação
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html` - Explorador interativo da API com testes ao vivo
- **OpenAPI JSON**: `http://localhost:8080/api-docs` - Especificação da API legível por máquina

---

## 🛡️ Segurança & Tratamento de Erros

### Autenticação
- **BCrypt** para hashing de senha com armazenamento seguro de credenciais
- **Autenticação HTTP Basic** para endpoints protegidos

### Respostas de Erro

Todos os erros seguem **RFC 7807 (Problem Details for HTTP APIs)** para respostas consistentes e legíveis por máquina:

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

**Benefícios**: Erros estruturados simplificam integração com frontend e debug da API.

---

## 🛠️ Stack Tecnológica

### Tecnologias Core
- **Java 17** - Versão LTS moderna com recursos de linguagem aprimorados
- **Spring Boot 3.4.0** - Framework mais recente para desenvolvimento rápido
- **Spring Data JPA** - Acesso a dados simplificado com Hibernate ORM

### Suporte a Banco de Dados
- **H2 Database** - Banco de dados em memória para dev/testes
- **PostgreSQL** - RDBMS de nível produção

### Segurança & Validação
- **BCrypt** - Hashing de senha padrão da indústria
- **Bean Validation (JSR 380)** - Validação declarativa de entrada

### Documentação & Testes
- **SpringDoc OpenAPI** - Documentação automática da API (Swagger UI)
- **JUnit 5** - Framework de testes moderno
- **Mockito** - Framework de mocking para testes unitários

### DevOps & Ferramentas
- **GitHub Actions** - Pipeline de Integração Contínua
- **Docker** - Containerização para deployments consistentes
- **Lombok** - Reduz código boilerplate
- **Maven** - Gerenciamento de dependências e automação de build

---

## 👨‍💻 Autor

**Pablo Ruan Tzeliks**

Desenvolvedor de Software | Entusiasta de Tecnologia | Aprendizado Continuo

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/pablo-ruan-tzeliks/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/PabloTzeliks)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:arq.pabloo@gmail.com)

### 🎓 Origens

Este projeto originou-se do [Curso de Java da Rocketseat](https://www.rocketseat.com.br/), evoluindo muito além do seu escopo inicial para demonstrar práticas de engenharia de nível enterprise.

<details>
<summary>Ver Certificado de Conclusão</summary>

<img width="1024" alt="Certificado Java Rocketseat" src="https://github.com/user-attachments/assets/15a56c67-9a1f-4166-924f-2e332ebdd1ff" />

</details>

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT** - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

<p align="center">
  <sub>Construído com ❤️ e ☕ por <a href="https://github.com/PabloTzeliks">Pablo Ruan Tzeliks</a></sub>
  <br/>
  <sub>De projeto educacional a projeto de portfólio de nível enterprise 🚀</sub>
</p>
