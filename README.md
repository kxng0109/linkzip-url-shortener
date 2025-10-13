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

### Shorten a URL

Creates a new short link.

**Endpoint**

`POST /api/v1/shorten`

**Request Body**

```json
{
  "longUrl": "https://www.very-long-and-complicated-url.com/with/some/path"
}
```

**Success Response (201 Created)**

```json
{
  "shortUrl": "http://localhost:8080/aB1xZ9pL"
}
```

**Failure Response (400 Bad Request)**

If the `longUrl` is missing or invalid:

```json
{
  "longUrl": "A valid URL format is required"
}
```

### Redirect to Original URL

Redirects to the original long URL associated with a short code.

**Endpoint**

`GET /{shortCode}`

**Example**

`GET /aB1xZ9pL`

**Success Response**

An HTTP 302 Found redirect to the original URL.

**Failure Response**

HTTP 404 Not Found if the `shortCode` does not exist.

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
