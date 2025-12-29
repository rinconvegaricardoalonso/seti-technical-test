# 📦 Technical Test — Reactive Franchise Management API (Hexagonal Architecture)

## 🧩 Overview

This project is a **Reactive REST API** built with **Spring WebFlux** and **Spring Data R2DBC**, designed to manage **franchises, offices, and products**.

The application has been refactored to follow **Hexagonal Architecture (Ports & Adapters / Clean Architecture)** principles, ensuring:

- Clear separation between **domain**, **application**, and **infrastructure**
- Independence of business rules from frameworks
- High testability and maintainability
- Reactive, non-blocking execution end-to-end

The API allows:
- Managing franchises and their offices
- Managing products associated with offices
- Retrieving the product with the **highest stock per office** for a given franchise

This project was developed as a **technical test**, with emphasis on:
- Hexagonal / Clean Architecture
- Reactive programming best practices
- Explicit domain modeling
- Clear separation of responsibilities

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot
- Spring WebFlux
- Spring Data R2DBC
- Project Reactor
- PostgreSQL
- Lombok
- JUnit 5
- Mockito
- Reactor Test (StepVerifier)

---

## 🧱 Architecture Overview (Hexagonal)

The application follows **Hexagonal Architecture**, also known as **Ports & Adapters**.

### Core Layers

#### 🟡 Domain Layer

- Contains **pure business models** (`record` / entities)
- Enforces **business rules and invariants**
- No dependency on Spring or infrastructure concerns
- Examples:
    - `Product`, `Office`, `Franchise`
    - Domain-level normalization (e.g. name formatting)

#### 🔵 Application Layer

- Contains **use cases** and application services
- Orchestrates domain logic
- Defines **ports** (interfaces) for external dependencies

**Packages:**
- `application.port.in`
- `application.port.out`
- `application.usecase`

#### ⚪ Infrastructure Layer

- Contains **adapters** that implement application ports
- Framework and technology-specific code
- Examples:
    - REST controllers (WebFlux)
    - Persistence adapters (R2DBC)
    - Spring configuration

**Packages:**
- `infrastructure.web`
- `infrastructure.persistence`
- `infrastructure.config`

---

## 🔌 Ports & Adapters

- **Inbound Ports** define what the application can do (use cases)
- **Outbound Ports** define what the application needs (repositories, external systems)
- **Adapters** translate between the domain model and external representations

Example:

- `OfficeRepositoryPort` → Application Port
- `OfficeRepositoryAdapter` → Infrastructure Adapter
- `OfficeRepository (R2dbcRepository)` → Framework-specific implementation

---

## 📁 Project Structure

```
com.seti.technical_test
├── domain
│   └── model
├── application
│   ├── port
│   │   ├── in
│   │   └── out
│   └── service
└── infrastructure
    ├── controller
    ├── persistence
    │   ├── adapter
    │   ├── entity
    │   └── repository
    │── exception
    └── security

```

---

## 🔁 Reactive Design Considerations

- `Mono` for single-result operations
- `Flux` for multiple-result operations
- Fully non-blocking (no `block()` or `subscribe()`)
- Reactive repositories using R2DBC
- Domain logic remains reactive-friendly

---

## 🧪 Testing Strategy

- Unit tests focused on:
    - Domain logic
    - Use cases
    - Adapters
- No framework dependency in domain tests
- JUnit 5 + Mockito
- Reactor Test (`StepVerifier`)
- Code coverage ≥ 80%

---

## ▶️ Running the Application

```bash
mvn spring-boot:run
```

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📈 Design Decisions Summary

- Hexagonal Architecture to isolate business logic
- WebFlux for scalability and non-blocking IO
- R2DBC to preserve reactive flows
- Domain-driven normalization and validation
- Frameworks treated as implementation details

---

## 👨‍💻 Author

Ricardo Alonso Rincón Vega

