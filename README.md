# Book API - Living Letters

A RESTful web service developed with Spring Boot to manage the books of the fiction publishing house "Letras Vivas."
This API allows clients to perform basic operations such as listing, adding, searching, and deleting books.
The project follows a layered architecture and professional coding practices.
In our first project, it only managed a library; the system allows CRUD operations for creating, reading, updating, and deleting. But now, with the new additions, the system includes validations, error handling, the use of Hibernate and Spring Data, and Swagger documentation.

---

## Features

- Retrieve all books.
- Add a new book.
- Search for books by title.
- Update an existing book.
- Delete a book by its ID.
- Register a user.
- Create JWT
- Data validation in DTOs.
- Centralized error handling.
- Swagger UI interactive documentation.
- Data persistence with H2 in-memory database.

---

## Technologies Used

- Java Zulu 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- H2 in-memory databas
- Maven
- Postman (for testing)
- Swagger UI
---

## Project Structure

- `model` – Book entity class
- `repository` – Interface extending JpaRepository
- `service` – Business logic (interface + implementation)
- `controller` – REST endpoints
- `config` - Basic configuration for OpenAPI/Swagger and CORS.
- `dto` - Data Transfer Objects with validation rules.
- `exception` - Global exception handling.
- `Security` - It handles JWT, filters, and access settings.
   
---

## Frontend (HTML + JS + CSS)

- Login and registration panel with validations.
- Book management (create, list, edit, delete, search).
- Automatic use of the JWT token upon login (no need to paste it manually).
- Modern and responsive design with Bootstrap and custom CSS.
- Client-side validations:
- Clear and functional interface with Edit and Delete buttons.

---

## How to Run the Project

1. Clone the repository
 ```
git clone https://github.com/XxRdorixX/DWF_DesafPrac3.git
   ```
 ```
cd DWF_DesafPrac3
 ```
2. Open the project

Open with IntelliJ IDEA.

3. Requirements

Java 17 (Zulu or any compatible JDK).

Maven installed.
  
4. write to terminal
```
cd Desafio3_Escobar_Martinez/backend/bookapi
 ```
5. Build the project
  ```
mvn clean install
  ```
6. Run the project

Option A (via IDE): Run `BookApiApplication.java`
Option B (via terminal):
```
mvn spring-boot:run
```
Client side Run Index.html 

7. Access the API

Base URL: http://localhost:8081/api/books

H2 Console: http://localhost:8081/h2-console

JDBC URL: jdbc:h2:mem:bookdb

User: sa

Password: (empty)

---

## Example Requests (Postman)

Get all books
GET http://localhost:8081/api/books

Create a new book
POST http://localhost:8081/api/books
body -> raw -> JSON
Content-Type: application/jso
```
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "publicationYear": 2008
}
```
Get book by ID
GET http://localhost:8081/api/books/1

Search books by title
GET http://localhost:8081/api/books/search?title=

Update a book
PUT http://localhost:8081/api/books/1
Body -> raw -> JSON
Content-Type: application/json
```
{
  "title": "Clean Coder",
  "author": "Robert C. Martin",
  "publicationYear": 2011
}
```
Delete a book
DELETE http://localhost:8081/api/books/1

Register a new user
POST http://localhost:8081/api/auth/register

```
{
  "username": "Admin",
  "password": "123456"
}
```
Get token
POST http://localhost:8081/api/auth/login
```
{
  "username": "Admin",
  "password": "123456"
}
```
---

## Error handling example

POST http://localhost:8081/api/books
body -> raw -> JSON
Content-Type: application/jso
```
{
  "title": "",
  "author": "",
  "publicationYear": -100
}
```
should show
```
{
      "status": 400,
    "error": "Bad Request",
    "message": "Title must be 1-200 chars; publicationYear must be a positive integer; Author must be 1-100 chars; Field 'author' is required; Field 'title' is required",
    "field": "title",
    "timestamp": "2025-09-22T06:51:55.1850315"
}
```
---

## Swagger / OpenAPI Documentation

After running the application, the interactive documentation is available at:

Swagger UI: http://localhost:8081/swagger-ui/index.html

OpenAPI JSON: http://localhost:8081/v3/api-docs

Compared to Postman in Swagger, we won't have to type the above in each GET, POST, PUT, and DELETE, Register, Token as the options are already pre-made.

To do this, simply copy the link and it will redirect to Swagger with all the options. For example, in POST /api/books, click Try it out and we'll have our body already created. 
Just change the Title, Author, and publicationYear and click Execute. The information will be saved and can be seen in the H2 Console.
