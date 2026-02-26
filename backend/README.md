# Defect Tracker Application

This is a Spring Boot application for managing software defects. It provides a RESTful API for creating, reading, updating, and deleting defects with features like severity tracking and status management.

## Sample Usage

```json
{
  "title": "Login page not loading",
  "description": "Users are unable to access the login page after recent deployment",
  "severity": "High",
  "status": "Open"
}
```

This application provides a simple yet effective way to track software defects and manage them throughout the development lifecycle.

## Features

- RESTful API for defect management
- Defect tracking with title, description, severity, and status
- Automatic timestamp tracking for creation and updates
- CRUD operations for defects
- Spring Boot and Spring Data JPA implementation

## Project Structure

- `src/main/java/com/example/defecttracker/` - Main application code
  - `Defect` - Entity class representing a defect
  - `DefectService` - Business logic for defect operations
  - `DefectController` - REST API endpoints
  - `DefectRepository` - Data access layer
  - `DefectTrackerApplication` - Main application class
- `src/test/java/com/example/defecttracker/` - Test code
  - `DefectControllerTest` - API endpoint tests

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate (via Spring Data JPA)
- Maven (build system)
- RESTful API design

## API Endpoints

### GET `/api/defects`
- Retrieve all defects

### POST `/api/defects`
- Create a new defect
- Request body: `{ "title": "...", "description": "...", "severity": "...", "status": "..." }`

### PUT `/api/defects/{id}`
- Update an existing defect
- Request body: `{ "title": "...", "description": "...", "severity": "...", "status": "..." }`

### DELETE `/api/defects/{id}`
- Delete a defect by ID

## Running the Application

1. Ensure you have Java 17+ installed
2. Install Maven
3. Navigate to the project root directory
4. Run: `mvn spring-boot:run`
5. The application will be available at `http://localhost:8080`

## Testing

The application includes JUnit 5 tests for the API endpoints. You can run the tests using:
```bash
mvn test
```

## Database Setup

The application uses Spring Data JPA with Hibernate. It connects to a default H2 database in-memory for development, but you can configure it to use other databases by modifying `src/main/resources/application.properties`.

## Contribution Guidelines

1. Fork the repository
2. Create a new branch for your feature or bug fix
3. Make your changes and ensure tests pass
4. Submit a pull request

## License

This project is licensed under the MIT License.

## Contact

For questions or issues, please open an issue on GitHub.