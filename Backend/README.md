# 🚀 CampusConnect

CampusConnect is a **microservices-based campus management platform** built using **Spring Boot + Spring Cloud + React**.

The system is fully containerized, Eureka-integrated, and secured using JWT authentication via an API Gateway.

It enables:

- Academic management
- Alumni networking
- Real-time messaging
- Placement & job opportunities
- Gamification & leaderboards

---

# 🏗️ Backend Architecture

```
CampusConnect/
├── discovery-server/      # Eureka (8761) - Service registry
├── config-server/         # Centralized configuration management
├── apiGateway/            # Spring Cloud Gateway + JWT (8080)
├── auth-service/          # Authentication & JWT issuing
├── chat-service/          # MongoDB messaging service
├── classroom-service/     # PostgreSQL - Academic management
├── alumni-service/        # Neo4j - Graph-based networking
├── opportunity-service/   # Job & placement board
└── gamification-service/  # XP, badges, leaderboards
```

All services are containerized and deployment-ready.

---

# 🔁 System Flow

Frontend (React)  
→ API Gateway (JWT validation)  
→ Eureka (Service Discovery)  
→ Microservices  
→ Databases (PostgreSQL / MongoDB / Neo4j)

---

# 🧩 Microservices Overview

## 🔍 discovery-server (Eureka)

- **Port:** 8761  
- Purpose: Service registry for all microservices  
- Endpoint: `/eureka/**`

---

## ⚙️ config-server

- Centralized `application.yml` management  
- Allows configuration updates without restarting services  

---

## 🌐 apiGateway (Spring Cloud Gateway)

- **Port:** 8080  
- Single entry point for frontend  
- Parses JWT token  
- Extracts `userId`  
- Injects `X-User-Id` header into downstream services  

### Route Examples

```
/auth/** → auth-service
/classroom/** → classroom-service
/chat/** → chat-service
/alumni/** → alumni-service
```

---

## 🔐 auth-service

Handles authentication and JWT generation.

### Endpoints

```
POST /auth/login
POST /auth/register
```

### Response

```json
{
  "token": "eyJhbGciOi..."
}
```

---

## 💬 chat-service (MongoDB)

Real-time messaging service.

Requires MongoDB replica set (`retryWrites` enabled).

### Endpoints

```
POST /chat/rooms
POST /chat/{roomId}/messages
GET /chat/rooms/user/{id}
```

---

## 🎓 classroom-service (PostgreSQL + JPA)

Core academic module.

### Entities

- Classroom  
- Assignment  
- Attendance  
- Submission  
- Exam  
- Schedule  

### Endpoints

```
GET /classrooms?subject=&semester=
POST /assignments
GET /assignments/classroom/{id}
POST /attendance/student/{id}/classroom/{id}/PRESENT
POST /submissions/assignment/{id}/file_url
```

---

## 💬 forum-service (PostgreSQL + JPA)

Forum and discussion service.

### Entities

- Forum  
- ForumReply  
- Category  
- Club  
- Event  
- Poll  

### Endpoints

```
GET /forums
POST /forums
GET /forums/{id}
PUT /forums/{id}
DELETE /forums/{id}
GET /forums/user/{userId}
POST /forums/{forumId}/replies
GET /forums/{forumId}/replies
GET /forums/replies/user/{userId}
DELETE /forums/replies/{id}
```

---

## 👥 alumni-service (Neo4j)

Graph-based networking system.

### Nodes

- Job  
- Placement  
- Mentorship  
- Company  
- AlumniMeet  

### Relationships

- POSTED_BY  
- MENTORS  
- APPLIED_TO  

### Endpoints

```
GET /jobs/active
POST /jobs
```

---

## 💼 opportunity-service (PostgreSQL + JPA)

Job and placement board.  
Integrates alumni jobs and external opportunities.

---

## 🏆 gamification-service

Student engagement system.

Features:

- XP tracking  
- Badges  
- Leaderboards  

### Endpoints

```
GET /leaderboard/semester/3
POST /badge/{studentId}
```

