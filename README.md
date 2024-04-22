## Vehicle Manager

Vehicle Manager is a Spring Boot application designed to manage vehicles and users in a vehicle management system. It
provides APIs for creating, updating,  listing, showing and deleting vehicles and users, as well as user authentication
and authorization.

### Features

- **User Management**: Allows users to sign up, log in, and manage their vehicles.
- **Vehicle Management**: Vehicle Management: Allows you to create, update and delete vehicles.
- **Authentication and Authorization**: Uses JWT (JSON Web Token) for user authentication and role-based authorization.

### Technologies Used

- Java 17
- Spring Boot 3.0
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Token)
- Swagger UI for API documentation

### How to Run

1. Clone the repository:

   git clone https://github.com/helenadantas/vehicle-manager.git


2. Navigate to the project directory:

   cd vehicle-manager


3. Build the project:

   mvn clean install


4. Run docker:
   
   cd docker
   docker-compose up
   

5. Run the application:

   mvn spring-boot:run


6. Run the script present in data.sql to add the necessary roles to your database. This script will populate the roles
table with initial data required by the application. You can execute the script using a MySQL client or command-line
interface.


The application will start on `http://localhost:8080`.


### Admin User Configuration

The application creates an admin user during startup using the `AdminUserConfig` class. This admin user is essential for accessing certain privileged endpoints and performing administrative tasks within the application.

#### Default Admin User Credentials
Upon application startup, the following admin user is created:
- **email**: admin@admin.com
- **password**: 123

You can use these credentials to log in as the admin user and access administrative functionalities within the application.

### API Documentation

After running the application, you can access the Swagger UI to explore the API endpoints and documentation. Open your
web browser and go to:

http://localhost:8080/swagger-ui/


### Configuration

You can configure the database connection, JWT secret key, and other application properties in
the `application.properties` file located in the `src/main/resources` directory.
