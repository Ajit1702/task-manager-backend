Here’s your **complete, production-ready `README.md`** for the Task Management Backend project built with **Spring Boot, MySQL, Kafka, Swagger, Docker, and Spring Security**.

---

````markdown
# 🗂️ Task Management Backend System

A robust backend application that allows users to register, log in, and manage their tasks securely. The system is powered by Spring Boot, JWT-based authentication, and Kafka event logging. All services are containerized using Docker and documented via Swagger UI.

---

## 🚀 Tech Stack

| Layer         | Technology                 |
|---------------|-----------------------------|
| Backend       | Spring Boot 3.5 (Java 17)    |
| Security      | Spring Security (JWT)       |
| Database      | MySQL                       |
| Messaging     | Apache Kafka + Zookeeper    |
| API Docs      | Swagger (springdoc-openapi) |
| Container     | Docker + Docker Compose     |
| Build Tool    | Maven                       |

---

## ✅ Features

- 🔐 User Registration & Login with JWT
- 📌 CRUD operations for tasks (linked to logged-in user)
- 📤 Kafka integration for task event logging
- 🧾 Swagger API Documentation
- 🐳 Docker Compose for local setup of MySQL, Kafka, and the application

---

## 📦 Run Locally

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/task-manager-backend.git
cd task-manager-backend
````

### 2. Build the Application

```bash
./mvnw clean install
```

### 3. Run with Docker Compose

```bash
docker-compose up --build
```

📌 Access the app:

* API Base URL: `http://localhost:8080`
* Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🛠️ Environment Variables

Define these in `.env` (optional, handled via `application.yml` for now):

```
MYSQL_ROOT_PASSWORD=root
JWT_SECRET=mysecurejwtkeymysecurejwtkey
```

---

## 🔐 Authentication APIs

| Method | Endpoint         | Description         |
| ------ | ---------------- | ------------------- |
| POST   | `/auth/register` | Register a new user |
| POST   | `/auth/login`    | Login & get JWT     |

> Use the token from `/auth/login` as a Bearer token in Authorization header.

---

## 🗂️ Task APIs (Protected)

| Method | Endpoint      | Description        |
| ------ | ------------- | ------------------ |
| GET    | `/tasks`      | Get all user tasks |
| POST   | `/tasks`      | Create a new task  |
| PUT    | `/tasks/{id}` | Update a task      |
| DELETE | `/tasks/{id}` | Delete a task      |

Example Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

---

## 🔁 Kafka Event Logging

Whenever a task is created, updated, or deleted, an event is pushed to a Kafka topic:

* **Topic**: `task-events`
* **Producer**: on task actions
* **Consumer**: logs messages to console

Example:

```
Kafka Producer sent message: Task created by ajit: Fix Swagger bug
Kafka Consumer received: Task created by ajit: Fix Swagger bug
```

---

## 🧪 Testing the Application

* Test APIs via **Swagger UI**: `http://localhost:8080/swagger-ui.html`
* Use **Postman** to verify secured and public routes

---

## 🧱 Folder Structure

```
task-manager-backend/
├── controller/         # REST Controllers
├── dto/                # Data Transfer Objects
├── model/              # JPA Entities
├── repository/         # Spring Data Repositories
├── security/           # JWT filters & config
├── service/            # Business logic
├── kafka/              # Kafka producer/consumer
├── config/             # Swagger & Kafka configs
├── Dockerfile
├── docker-compose.yml
├── application.yml
├── README.md
```

---

## 🧠 Future Enhancements

* Role-based access control (Admin/User)
* Task reminders via email or push
* Kafka message persistence
* Frontend integration (React/Angular)

---

## 👨‍💻 Author

**Ajit Kumar**
[GitHub Profile](https://github.com/Ajit1702)

---

## 📜 License

This project is licensed under the MIT License.