---

# 🌐 Frontend (React)

- Built using React  
- Communicates only via API Gateway  
- JWT stored in cookies  

### Required Dependencies

```
@mui/material
react-router-dom
axios
js-cookie
```

---

# 🚀 Quick Start

## 🔧 Prerequisites

- Java 21+
- Maven 3.8+
- Docker
- Node 18+

---

## 🐳 1. Database Setup

```bash
docker-compose up -d postgres neo4j mongodb
```

Ports:

- PostgreSQL → 5432  
- Neo4j → 7474  
- MongoDB → 27017  

---

## 🏗️ 2. Build All Services

```bash
mvn clean install
```

---

## ▶️ 3. Run Services (Sequential Order)

### 1️⃣ Discovery Server

```bash
cd discovery-server
mvn spring-boot:run
```

### 2️⃣ API Gateway

```bash
cd apiGateway
mvn spring-boot:run
```

### 3️⃣ Other Services (Run in Separate Terminals)

```bash
cd auth-service && mvn spring-boot:run
cd classroom-service && mvn spring-boot:run
cd alumni-service && mvn spring-boot:run
cd chat-service && mvn spring-boot:run
cd opportunity-service && mvn spring-boot:run
cd gamification-service && mvn spring-boot:run
```

---

## 🌐 4. Start Frontend

```bash
npx create-react-app frontend
cd frontend
npm install
npm start
```

Frontend runs at:

```
http://localhost:3000
```

Backend entry point:

```
http://localhost:8080
```

---

# 🔑 API Contract (Frontend Usage)

**Base URL**

```
http://localhost:8080/api
```

**Authorization Header**

```
Authorization: Bearer <token>
```

---

## Core Endpoints Summary

| Service        | Endpoint                         | Response |
|---------------|----------------------------------|----------|
| Auth          | POST /auth/login                 | `{token}` |
| Classroom     | GET /classrooms                  | ClassroomDTO[] |
| Chat          | POST /chat/rooms                 | `{roomId}` |
| Alumni        | GET /jobs/active                 | JobDTO[] |
| Gamification  | GET /leaderboard/semester/3      | LeaderboardDTO[] |

DTOs are defined inside their respective services.

---

# 🧪 Testing APIs (Postman)

1. `POST localhost:8080/auth/login`  
2. Copy returned token  
3. Call secured endpoints with:

```
Authorization: Bearer <token>
```

4. Verify `X-User-Id` header propagation to services

---

# 🔄 Development Workflow

1. Develop backend endpoint  
2. Test using Postman  
3. Push feature branch  
4. Frontend consumes via Gateway  
5. Gateway auto-discovers services through Eureka  

Branch examples:

```
feature/classroom-exams
feat/react-jobs
```

---

# ⚙️ Configuration Example

