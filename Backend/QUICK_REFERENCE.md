# 🚀 CampusConnect Backend - QUICK START GUIDE

## 📋 ONE-PAGE REFERENCE

### **10 Services at a Glance**

```
PORT    SERVICE                  DATABASE    STATUS
8761    discoveryServer          -           ✅ Eureka Registry
8888    config-server            -           ✅ Config Server
8080    apiGateway               -           ✅ JWT + Routing
8081    authService              PostgreSQL  ✅ Login/JWT
8082    classroomService         PostgreSQL  ✅ Academics
8083    chatService              MongoDB     ✅ Messaging
8084    forumService             PostgreSQL  ✅ Forums/Polls
8085    opportunityService       PostgreSQL  ✅ Jobs
8086    gamificationService      PostgreSQL  ✅ XP/Leaderboards
8087    aluminiService           Neo4j       ✅ Networking
```

### **Database Quick Setup**

```bash
# Start all infrastructure
docker-compose up -d

# Verify services are running
docker ps | grep campusconnect
```

### **Compilation Verification**

```bash
# Check all services compile
cd authService && mvn clean compile -q && echo "✅ authService"
cd classroomService && mvn clean compile -q && echo "✅ classroomService"
cd forumService && mvn clean compile -q && echo "✅ forumService"
cd chatService && mvn clean compile -q && echo "✅ chatService"
cd gamificationService && mvn clean compile -q && echo "✅ gamificationService"
cd opportunityService && mvn clean compile -q && echo "✅ opportunityService"
cd aluminiService && mvn clean compile -q && echo "✅ aluminiService"
cd apiGateway && mvn clean compile -q && echo "✅ apiGateway"
```

### **Service Startup Commands**

```bash
# Terminal 1: Discovery Server
cd discoveryServer && mvn spring-boot:run

# Terminal 2: Config Server
cd config-server && mvn spring-boot:run

# Terminal 3: API Gateway
cd apiGateway && mvn spring-boot:run

# Terminal 4+: Individual Services
cd authService && mvn spring-boot:run
cd classroomService && mvn spring-boot:run
cd chatService && mvn spring-boot:run
cd forumService && mvn spring-boot:run
cd gamificationService && mvn spring-boot:run
cd opportunityService && mvn spring-boot:run
cd aluminiService && mvn spring-boot:run
```

### **Access Points**

```
Eureka Dashboard:     http://localhost:8761
Config Server:        http://localhost:8888
API Gateway:          http://localhost:8080
Neo4j Browser:        http://localhost:7474
Kafka:                localhost:9092
PostgreSQL:           localhost:5432
MongoDB:              localhost:27017
```

### **Database Credentials**

| Database  | Host          | Port  | User     | Password |
|-----------|---------------|-------|----------|----------|
| PostgreSQL| localhost     | 5432  | postgres | password |
| MongoDB   | localhost     | 27017 | admin    | password |
| Neo4j     | localhost     | 7687  | neo4j    | password |

### **API Authentication**

```bash
# 1. Get Token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"pass"}'

# 2. Use Token
curl -H "Authorization: Bearer <token>" \
  http://localhost:8080/api/forums
```

### **Key Endpoints**

```
Auth:
  POST /auth/login              → Get JWT token
  POST /auth/signup             → Register user
  GET /auth/refresh             → Refresh token
  PUT /auth/change-password     → Change password

Forums:
  GET /api/forums               → List all forums
  POST /api/forums              → Create forum
  POST /api/forums/{id}/replies → Add reply
  POST /api/forums/{id}/polls   → Create poll
  POST /api/forums/polls/{id}/vote → Vote

Classrooms:
  GET /api/classrooms           → List classrooms
  POST /api/classrooms          → Create classroom
  POST /api/classrooms/{id}/assignments

Chat:
  POST /chat/rooms              → Create room
  POST /chat/{roomId}/messages  → Send message

Opportunities:
  GET /api/opportunities        → List jobs
  POST /api/opportunities/apply → Apply for job

Gamification:
  GET /api/gamification/leaderboard → Leaderboard
  POST /api/gamification/points     → Add points

Alumni:
  GET /alumni/jobs              → Get job listings
  POST /alumni/mentor           → Start mentorship
```

### **Troubleshooting Quick Fixes**

```bash
# Port already in use
sudo lsof -i :8080  # Find process
kill -9 <PID>       # Kill process

# Docker cleanup
docker-compose down -v  # Remove volumes
docker-compose up -d    # Restart clean

# Database issues
docker-compose logs postgres  # View postgres logs
docker-compose logs mongodb   # View mongodb logs

# Service not starting
mvn clean install  # Clean rebuild
mvn spring-boot:run -X  # Debug mode

# Config server issues
curl http://localhost:8888/authService/default  # Test config
```

### **Project Statistics**

```
Total Java Files:     258
Controllers:          23
Services:             66
Repositories:         31
DTOs:                 33
Entities:             31
Configuration Files:  17
```

### **Files to Know**

```
docker-compose.yml          → Infrastructure setup
Dockerfile                  → Container image
README.md                   → Full documentation
PROJECT_SUMMARY.md          → This detailed summary
config-server/src/main/resources/config/  → Service configs
```

### **Common Issues & Solutions**

| Issue | Solution |
|-------|----------|
| Services can't register to Eureka | Ensure discoveryServer is running on 8761 |
| Config not loading | Check config-server is running on 8888 |
| Database connection failed | Verify docker-compose services are running |
| JWT validation fails | Ensure authService is running and same secret key used |
| Port conflicts | Change server.port in application.properties |
| MongoDB auth fails | Use: mongodb://admin:password@localhost:27017/campusconnect?authSource=admin |

### **Performance Tuning**

```bash
# Increase JVM heap
export JAVA_OPTS="-Xmx1024m -Xms512m"
mvn spring-boot:run

# Enable parallel builds
mvn -T 1C clean install

# Skip tests for faster build
mvn clean install -DskipTests
```

### **Health Checks**

```bash
# Check all services health
curl http://localhost:8081/actuator/health  # authService
curl http://localhost:8082/actuator/health  # classroomService
curl http://localhost:8083/actuator/health  # chatService
curl http://localhost:8084/actuator/health  # forumService
curl http://localhost:8085/actuator/health  # opportunityService
curl http://localhost:8086/actuator/health  # gamificationService
curl http://localhost:8087/actuator/health  # aluminiService
```

---

**Last Updated**: April 18, 2026
**Status**: ✅ PRODUCTION READY

