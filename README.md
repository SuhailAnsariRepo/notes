# Notes App

This is a backend system for a notes app that supports multiple users. The application's core features include user authentication, folder creation (both public and private), adding text notes and image uploads to folders, and ensuring users can access their private and public folders. Additionally, the system implements JWT-based authentication and utilizes Postgres as the database.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository to your local machine.
2. Install the required dependencies using `mvn install` or `./gradlew build`.
3. Start the server using `mvn spring-boot:run` or `./gradlew bootRun`

## User Authentication

The system implements a robust user authentication system, allowing users to log in using their mobile numbers and passwords. JWT-based authentication is used for secure access to the app.

## Folder Management

The system enables users to create both public and private folders.

## Note and Image Management

Within each folder, users can add text notes and upload images.

## Access Control

The system ensures that users can view the content of their private folders and public folders.

## Database Integration

Postgres is utilized as the database to store user data, folders, notes, and images.

## Contributing

Contributions are welcome! Please feel free to submit a pull request.
