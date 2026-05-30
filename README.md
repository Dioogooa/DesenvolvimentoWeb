# 🐾 Pet Shop API

API REST para e-commerce de produtos para pets, desenvolvida com Spring Boot 3 e PostgreSQL.

## 📋 Índice

- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Como Executar](#como-executar)
- [Documentação dos Endpoints](#documentação-dos-endpoints)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Exemplos de Uso](#exemplos-de-uso)

---

## 🛠 Tecnologias

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA / Hibernate
- PostgreSQL 15+
- Maven
- Lombok
- Swagger / OpenAPI 3 (springdoc-openapi 2.5.0)

---

## ✅ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [JDK 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [PostgreSQL 15+](https://www.postgresql.org/download/)

---

## 🗄 Configuração do Banco de Dados

### 1. Criar o banco de dados

Acesse o PostgreSQL via psql ou pgAdmin e execute:

```sql
CREATE DATABASE petshop_db;
```

> O Hibernate cria todas as tabelas automaticamente na primeira execução (`ddl-auto=update`).

### 2. Configurar credenciais

Edite o arquivo `src/main/resources/application.properties` com suas credenciais:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/petshop_db
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

---

## ▶️ Como Executar

### Via Maven

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/petshop-api.git
cd petshop-api

# Compilar e executar
mvn spring-boot:run
```

### Via JAR

```bash
# Gerar o JAR
mvn clean package -DskipTests

# Executar
java -jar target/api-1.0.0.jar
```

A aplicação estará disponível em: **http://localhost:8080**

---

## 📖 Documentação dos Endpoints

Com a aplicação rodando, acesse o Swagger UI:

**http://localhost:8080/swagger-ui.html**

### Resumo dos endpoints

#### 📁 Categorias — `/api/categories`
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/categories` | Listar todas as categorias |
| GET | `/api/categories/{id}` | Buscar categoria por ID |
| POST | `/api/categories` | Criar nova categoria |
| PUT | `/api/categories/{id}` | Atualizar categoria |
| DELETE | `/api/categories/{id}` | Excluir categoria |

#### 📦 Produtos — `/api/products`
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/products` | Listar produtos ativos |
| GET | `/api/products?search=ração` | Buscar produto por nome |
| GET | `/api/products?categoryId=1` | Filtrar por categoria |
| GET | `/api/products/{id}` | Buscar produto por ID |
| POST | `/api/products` | Cadastrar produto |
| PUT | `/api/products/{id}` | Atualizar produto |
| DELETE | `/api/products/{id}` | Desativar produto (soft delete) |

#### 👤 Clientes — `/api/customers`
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/customers` | Listar todos os clientes |
| GET | `/api/customers/{id}` | Buscar cliente por ID |
| POST | `/api/customers` | Cadastrar cliente |
| PUT | `/api/customers/{id}` | Atualizar cliente |
| DELETE | `/api/customers/{id}` | Excluir cliente |

#### 🛒 Pedidos — `/api/orders`
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/orders` | Listar todos os pedidos |
| GET | `/api/orders?customerId=1` | Pedidos de um cliente |
| GET | `/api/orders/{id}` | Buscar pedido completo por ID |
| POST | `/api/orders` | Criar pedido |
| PATCH | `/api/orders/{id}/status` | Atualizar status do pedido |

#### Fluxo de status do pedido:
```
PENDING → CONFIRMED → SHIPPED → DELIVERED
                ↘         ↘        (bloqueado)
                      CANCELLED
```

---

## 🗂 Estrutura do Projeto

```
src/
└── main/
    └── java/com/petshop/api/
        ├── config/
        │   └── OpenApiConfig.java
        ├── controller/
        │   ├── CategoryController.java
        │   ├── CustomerController.java
        │   ├── OrderController.java
        │   └── ProductController.java
        ├── dto/
        │   ├── request/
        │   │   ├── CategoryRequest.java
        │   │   ├── CustomerRequest.java
        │   │   ├── OrderItemRequest.java
        │   │   ├── OrderRequest.java
        │   │   ├── OrderStatusRequest.java
        │   │   └── ProductRequest.java
        │   └── response/
        │       ├── CategoryResponse.java
        │       ├── CustomerResponse.java
        │       ├── OrderItemResponse.java
        │       ├── OrderResponse.java
        │       └── ProductResponse.java
        ├── exception/
        │   ├── BusinessException.java
        │   ├── ErrorResponse.java
        │   ├── GlobalExceptionHandler.java
        │   └── ResourceNotFoundException.java
        ├── model/
        │   ├── Category.java
        │   ├── Customer.java
        │   ├── Order.java
        │   ├── OrderItem.java
        │   ├── OrderStatus.java
        │   └── Product.java
        ├── repository/
        │   ├── CategoryRepository.java
        │   ├── CustomerRepository.java
        │   ├── OrderItemRepository.java
        │   ├── OrderRepository.java
        │   └── ProductRepository.java
        ├── service/
        │   ├── CategoryService.java
        │   ├── CustomerService.java
        │   ├── OrderService.java
        │   └── ProductService.java
        └── ApiApplication.java
    └── resources/
        └── application.properties
```

---

## 🧪 Exemplos de Uso

### Criar uma categoria
```http
POST /api/categories
Content-Type: application/json

{
  "name": "Alimentação",
  "description": "Rações, petiscos e suplementos"
}
```

### Cadastrar um produto
```http
POST /api/products
Content-Type: application/json

{
  "name": "Ração Golden Adulto 15kg",
  "description": "Ração premium para cães adultos",
  "price": 189.90,
  "stock": 50,
  "categoryId": 1
}
```

### Cadastrar um cliente
```http
POST /api/customers
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "cpf": "12345678901",
  "phone": "62999998888",
  "address": "Rua das Flores, 123 - Goiânia/GO"
}
```

### Criar um pedido
```http
POST /api/orders
Content-Type: application/json

{
  "customerId": 1,
  "notes": "Entregar no período da tarde",
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

### Atualizar status do pedido
```http
PATCH /api/orders/1/status
Content-Type: application/json

{
  "status": "CONFIRMED"
}
```

---

## ⚠️ Códigos de Resposta

| Código | Descrição |
|--------|-----------|
| 200 | Sucesso |
| 201 | Criado com sucesso |
| 204 | Excluído com sucesso (sem corpo) |
| 400 | Dados inválidos (erros de validação) |
| 404 | Recurso não encontrado |
| 422 | Erro de regra de negócio |
| 500 | Erro interno do servidor |

---

## 👨‍💻 Autor

Desenvolvido como projeto acadêmico para a disciplina de desenvolvimento de APIs REST com Spring Boot.
