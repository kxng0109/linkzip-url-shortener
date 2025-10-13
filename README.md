# LinkZip: A Robust URL Shortener API

LinkZip is a powerful and lightweight URL shortening service built from the ground up using Java and the Spring Boot framework. It provides a clean, RESTful API to convert long, unwieldy URLs into short, shareable links, which seamlessly redirect users to their original destinations. The application is designed for performance and scalability, with robust validation and a clear separation of concerns.

This project demonstrates modern backend development principles, including a clear service-oriented architecture, robust testing, and professional configuration management. It leverages Spring Profiles to seamlessly switch between a persistent PostgreSQL database for development and a lightweight H2 in-memory database for automated testing.

## Key Features

* RESTful API: Exposes endpoints for creating short links (`POST`) and handling redirects (`GET`).
* Persistent Storage: Uses a Dockerized PostgreSQL database to ensure link data is saved permanently.
* Profile-Based DB Configuration: Automatically switches between PostgreSQL (for `dev`) and H2 (for `test`) using Spring Profiles.
* Secure Configuration: Avoids committing secrets by loading database credentials from environment variables.
* Robust Input Validation: Ensures all incoming data is well-formed and returns clean error messages via a global exception handler.
* Well-Tested: Includes a full suite of unit and integration tests to ensure reliability and code quality.

## Built With

* Java 25 & Spring Boot 3.5.6
* Spring Data JPA & Hibernate
* Bean Validation (Hibernate Validator)
* Databases: PostgreSQL (dev), H2 (test)
* Testing: JUnit 5 & Mockito
* Containerization: Docker
* Build Tool: Maven

## API Usage

*(This section remains the same as the API contract has not changed)*

### Shorten a URL

**Endpoint**: `POST /api/v1/shorten`

**Success (201 Created)**: Returns a JSON object with the full short URL.

**Failure (400 Bad Request)**: Returns a JSON object with validation errors if the URL is invalid.

### Redirect to Original URL

**Endpoint**: `GET /{shortCode}`

**Success (302 Found)**: Redirects the client to the original long URL.

**Failure (404 Not Found)**: Returns a `404 Not Found` status if the code does not exist.

## Getting Started

To get a local copy up and running, please follow these steps.

### Prerequisites

* JDK 25 or later
* Maven
* Docker and Docker Compose

### Installation & Setup

```bash
git clone https://github.com/kxng0109/linkzip-url-shortener.git
cd linkzip
```

Run PostgreSQL in Docker:

```bash
docker run --name linkzip-db \
  -e POSTGRES_PASSWORD=mysecretpassword \
  -p 5432:5432 \
  -d postgres:18
```

Next, connect to the container and create the database:

```bash
docker exec -it linkzip-db psql -U postgres
# Now, inside the psql shell, run:
CREATE DATABASE linkzip_db;
```

### Configure Local Environment

This project uses environment variables to handle secrets securely.

* Create a new file named `application-dev.properties` inside `src/main/resources`. You can copy the contents from `application-dev.properties.example`.
* Set the `POSTGRES_PASSWORD` environment variable on your system or in your IDE's run configuration to match the password from the `docker run` command (for example, `mysecretpassword`).

Run the application:

```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

### Running Tests

To run the full suite of unit and integration tests (which will use the H2 database):

```bash
mvn test
```

## Future Enhancements

* [x] Integrate PostgreSQL for persistent storage.
* [ ] Implement user accounts with Spring Security to manage links.
* [ ] Add click tracking and analytics for each link.

## License

This project is licensed under the MIT License - see the [LICENSE](/LICENSE) file for details.
