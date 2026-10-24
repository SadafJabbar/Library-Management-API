# Library Management API

A RESTful Library Management API built using **Java and Spring Boot**. The application manages books, categories, users, and loans with MySQL database integration, caching, and JWT-based authentication and authorization.

## Technologies Used

- Java 21
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL
- Spring Cache
- Caffeine
- Docker
- Maven
- JUnit 5
- Mockito
- JaCoCo
- Spring AOP
- Flyway
- Springdoc OpenAPI / Swagger

## Features

- Manage books, categories, users, and loans
- CRUD operations
- DTO pattern and object mapping
- Centralized exception handling
- Request validation
- MySQL database integration
- Database schema migrations using Flyway
- Caffeine caching with TTL and cache eviction
- Aspect-Oriented Programming (AOP)
- Unit, controller, and integration testing
- Code coverage with JaCoCo
- JWT-based authentication
- Role-based authorization using Spring Security
- Password hashing with BCrypt
- Access-token validation and authentication filtering
- Refresh-token support for obtaining new access tokens
- Refresh-token persistence and revocation
- Stateless session management
- Protected endpoints based on user roles and permissions

## Project Structure

```text
controller
service
repository
entity
dto
mapper
exception
aspect
configuration
security
integration tests
```

## Database Configuration

The application uses MySQL. Configure database connection details in `application.properties` or through environment variables according to your local environment.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

## Authentication and Authorization

The API uses **Spring Security and JWT** to secure protected endpoints.

- **Authentication:** Users authenticate with their credentials to obtain an access token.
- **JWT validation:** Incoming requests are checked by a security filter that validates the access token.
- **Role-based access control:** Access to protected endpoints is restricted according to user roles and configured authorization rules.
- **Password security:** Passwords are hashed using BCrypt rather than stored as plain text.
- **Refresh tokens:** Refresh tokens can be used to obtain new access tokens without requiring the user to log in again each time.
- **Token persistence and revocation:** Refresh-token records are stored in the database, supporting expiration and revocation.
- **Stateless security:** The application uses stateless session management.

Authentication and refresh-token endpoints are configured to be publicly accessible as required, while other endpoints are protected according to the security configuration.

## Caching

The application uses **Spring Cache with Caffeine** to reduce unnecessary database queries.

Frequently requested resources are cached using `@Cacheable`. Cache entries are evicted or refreshed when corresponding data is updated or deleted.

Caching is implemented for:

- Books
- Users
- Categories
- Loans

The cache configuration includes automatic expiration through TTL and a maximum cache size.

## Running the Application

### 1. Clone the repository

```bash
git clone <https://github.com/SadafJabbar/Library-Management-API.git>
```

### 2. Start MySQL using Docker

Create a MySQL container with your own database credentials:

```bash
docker run --name mysql-docker \
  -e MYSQL_ROOT_PASSWORD=<your-password> \
  -e MYSQL_DATABASE=library_db \
  -p 3306:3306 \
  -d mysql
```

### 3. Configure the application

Set your database credentials and any required JWT configuration through your application properties or environment variables.

Do not commit real passwords, JWT secrets, or other sensitive configuration values to version control.

### 4. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

## Testing

The project includes automated tests for different application layers.

Completed testing includes:(yet to update after feature addition)

- Controller tests
- Mapper tests
- ExceptionHanlder
-Service-layer

Run the existing tests with:

```bash
./mvnw clean test
```

## Code Coverage

JaCoCo is configured to generate test coverage reports.

Run:

```bash
./mvnw clean test
```

The report is generated at:

```text
target/site/jacoco/index.html
```

## Aspect-Oriented Programming

Spring AOP is used to separate cross-cutting concerns from the main application logic. The project includes an aspect layer for concerns that apply across different parts of the application.

## Load Testing

Load tests are created using **Apache JMeter**.

### How to Run

1. Open a `.jmx` file from the `load-testing` folder in Apache JMeter.
2. Select the required test:
   - Normal Load
   - Load Test
   - Stress Test
   - Spike Test
3. Run the test.
4. Review the results using Summary Report or Aggregate Report.

## API Endpoints

Example book endpoints:

```text
GET    /api/v1/books
POST   /api/v1/books
PUT    /api/v1/books/{id}
DELETE /api/v1/books/{id}
```

Authentication and refresh-token endpoints are available under the configured authentication and refresh routes.

## API Documentation

If Springdoc OpenAPI is enabled, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Author

Sadaf Jabbar
