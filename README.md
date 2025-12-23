# User Services

The **User Services** project is a microservice responsible for managing users, including authentication, profiles, and authorizations.

## Table of Contents

- [User Services](#user-services)
  - [Table of Contents](#table-of-contents)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Environment Configuration](#environment-configuration)
  - [Usage](#usage)
    - [Running the Environment with Docker Compose](#running-the-environment-with-docker-compose)
    - [Database Migrations](#database-migrations)
      - [Creating a New Migration](#creating-a-new-migration)
  - [API](#api)
    - [gRPC](#grpc)
  - [Project Structure](#project-structure)

## Getting Started

These instructions will provide you with a copy of the project running on your local machine for development and testing purposes.

### Prerequisites

- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/velo-project/users-service/
   ```
2. Navigate to the project directory:
   ```sh
   cd users-service
   ```

### Environment Configuration

1. Create a `.env` file from the example:
   ```sh
   cp .env.example .env
   ```
2. Edit the `.env` file with your settings. The environment variables are:

   - `SERVER_PORT`: Application HTTP port.
   - `POSTGRES_DB`: Database name.
   - `POSTGRES_HOST`: Database host.
   - `POSTGRES_USER`: Database user.
   - `POSTGRES_PASSWORD`: Database password.
   - `RABBITMQ_DEFAULT_HOST`: RabbitMQ host.
   - `RABBITMQ_DEFAULT_PORT`: RabbitMQ port.
   - `RABBITMQ_DEFAULT_USER`: RabbitMQ user.
   - `RABBITMQ_DEFAULT_PASS`: RabbitMQ password.
   - `REDIS_HOST`: Redis host.
   - `REDIS_PORT`: Redis port.
   - `REDIS_USERNAME`: Redis user.
   - `REDIS_PASSWORD`: Redis password.
   - `GMAIL_ADDRESS`: Gmail address for sending emails.
   - `GMAIL_SECRET_KEY`: Gmail app key.
   - `GOOGLE_CLOUD_PROJECT`: Google Cloud project name.
   - `GOOGLE_CLOUD_CREDENTIALS`: Path to Google Cloud service account credentials.
   - `GOOGLE_CLOUD_BUCKET`: Google Cloud Storage bucket name.
   - `SENTRY_DSN`: Sentry DSN for error tracking.
   - `SENTRY_AUTH_TOKEN`: Authentication token for Sentry.
   - `RSA_PRIVATE_KEY`: Path to your RSA private key.
   - `RSA_PUBLIC_KEY`: Path to your RSA public key.

## Usage

### Running the Environment with Docker Compose

To start all services (application, database, RabbitMQ, Redis, and Nginx), run the following command:

```sh
docker-compose up -d
```

The application will be available at `http://localhost`.

### Database Migrations

The project uses [Flyway](https://flywaydb.org/) to manage database migrations. Migrations are automatically run when the application starts.

The migration files are located in `presentations/src/main/resources/db/migration`.

#### Creating a New Migration

To create a new migration, add a new SQL file in the migrations directory following the Flyway naming pattern: `V<VERSION>__<DESCRIPTION>.sql`.

For example: `V8__Add_new_table.sql`.

## API

### gRPC

The service exposes a gRPC API for internal communication between microservices. The service definition is in the `presentations/src/main/proto/user.proto` file.

To generate the gRPC client code, you can use `protoc` with the gRPC plugin for your programming language.

## Project Structure

The project is divided into modules, following the principles of Clean Architecture:

- **domain**: Contains the entities, value objects, and core business logic.
- **application**: Orchestrates the data flow between the `domain` and `infrastructure`. Contains the use cases (commands and queries).
- **infrastructure**: Implements the technical details, such as database repositories, email services, and cache.
- **presentations**: The outermost layer, responsible for exposing the API (REST and gRPC) and handling HTTP requests.
