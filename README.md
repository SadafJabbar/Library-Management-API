# Library Management API

A RESTful Library Management API built using **Java and Spring Boot**. The application manages books, categories, users, and loans with MySQL database integration.

## Technologies Used

* Java 21
* Spring Boot
* Spring Web MVC
* Spring Data JPA / Hibernate
* MySQL
* Spring Cache
* Caffeine
* Docker
* Maven
* JUnit 5
* Mockito
* JaCoCo
* Spring AOP

## Features

* Manage books
* Manage categories
* Manage users
* Manage loans
* CRUD operations
* DTO pattern
* Exception handling
* Request validation
* MySQL database integration
* Caffeine caching
* Cache expiration using TTL
* Cache eviction on data updates and deletion
* Aspect-Oriented Programming (AOP)
* Unit testing
* Controller testing
* Integration testing
* Code coverage with JaCoCo

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
integration tests
```

## Database Configuration

The application uses MySQL as the database.

Database connection details are configured through application properties and should be provided according to the local environment.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
spring.datasource.username=<username>
spring.datasource.password=<password>

```
## Features

- CRUD operations for books, categories, users, and loans
- DTO pattern and object mapping
- Centralized exception handling and request validation
- MySQL database integration
- Database schema migrations using Flyway
- Caffeine caching with TTL and cache eviction
- Aspect-Oriented Programming (AOP)
- Unit, controller, and integration testing
- Code coverage with JaCoCo
- Load testing using Apache JMeter
- JWT-based authentication using Spring Security
- Access and refresh token management
- Token validation, expiration, and revocation
- Role-based authorization for administrators and members
- Protected endpoints and user-specific access control

## Authentication and Authorization (In Progress)

JWT-based authentication and authorization are being implemented using Spring Security.

Current work includes:

- User login and JWT access token generation
- Refresh token management and access token renewal
- Token validation and expiration handling
- Logout with access and refresh token revocation
- Role-based access control for administrators and members
- Securing API endpoints based on authentication and authorization rules

This section will be updated as additional security features and tests are completed.
## Caching

The application uses **Spring Cache with Caffeine** to reduce unnecessary database queries.

Frequently requested resources are cached using `@Cacheable`. Cache entries are removed when the corresponding data is updated or deleted using `@CacheEvict`.

Caching is implemented for:

* Books
* Users
* Categories
* Loans

The Caffeine cache is configured with:

* Automatic expiration using TTL
* Maximum cache size

This allows repeated requests for the same resource to be served from the cache instead of querying the database every time.

## Running the Application

### Clone the repository

```bash
git clone <repository-url>
```

### Start MySQL using Docker

Create a MySQL container with your own database credentials:

```bash
docker run --name mysql-docker \
-e MYSQL_ROOT_PASSWORD=<your-password> \
-e MYSQL_DATABASE=librarydb \
-p 3306:3306 \
-d mysql
```

### Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

## Testing

The project includes automated tests for the application's main layers.

Completed testing includes:

* Controller tests
* Mapper tests

Service-layer unit tests are the remaining tests to be completed.

Run the existing tests with:

```bash
./mvnw clean test
```

## Code Coverage

JaCoCo is configured to generate test coverage reports.

Generate the report:

```bash
./mvnw clean test
```

The report can be found at:

```text
target/site/jacoco/index.html
```

## Aspect-Oriented Programming

Spring AOP is used to separate cross-cutting concerns from the main application logic.

The project includes an `aspect` layer for handling concerns that can be applied across multiple parts of the application.

## Load Testing

Load tests are created using **Apache JMeter**.

### How to Run

1. Open the `.jmx` file from the `load-testing` folder in **Apache JMeter**.
2. Select the required test:
   - **Normal Load**
   - **Load Test**
   - **Stress Test**
   - **Spike Test**
3. Run the test.
4. View the results using **Summary Report** or **Aggregate Report**.
## API Endpoints

Example book endpoints:

```text
GET    /api/v1/books
POST   /api/v1/books
PUT    /api/v1/books/{id}
DELETE /api/v1/books/{id}
```

## Author

Sadaf Jabbar
