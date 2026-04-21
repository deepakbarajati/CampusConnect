# 🎯 MINIMAL APPLICATION.PROPERTIES - ALL SERVICES


[//]: # (## Discovery Server (application.properties))
```ini

spring.application.name=discoveryServer

spring.config.import=configserver:http://localhost:8888

```


## Config Server (application.yml)

```yaml

spring.application.name=config-server

spring.profiles.active=native

```


## API Gateway (application.properties)

```ini

spring.application.name=apiGateway

spring.config.import=configserver:http://localhost:8888

```


## Auth Service (application.properties)

```ini

spring.application.name=authService

spring.config.import=configserver:http://localhost:8888

```


## Classroom Service (application.properties)

```ini

spring.application.name=classroomService

spring.config.import=configserver:http://localhost:8888

```


## Chat Service (application.properties)

```ini

spring.application.name=chatService

spring.config.import=configserver:http://localhost:8888

```


## Forum Service (application.properties)

```ini

spring.application.name=forumService

spring.config.import=configserver:http://localhost:8888

```


## Opportunity Service (application.properties)

```ini

spring.application.name=opportunityService

spring.config.import=configserver:http://localhost:8888

```


## Gamification Service (application.properties)

```ini

spring.application.name=gamificationService

spring.config.import=configserver:http://localhost:8888

```


## Alumni Service (application.properties)

```ini

spring.application.name=aluminiService

spring.config.import=configserver:http://localhost:8888

```


---


# 📝 CONFIG SERVER YAML FILES (FOR GITHUB CONFIG REPO)


## File: discoveryServer.yml

```yaml

spring:

  application:

    name: discoveryServer


server:

  port: 8761


eureka:

  client:

    register-with-eureka: false

    fetch-registry: false

  server:

    enable-self-preservation: false


logging:

  level:

    com.netflix.eureka: WARN

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: config-server.yml

```yaml

spring:

  application:

    name: config-server


  profiles:

    active: native

  cloud:

    config:

      server:

        native:

          search-locations: classpath:/config


server:

  port: 8888


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: apiGateway.yml

```yaml

spring:

  application:

    name: apiGateway


  cloud:

    gateway:

      routes:

        - id: authService

          uri: lb://authService

          predicates:

            - Path=/api/v1/campusConnect/auth/**

          filters:

            - StripPrefix=2


        - id: chatService

          uri: lb://chatService

          predicates:

            - Path=/api/v1/campusConnect/chat/**

          filters:

            - StripPrefix=2

            - name: AuthenticationFilter

            
        - id: alumniService

          uri: lb://aluminiService

          predicates:

            - Path=/api/v1/campusConnect/alumni/**

          filters:

            - StripPrefix=2

            - name: AuthenticationFilter


        - id: classroomService

          uri: lb://classroomService

          predicates:

            - Path=/api/v1/campusConnect/classroom/**

          filters:

            - StripPrefix=2

            - name: AuthenticationFilter


        - id: forumService

          uri: lb://forumService

          predicates:

            - Path=/api/v1/campusConnect/forum/**

          filters:

            - StripPrefix=2

            - name: AuthenticationFilter


        - id: gamificationService

          uri: lb://gamificationService

          predicates:

            - Path=/api/v1/campusConnect/gamification/**

          filters:

            - StripPrefix=2

            - name: AuthenticationFilter


        - id: opportunityService

          uri: lb://opportunityService

          predicates:

            - Path=/api/v1/campusConnect/opportunity/**

          filters:

            - StripPrefix=2

            - name: AuthenticationFilter


server:

  port: 8080


jwt:

  secretKey: jajfkfahancfhaihsdunnfakdjbfdfhjakjj


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.apiGateway: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: authService.yml

```yaml

spring:

  application:

    name: authService


server:

  port: 8081


spring:

  datasource:

    url: jdbc:postgresql://localhost:5432/campusconnect

    username: postgres

    password: password

    driver-class-name: org.postgresql.Driver

  jpa:

    hibernate:

      ddl-auto: update

    show-sql: true

    properties:

      hibernate:

        format_sql: true

        dialect: org.hibernate.dialect.PostgreSQLDialect


jwt:

  secretKey: mySecretKey1234567890123456789012345678901234567890

  expiration: 86400000


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.authService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: classroomService.yml

```yaml

spring:

  application:

    name: classroomService


server:

  port: 8082


spring:

  datasource:

    url: jdbc:postgresql://localhost:5432/campusconnect

    username: postgres

    password: password

    driver-class-name: org.postgresql.Driver

  jpa:

    hibernate:

      ddl-auto: update

    show-sql: true

    properties:

      hibernate:

        format_sql: true

        dialect: org.hibernate.dialect.PostgreSQLDialect


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.classroomService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: chatService.yml

```yaml

spring:

  application:

    name: chatService


  data:

    mongodb:

      uri: mongodb://admin:password@localhost:27017/campusconnect?authSource=admin

      database: campusconnect


server:

  port: 8083


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.chatService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: forumService.yml

```yaml

spring:

  application:

    name: forumService


server:

  port: 8084


spring:

  datasource:

    url: jdbc:postgresql://localhost:5432/campusconnect

    username: postgres

    password: password

    driver-class-name: org.postgresql.Driver

  jpa:

    hibernate:

      ddl-auto: update

    show-sql: true

    properties:

      hibernate:

        format_sql: true

        dialect: org.hibernate.dialect.PostgreSQLDialect


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.forumService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: opportunityService.yml

```yaml

spring:

  application:

    name: opportunityService


server:

  port: 8085


spring:

  datasource:

    url: jdbc:postgresql://localhost:5432/campusconnect

    username: postgres

    password: password

    driver-class-name: org.postgresql.Driver

  jpa:

    hibernate:

      ddl-auto: update

    show-sql: true

    properties:

      hibernate:

        format_sql: true

        dialect: org.hibernate.dialect.PostgreSQLDialect


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.opportunityService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: gamificationService.yml

```yaml

spring:

  application:

    name: gamificationService


server:

  port: 8086


spring:

  datasource:

    url: jdbc:postgresql://localhost:5432/campusconnect

    username: postgres

    password: password

    driver-class-name: org.postgresql.Driver

  jpa:

    hibernate:

      ddl-auto: update

    show-sql: true

    properties:

      hibernate:

        format_sql: true

        dialect: org.hibernate.dialect.PostgreSQLDialect

  kafka:

    bootstrap-servers: localhost:9092

    consumer:

      group-id: gamification-group

      auto-offset-reset: earliest

    producer:

      key-serializer: org.apache.kafka.common.serialization.StringSerializer

      value-serializer: org.apache.kafka.common.serialization.StringSerializer


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.gamificationService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


## File: aluminiService.yml

```yaml

spring:

  application:

    name: aluminiService


  neo4j:

    uri: bolt://localhost:7687

    authentication:

      username: neo4j

      password: Aa@723201


server:

  port: 8087

  servlet:

    context-path: /campusConnect/alumni


eureka:

  client:

    service-url:

      defaultZone: http://localhost:8761/eureka/

  instance:

    prefer-ip-address: true


logging:

  level:

    com.campusConnect.aluminiService: DEBUG

  pattern:

    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

```


---


## ✅ SUMMARY


✅ All application.properties files now contain ONLY 2 lines:

  - spring.application.name={serviceName}

  - spring.config.import=configserver:http://localhost:8888


✅ All detailed configuration moved to centralized YAML files in config-server


✅ All services compile successfully


✅ Paste these YAML files to your GitHub config repository



