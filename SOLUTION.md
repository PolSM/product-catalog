## Overview

The Products API was created as a flexible, scalable service to manage a product catalog. The design is guided by **Domain-Driven Design (DDD)** principles and a **hexagonal architecture** to keep the core logic organized and adaptable.

## Architectural Decisions

### Domain-Driven Design (DDD)

- **Decision**: Organized the API into **Domain**, **Application**, **Infrastructure**, and **API** layers. This separation helps keep business logic clear and enhances long-term maintainability.
- **Reason**: This modular approach makes it easier to organize and test domain-specific logic while keeping the code adaptable to changes.

### Hexagonal Architecture (Ports & Adapters)

- **Decision**: The API’s core logic is built to be independent of any external dependencies by using interfaces for communication.
- **Reason**: This design ensures that dependencies like databases can be swapped out easily, making the service more testable, modular, and resilient.

### Testing Approach

- **Decision**: Implemented both **unit** and **integration** tests to verify core functionality and database interactions.
- **Reason**: Testing at multiple layers is essential to ensure that the service remains reliable, even as it scales.

### H2 Database for Development

- **Decision**: Used an H2 in-memory database for local development and testing.
- **Reason**: H2 is lightweight, fast, and easy to set up, making it ideal for testing. For production, I plan to use a Dockerized MySQL instance on an `alpine` image for a balance of performance and scalability.