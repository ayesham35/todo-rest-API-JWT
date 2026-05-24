# Todo REST API

A secure REST API for managing personal todos, built with Spring Boot and JWT authentication.

## Tech Stack

- Java 25
- Spring Boot 4.0.6
- Spring Web (REST controllers)
- Spring Security 7.0.x
- Spring Data JPA
- jjwt 0.13.x (io.jsonwebtoken)
- H2 In-Memory Database
- Lombok
- Bean Validation (Jakarta EE 11)

## Features

- User registration and login
- JWT token authentication
- Password hashing with BCrypt
- Create, read, update, and delete todos
- Users can only access their own todos
- Input validation on all requests

## How to Run

1. Clone the repository
2. Open in IntelliJ IDEA
3. Run `TodoApiApplication.java`
4. API runs at `http://localhost:8080`

## Authentication

Register and login via the auth endpoints to receive a JWT token.
Include the token in all todo requests:

```
Authorization: Bearer <your_token_here>
```

## Endpoints

| Method | Endpoint | Auth Required |
|---|---|---|
| POST | `/api/auth/register` | No |
| POST | `/api/auth/login` | No |
| GET | `/api/todos` | Yes |
| POST | `/api/todos` | Yes |
| PUT | `/api/todos/{id}` | Yes |
| DELETE | `/api/todos/{id}` | Yes |
