# HA Products

Simple application for managing products implemented using Spring Boot and MongoDB.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 21

This project uses Maven as a build tool - it's recommended to use attached Maven wrapper to interact with maven (see `mvnw` files).

This project uses MongoDB database.

### <a name="prereqs-mongodb"></a>MongoDB database

For your convenience, there is a `compose.yaml` file, which is a docker-compose file that provides MongoDB as a docker container.

Thanks to [Docker Compose Support in Spring Boot](https://spring.io/blog/2023/06/21/docker-compose-support-in-spring-boot-3-1), MongoDB will be automatically started with the application, if your system supports Docker.

Because we still can't take it for granted, you need to opt-in for that by enabling `local-run-with-docker` Maven profile.

If your system doesn't support Docker, provide any working instance on 27017 port and app will connect to it.

Alternatively, you can specify the MongoDB instance using standard Spring Boot properties, such as `spring.data.mongodb.uri`. 

## Getting Started

(Remember about `local-run-with-docker` mentioned in [previous chapter](#prereqs-mongodb))

1. Compile the code and run the tests:

```bash
./mvnw verify
```

1. Start the application:

```bash
./mvnw spring-boot:run
```

The application will start on <http://localhost:8080>.

There is Swagger available and can be accessed with <http://localhost:8080/swagger-ui.html>.