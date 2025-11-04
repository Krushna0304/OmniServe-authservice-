# OmniServe — auth-service

A small Spring Boot authentication microservice for OmniServe. It provides endpoints to create users and authenticate (login). This repository contains the `auth-service` application and a `Common/` module used by the project.

## Tech stack

- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL (runtime)
- Maven (wrapper included)

## Quick facts

- Main application: `com.order.authservice.AuthServiceApplication`
- REST endpoints:
  - POST `/create-user` — create a new user
  - POST `/login` — login/authenticate a user
- Default HTTP port: 8080 (Spring Boot default)

## Prerequisites

- JDK 21 installed and JAVA_HOME set
- PostgreSQL running (or adjust datasource to use another DB)

## Default configuration (from `src/main/resources/application.yml`)

The project ships with an example datasource configuration. Do NOT use these credentials in production — they are for local development only.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/omniservedb
    username: postgres
    password: Admin@123
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
```

This configuration will attempt to create the database schema on startup (`ddl-auto: create`). If you prefer to manage schema migrations, change `ddl-auto` to `validate` or `none` and use a migration tool (Flyway/Liquibase).

You can override these values with environment variables (recommended):

- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD

Or set a custom `application-<profile>.yml` and pass `--spring.profiles.active=<profile>` on start.

## Build and run (Windows PowerShell)

Using the included Maven wrapper (recommended):

```powershell
# Run the app directly
.\mvnw.cmd spring-boot:run

# Build (skip tests) and run the packaged app
.\mvnw.cmd -DskipTests package
java -jar target\*.jar

# Run tests
.\mvnw.cmd test
```

Note: Running `spring-boot:run` uses the project sources directly and does not require creating an artifact first.

## API — Endpoints and examples

1) Create user

- URL: POST /create-user
- Request JSON body:

```json
{
  "username": "alice",
  "password": "secret",
  "userRole": "USER"
}
```

- Success response: 201 CREATED with the created user DTO

Example using curl (Linux/macOS) or Windows with curl available:

```bash
curl -X POST http://localhost:8080/create-user \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret","userRole":"USER"}'
```

PowerShell (Invoke-RestMethod):

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/create-user -ContentType 'application/json' -Body (@{ username='alice'; password='secret'; userRole='USER' } | ConvertTo-Json)
```

2) Login

- URL: POST /login
- Request JSON body: same shape as create-user
- Success response: 200 OK with a string (currently returns "Login successful" on success)

Example:

```bash
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"username":"alice","password":"secret","userRole":"USER"}'
```

PowerShell:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/login -ContentType 'application/json' -Body (@{ username='alice'; password='secret'; userRole='USER' } | ConvertTo-Json)
```

Note: The current implementation returns a plain string "Login successful" on success. The code contains a placeholder comment where a JWT could be returned in future work.

## Data model

`UserCredentials` entity (fields):

- `userId` — generated UUID
- `username` — unique
- `password`
- `userRole` — enum (USER, SUPPLIER, CARRIER)

Passwords are stored as plain text in the current implementation. For any real application, hash passwords with a secure algorithm (BCrypt/Argon2) and never store plaintext passwords.

## Running with PostgreSQL (quick setup)

1. Create database (example using psql):

```powershell
psql -U postgres -c "CREATE DATABASE omniservedb;"
```

2. Ensure `application.yml` credentials match your DB, or export env vars before starting:

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/omniservedb'
$env:SPRING_DATASOURCE_USERNAME = 'postgres'
$env:SPRING_DATASOURCE_PASSWORD = 'Admin@123'
```

Then run the app with `.\mvnw.cmd spring-boot:run`.

## Tests

Run unit tests using:

```powershell
.\mvnw.cmd test
```

## To do / Suggestions

- Return a JWT on successful login and add token-based auth for endpoints
- Hash passwords (BCrypt) before saving
- Add integration tests that run against a test-containers PostgreSQL instance
- Add documentation for Common module and how it's used

## Contributing

Fork, create a feature branch, run tests, and open a PR. Follow the existing code style and add unit tests for new behavior.

## License

This repository does not specify a license. Add a LICENSE file to make the terms explicit.

---

If you'd like, I can commit this `README.md` to your repo for you, or adjust it (shorter/longer, more diagrams, remove sensitive defaults).