Each service contains:

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka
```

Database configuration is service-specific.

---

# 📌 Project Status

- Microservices containerized  
- Eureka integrated  
- JWT secured  
- Gateway routing configured  
- Frontend ready for integration  

---

# 📚 Backend Implementation Details

This section provides detailed implementation for each microservice, including technologies, key classes, endpoints, enhancements made, and additional improvements.

## 🔐 auth-service

**Technologies:** Spring Boot 3.x, Spring Security, JWT (jjwt), PostgreSQL, Spring Data JPA, Lombok, ModelMapper, Spring Mail, Bucket4j (rate limiting)

**Key Classes:**
- **Entity:** User (with roles, email verification, password reset)
- **Repository:** UserRepository (JPA queries)
- **Service:** AuthService (business logic), EmailService (email sending), JwtService (token handling)
- **Controller:** AuthController (REST endpoints)
- **Security:** WebSecurityConfig, JwtAuthFilter, RateLimitAspect

**Endpoints:**
- `POST /auth/signup` - User registration with email verification
- `POST /auth/login` - JWT token generation
- `GET /auth/refresh` - Refresh access token
- `PUT /auth/change-password` - Change password (authenticated)
- `GET /auth/verify` - Email verification
- `POST /auth/forgot-password` - Send password reset email
- `POST /auth/reset-password` - Reset password with token

**Enhancements Made:**
- Dynamic role assignment in signup
- Email verification for new users
- Password reset functionality
- Rate limiting on auth endpoints
- MDC logging for user tracing
- Global exception handling with consistent error responses
- Input validation on all DTOs
- Actuator for health checks

**Additional Improvements:**
- OAuth2 integration for social login (Google, GitHub)
- Multi-factor authentication (2FA)
- Account lockout after failed attempts
- Audit logging for security events

## 🌐 apiGateway

**Technologies:** Spring Cloud Gateway, JWT, Eureka Client, Spring Boot Actuator

**Key Classes:**
- **Filters:** AuthenticationFilter (JWT validation, header injection)
- **Service:** JwtService (token parsing)

**Enhancements Made:**
- Extracts userId and userRole from JWT
- Injects X-User-Id and X-User-Role headers
- MDC logging for tracing
- Graceful error handling

**Additional Improvements:**
- Request/response logging
- Circuit breaker with Resilience4j
- API versioning support
- Rate limiting at gateway level

## 🎓 classroom-service

**Technologies:** Spring Boot 3.x, PostgreSQL, Spring Data JPA, Spring Web, Lombok, ModelMapper, OpenFeign, WebClient, Eureka Client

**Entities:** Classroom, Assignment, Attendance, Submission, Exam, Schedule

**Key Classes:**
- **Controller:** ClassroomController (CRUD with pagination)
- **Service:** ClassroomService (business logic)
- **Repository:** JPA repositories with custom queries
- **Client:** UserServiceClient (WebClient for inter-service calls)

**Endpoints:**
- `GET /api/classrooms` - Paginated list of classrooms
- `POST /api/classrooms` - Create classroom
- `GET /api/classrooms/{id}` - Get classroom by ID
- `PUT /api/classrooms/{id}` - Update classroom
- `DELETE /api/classrooms/{id}` - Delete classroom
- Additional endpoints for assignments, attendance, etc.

**Enhancements Made:**
- Pagination and filtering on list endpoints
- WebClient for non-blocking inter-service communication
- Input validation and global exception handling
- MDC logging

**Additional Improvements:**
- File upload for assignments/submissions (using S3 or local storage)
- Notification system for assignment deadlines
- Classroom analytics (attendance stats, performance metrics)
- Bulk operations for attendance marking

## 💬 forum-service

**Technologies:** Spring Boot 3.x, PostgreSQL, Spring Data JPA, Spring Web, Lombok, ModelMapper, Eureka Client, Actuator

**Entities:** Forum, ForumReply, Category, Club, Event, Poll

**Key Classes:**
- **Controller:** ForumController (CRUD for forums and replies)
- **Service:** ForumService (business logic)
- **Repository:** JPA repositories
- **DTOs:** ForumDTO, ForumReplyDTO

**Endpoints:**
- `GET /api/forums` - Get all forums
- `POST /api/forums` - Create forum
- `GET /api/forums/{id}` - Get forum by ID
- `PUT /api/forums/{id}` - Update forum
- `DELETE /api/forums/{id}` - Delete forum
- `GET /api/forums/user/{userId}` - Get forums by user
- `POST /api/forums/{forumId}/replies` - Create reply
- `GET /api/forums/{forumId}/replies` - Get replies by forum
- `GET /api/forums/replies/user/{userId}` - Get replies by user
- `DELETE /api/forums/replies/{id}` - Delete reply

**Enhancements Made:**
- Full CRUD operations for forums and replies
- Input validation on DTOs
- Global exception handling with consistent error responses
- Actuator for health checks

**Additional Improvements:**
- Category-based forum filtering
- Poll creation and voting
- Event scheduling integration
- Notification for new replies

## 💬 chat-service

**Technologies:** Spring Boot 3.x, MongoDB, Spring Data MongoDB, WebSocket (for real-time), Eureka Client

**Entities:** ChatRoom, Message

**Enhancements Made:**
- Real-time messaging with WebSocket
- MongoDB for scalable message storage
- User presence tracking

**Additional Improvements:**
- Message encryption
- File sharing in chats
- Message search and archiving
- Push notifications for mobile

## 👥 alumni-service

**Technologies:** Spring Boot 3.x, Neo4j, Spring Data Neo4j, Eureka Client

**Nodes:** Job, Placement, Mentorship, Company, AlumniMeet

**Enhancements Made:**
- Optimized Cypher queries in repositories
- Graph-based relationships for networking

**Additional Improvements:**
- Recommendation engine using graph algorithms
- Event-based notifications for job matches
- Alumni directory with advanced search

## 💼 opportunity-service

**Technologies:** Spring Boot 3.x, PostgreSQL, Spring Data JPA, Eureka Client, Actuator

**Entities:** Opportunity, Application, EligibilityCriteria

**Key Classes:**
- **Controller:** OpportunityController (CRUD for opportunities and applications)
- **Service:** OpportunityService (business logic)
- **Repository:** JPA repositories
- **DTOs:** OpportunityDTO, ApplicationDTO

**Endpoints:**
- `GET /api/opportunities` - Get all opportunities
- `POST /api/opportunities` - Create opportunity
- `GET /api/opportunities/{id}` - Get opportunity by ID
- `PUT /api/opportunities/{id}` - Update opportunity
- `DELETE /api/opportunities/{id}` - Delete opportunity
- `GET /api/opportunities/active` - Get active opportunities
- `GET /api/opportunities/search` - Search opportunities
- `POST /api/opportunities/apply` - Apply for opportunity
- `GET /api/opportunities/{opportunityId}/applications` - Get applications by opportunity
- `GET /api/opportunities/applications/status/{status}` - Get applications by status

**Enhancements Made:**
- Job posting and application management
- Search and filtering for opportunities
- Application status tracking
- Input validation and global exception handling
- Actuator for health checks

**Additional Improvements:**
- Resume parsing and matching
- Interview scheduling
- Analytics for placement success
- Notification system for application updates

## 🏆 gamification-service

**Technologies:** Spring Boot 3.x, PostgreSQL, Spring Data JPA, Kafka, Eureka Client, Actuator

**Entities:** Gamification, Badge, LeaderBoard, Review

**Key Classes:**
- **Controller:** GamificationController (XP and leaderboard management)
- **Service:** GamificationService (business logic), KafkaProducerService (event publishing)
- **Repository:** JPA repositories
- **DTOs:** GamificationDTO, LeaderBoardDTO
- **Event:** XpGainedEvent

**Endpoints:**
- `POST /api/gamification/points` - Add points
- `GET /api/gamification/points/user/{userId}` - Get user points
- `GET /api/gamification/points/total/{userId}` - Get total points
- `GET /api/gamification/leaderboard` - Get leaderboard
- `POST /api/gamification/leaderboard/update` - Update leaderboard

**Enhancements Made:**
- XP tracking and badge awarding
- Kafka event publishing for XP gains
- Leaderboard calculations
- Input validation and global exception handling
- Actuator for health checks

**Additional Improvements:**
- Real-time leaderboard updates
- Achievement notifications
- Gamified learning paths
- Integration with classroom activities

## 🔍 discovery-server

**Technologies:** Spring Cloud Netflix Eureka Server

**Configuration:** Standard Eureka server setup

## ⚙️ config-server

**Technologies:** Spring Cloud Config Server

**Configuration:** Centralized YAML management for all services

---

# 🚀 Production Deployment

- Docker Compose  
- Kubernetes  
- Load Balancer → apiGateway:80  
- AWS RDS (PostgreSQL)  
- MongoDB Atlas / DocumentDB  
- Neo4j Aura  
- Redis caching  
- Kafka for event streaming  

---

# 👨‍💻 Author

Deepak Barajati
Spring Boot | Microservices | Distributed Systems | React
