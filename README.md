# Atviv Automanager

Sistema de gerenciamento desenvolvido com **Spring Boot** e autenticação baseada em **JWT**, contendo CRUDs organizados por diferentes perfis de acesso (Administrador, Gerente, Vendedor e Cliente).

## 🚀 Tecnologias Utilizadas

- **Java**: Linguagem principal para o backend.
- **Spring Boot**: Framework para construção do backend.
- **Spring Security**: Autenticação e autorização de usuários.
- **JWT**: Token para autenticação e proteção de rotas.
- **Hibernate/JPA**: Integração e persistência no banco de dados.
- **MySQL**: Banco de dados relacional.
- **Lombok**: Para simplificação do código (getters, setters, etc.).
- **Postman/Insomnia**: Ferramentas para teste dos endpoints da API.

---

## 🎯 Funcionalidades

- **Autenticação e Autorização com JWT**:
  - Login e geração de token JWT.
  - Proteção de rotas com validação do token.
- **Perfis de Acesso**:
  - **Administrador (ADMIN)**: Controle total do sistema.
  - **Gerente (MANAGER)**: Gerenciamento de vendedores e usuários.
  - **Vendedor (SELLER)**: Controle de clientes e vendas.
  - **Cliente**: Acesso restrito às informações pessoais.
- **CRUDs**:
  - Cadastro, consulta, atualização e exclusão de dados para:
    - Usuários
    - Clientes
    - Gerentes
    - Vendedores
- **Configuração de segurança**:
  - Habilitação de CORS para o frontend.
  - Controle de permissões nas rotas.

---

## 🔧 Como Configurar e Executar

### Pré-requisitos

- [Java 11+](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/)
- [Postman ou Insomnia](https://insomnia.rest/download) (para teste dos endpoints)

### Passos para configurar:

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/DiogoPalharini/atviv-autobots.git
   cd atviv-autobots
   ```

Compile o projeto:
```bash
mvn clean install
```
Execute a aplicação:
```bash

mvn spring-boot:run
```
Acesse a aplicação:

A API estará disponível em: http://localhost:8080
🧪 Endpoints Disponíveis
Autenticação
POST /auth/login: Realiza o login e retorna o token JWT.
```bash
{
  "nomeUsuario": "admin",
  "senha": "123456"
}
```
### Usuários

#### **GET /usuarios/listar**
- **Descrição**: Lista todos os usuários cadastrados.
- **Acesso**: Apenas **ADMIN** e **MANAGER**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Resposta (200 OK):
    ```json
    [
      {
        "id": 1,
        "nome": "Administrador",
        "credencial": {
          "id": 1,
          "nomeUsuario": "admin",
          "senha": "$2a$10$hashedSenha"
        },
        "perfis": ["ROLE_ADMIN"]
      }
    ]
    ```

#### **POST /usuarios/criar**
- **Descrição**: Cria um novo usuário.
- **Acesso**: Apenas **ADMIN**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Corpo:
    ```json
    {
      "nome": "Novo Usuário",
      "credencial": {
        "nomeUsuario": "novoUsuario",
        "senha": "senha123"
      },
      "perfis": ["ROLE_MANAGER"]
    }
    ```
  - Resposta (201 Created):
    ```json
    {
      "mensagem": "Usuário criado com sucesso"
    }
    ```

---

### Clientes

#### **GET /clientes/listar**
- **Descrição**: Lista todos os clientes cadastrados.
- **Acesso**: Apenas **ADMIN**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Resposta (200 OK):
    ```json
    [
      {
        "id": 1,
        "nome": "Cliente 1",
        "email": "cliente1@exemplo.com",
        "telefone": "99999-9999"
      }
    ]
    ```

#### **POST /clientes/criar**
- **Descrição**: Cria um novo cliente.
- **Acesso**: Apenas **ADMIN**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Corpo:
    ```json
    {
      "nome": "Cliente 1",
      "email": "cliente1@exemplo.com",
      "telefone": "99999-9999"
    }
    ```
  - Resposta (201 Created):
    ```json
    {
      "mensagem": "Cliente criado com sucesso"
    }
    ```

---

### Gerentes

#### **GET /gerentes/listar**
- **Descrição**: Lista todos os gerentes cadastrados.
- **Acesso**: Apenas **ADMIN**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Resposta (200 OK):
    ```json
    [
      {
        "id": 1,
        "nome": "Gerente 1",
        "email": "gerente1@exemplo.com",
        "telefone": "88888-8888"
      }
    ]
    ```

#### **POST /gerentes/criar**
- **Descrição**: Cria um novo gerente.
- **Acesso**: Apenas **ADMIN**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Corpo:
    ```json
    {
      "nome": "Gerente 1",
      "email": "gerente1@exemplo.com",
      "telefone": "88888-8888"
    }
    ```
  - Resposta (201 Created):
    ```json
    {
      "mensagem": "Gerente criado com sucesso"
    }
    ```

---

### Vendedores

#### **GET /vendedores/listar**
- **Descrição**: Lista todos os vendedores cadastrados.
- **Acesso**: Apenas **MANAGER**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Resposta (200 OK):
    ```json
    [
      {
        "id": 1,
        "nome": "Vendedor 1",
        "email": "vendedor1@exemplo.com",
        "telefone": "77777-7777"
      }
    ]
    ```

#### **POST /vendedores/criar**
- **Descrição**: Cria um novo vendedor.
- **Acesso**: Apenas **MANAGER**.
- **Exemplo de Requisição**:
  - Headers:
    ```
    Authorization: Bearer <SEU_TOKEN_JWT>
    ```
  - Corpo:
    ```json
    {
      "nome": "Vendedor 1",
      "email": "vendedor1@exemplo.com",
      "telefone": "77777-7777"
    }
    ```
  - Resposta (201 Created):
    ```json
    {
      "mensagem": "Vendedor criado com sucesso"
    }
    ```

---

### Permissões por Perfil

| Rota                   | ADMIN | MANAGER | SELLER | CLIENTE |
|------------------------|-------|---------|--------|---------|
| `/auth/login`          | ✅    | ✅      | ✅     | ✅      |
| `/usuarios/listar`     | ✅    | ✅      | ❌     | ❌      |
| `/clientes/listar`     | ✅    | ❌      | ❌     | ❌      |
| `/gerentes/listar`     | ✅    | ❌      | ❌     | ❌      |
| `/vendedores/listar`   | ❌    | ✅      | ❌     | ❌      |
