# 📦 Technical Test -- Reactive Franchise Management API

## 🧩 Overview

This project is a **Reactive REST API** built with **Spring WebFlux**
and **Spring Data R2DBC**, designed to manage **franchises, offices, and
products**.

The main goal of the application is to provide a **non-blocking,
scalable solution** that allows: - Managing franchises and their
offices - Managing products associated with offices - Retrieving the
product with the **highest stock per office** for a given franchise

The project was developed as a **technical test**, focusing on: - Clean
architecture - Reactive programming best practices - Clear separation of
concerns - High unit test coverage

------------------------------------------------------------------------

## 🛠️ Tech Stack

-   Java 17\
-   Spring Boot\
-   Spring WebFlux\
-   Spring Data R2DBC\
-   Project Reactor\
-   PostgreSQL\
-   Lombok\
-   JUnit 5\
-   Mockito\
-   Reactor Test (StepVerifier)

------------------------------------------------------------------------

## 🧱 Architecture Overview

The application follows a **layered architecture**, with clear
responsibilities per layer:

Controller → Service → Repository

### Key Principles

-   Reactive-first design (Mono / Flux)
-   No blocking calls
-   DTO-based communication
-   Thin repositories
-   Business rules enforced in services

------------------------------------------------------------------------

## 📁 Project Structure

com.seti.technical_test\
├── controller\
├── dto\
├── entity\
├── exception\
├── repository\
├── service\
│ ├── impl\
│ └── interfaces\
└── config

------------------------------------------------------------------------

## 🔁 Reactive Design Considerations

-   Mono for single-result operations
-   Flux for multiple-result operations
-   switchIfEmpty for not-found scenarios
-   StepVerifier for reactive unit testing
-   No usage of block() or subscribe()

------------------------------------------------------------------------

## 🧪 Testing Strategy

-   Unit tests only
-   No integration tests
-   JUnit 5 + Mockito
-   Reactor Test
-   Coverage ≥ 80%

------------------------------------------------------------------------

## ▶️ Running the Application

mvn spring-boot:run

------------------------------------------------------------------------

## 🧪 Running Tests

mvn test

------------------------------------------------------------------------

## 📈 Design Decisions Summary

-   WebFlux over MVC for scalability
-   R2DBC to preserve reactive flow
-   DTOs to decouple API and persistence
-   Service-level validations
-   Clean separation of concerns

------------------------------------------------------------------------

## 👨‍💻 Author

Ricardo Alonso Rincón Vega.
