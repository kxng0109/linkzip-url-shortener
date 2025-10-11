# LinkZip: A Simple URL Shortener 🚀

LinkZip is a lightweight and efficient URL shortening service built with Java and Spring Boot. It provides a simple REST API to convert long, cumbersome URLs into short, easy-to-share links. The application is designed for performance and scalability, with robust validation and a clear separation of concerns.

This version uses an in-memory H2 database, making it easy to run and test without any external database setup.

## Key Features

* Shorten any URL: Convert a long URL into a short, unique code.
* Fast redirects: Automatically redirects short links to their original destination.
* RESTful API: A clean and simple API for programmatic access.
* Robust input validation: Ensures that submitted data is well-formed and returns clear error messages.
* Zero-setup database: Uses H2 in-memory database for immediate use.
* Well-tested: Includes unit and integration tests to ensure reliability.

## Built With

* Java 25
* Spring Boot 3
* Spring Data JPA
* Bean Validation (Hibernate Validator)
* H2 Database (development and testing)
* JUnit 5 & Mockito (testing)
* Maven

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

To get a local copy up and running, follow these steps.

### Prerequisites

* JDK 25 or later
* Maven

### Installation & Setup

```bash
git clone https://github.com/kxng0109/linkzip-url-shortener.git
cd linkzip
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

Run tests:

```bash
mvn test
```

## Future Enhancements

* [ ] Integrate PostgreSQL for persistent storage.

* [ ] Implement user accounts with Spring Security to manage links.

* [ ] Add click tracking and analytics for each link.

## License

This project is licensed under the MIT License - see the [LICENSE](/LICENSE) file for details.
