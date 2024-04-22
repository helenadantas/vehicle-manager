## Vehicle Manager

Vehicle Manager is a Spring Boot application designed to manage vehicles and users in a vehicle management system. It
provides APIs for creating, updating, and deleting vehicles, as well as user authentication and authorization.

### Features

- **User Management**: Allows users to sign up, log in, and manage their vehicles.
- **Vehicle Management**: Provides APIs for creating, updating, and deleting vehicles.
- **Authentication and Authorization**: Uses JWT (JSON Web Token) for user authentication and role-based authorization.

### Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Token)
- Swagger UI for API documentation

### How to Run

1. Clone the repository:
   git clone https://github.com/your-username/vehicle-manager.git

2. Navigate to the project directory:

cd vehicle-manager

3. Build the project:

mvn clean install

4. Run the application:

mvn spring-boot:run

The application will start on `http://localhost:8080`.

### API Documentation

After running the application, you can access the Swagger UI to explore the API endpoints and documentation. Open your
web browser and go to:

http://localhost:8080/swagger-ui/

### Configuration

You can configure the database connection, JWT secret key, and other application properties in
the `application.properties` file located in the `src/main/resources` directory.
