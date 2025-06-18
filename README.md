# socialMediaApi
APIs for User Creation, Login, Post Creation, Like, Delete and Listing
# JWT-Based Social Media API Server (Spring Boot)

# JWT-Based API Server (Spring Boot)

This project implements a simple API server using **Java Spring Boot** that supports:

* User Registration & Login
* JWT-based Authentication
* In-memory data storage (no database)
* Basic Post operations (Create, List, Like, Delete)
* Logging

---

## ✅ Features

* Java Spring Boot REST API
* JWT (JSON Web Token) for secure authentication
* In-memory storage using `ConcurrentHashMap`
* Custom filter to validate JWT
* Single-file startup with embedded server
* Postman collection for testing

---

## 🛠 Tech Stack

* Java 17+
* Spring Boot 3.x
* Spring Security
* jjwt (JWT library)

---

## 📁 Project Structure

```plaintext
src/main/java/com/dev/SocialMediaApi
├── Controller
│   ├── UserController.java         # Handles user registration and login
│   └── PostController.java         # Handles all post-related endpoints
├── DTO
│   ├── LoginRequest.java           
│   ├── PostRequest.java
│   └── UserRequest.java
├── Model
│   ├── User.java                   # User model
│   └── Post.java                   # Post model
├── Security
│   └── SecurityConfig.java        # Security configuration using JWT filter
├── service
│   ├── AuthService.java           # Authentication service interface
│   └── PostService.java           # Post service interface
├── serviceImpl
│   ├── AuthServiceImpl.java       # Auth service implementation
│   └── PostServiceImpl.java       # Post service implementation
├── util
│   ├── JwtUtil.java               # JWT generation & validation
│   └── JwtAuthenticationFilter.java # Custom JWT filter implementation
└── SocialMediaApiApplication.java         # Main application starter
```

---

## 🚀 Running the Application

### Prerequisites:

* Java 17+
* Maven or Gradle

### Step 1: Clone & Run

```bash
./mvnw spring-boot:run
```

or

```bash
./gradlew bootRun
```

The server will run on `http://localhost:8080`

---

## 📫 API Endpoints

### 🔐 1. Register

* `POST /auth/register`
* Body:

```json
{
  "username": "arun",
  "password": "password123"
}
```

### 🔐 2. Login

* `POST /auth/login`
* Body:

```json
{
  "username": "arun",
  "password": "password123"
}
```

* Response:

```json
{
  "token": "<JWT_TOKEN>"
}
```

Add this token as:

```
Authorization: Bearer <JWT_TOKEN>
```

### 📝 3. Create Post

* `POST /posts`
* Body:

```json
{
  "content": "My first post!"
}
```

### 📄 4. List Posts

* `GET /posts`

### 👍 5. Like Post

* `POST /posts/{id}/like`

### ❌ 6. Delete Post

* `DELETE /posts/{id}`

---

## 🔎 Logging

All incoming requests, login attempts, and post actions are logged using `SLF4J` for better traceability.

---

## 🧪 Postman Collection

Import the included Postman collection file `jwt-api.postman_collection.json` to test all endpoints.

Steps:

1. Open Postman > Import
2. Select the `.json` file
3. Start testing from Register > Login > Use JWT > Create/List/Like/Delete Posts

---

## 📝 Comments & Documentation

* All classes and methods include **JavaDoc-style** and inline comments to explain logic.
* JWT secret is generated on app start using `Keys.secretKeyFor()` (Note: replace with a static secret for production).
* Error handling and validation are kept basic due to simplicity and in-memory design.

---

## 📦 Final Deliverables

* ✅ Complete Spring Boot Project with single entry point (`JwtApiApplication`)
* ✅ README documentation
* ✅ Logging integrated
* ✅ Postman Collection (`jwt-api.postman_collection.json`)
* ✅ Code includes inline comments for clarity
* ✅ Fully working API endpoints with JWT

---

## 👨‍💻 Author

Siva Guru





