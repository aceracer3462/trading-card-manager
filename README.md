# 🃏 Trading Card Collection Manager

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3-purple.svg)](https://getbootstrap.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📋 Overview
A full-stack web application for managing trading card collections across multiple games (Magic: The Gathering, Pokémon, Yu-Gi-Oh!, Vanguard). Built as a CS 157A Database Systems final project.

## ✨ Features
- **User Authentication** - Secure registration/login with session management
- **Card Database** - 50+ sample cards with real images
- **Collection Management** - Track cards with condition-based pricing
- **Deck Building** - Create and manage custom decks
- **Advanced Search** - Filter by name, type, rarity, price, game
- **Statistics Dashboard** - Real-time collection valuation
- **RESTful API** - Complete API with Swagger documentation
- **Responsive Design** - Mobile-friendly Bootstrap 5 interface

## 🏗️ Architecture
Three-Tier Architecture:

  │ Presentation │    │ Application │      │ Database │

  │ (Thymeleaf) │◄──►│ (Spring Boot) │◄──►│ (PostgreSQL) │
 
  │ Bootstrap 5 │       │ Java 21 │        │ JDBC/JPA │


## 🛠️ Tech Stack
| Component | Technology |
|-----------|------------|
| Backend | Java 21, Spring Boot 3.2, Spring Data JPA |
| Database | PostgreSQL 15, Hibernate 6.3 |
| Frontend | Thymeleaf 3.1, Bootstrap 5.3, JavaScript |
| Build Tool | Maven 3.9 |
| API Docs | Swagger/OpenAPI 3.0 |

## 🚀 Quick Start

### Prerequisites
- Java 21 or later
- PostgreSQL 15
- Maven 3.9+

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/yourusername/trading-card-manager.git
cd trading-card-manager

# Create database
createdb tradingcards

# Run schema script (optional - Spring Boot will create tables)
psql -d tradingcards -f src/main/resources/db/schema.sql

#Edit src/main/resources/application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/tradingcards
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# Build the application
mvn clean package

# Run the application
mvn spring-boot:run

# Or run the JAR file
java -jar target/trading-card-webapp.jar

Access the application:

🌐 Web Interface: http://localhost:8080

📖 API Documentation: http://localhost:8080/swagger-ui.html

🔐 Demo Credentials: demo / demo123

#Project Structure
trading-card-manager/
├── src/main/java/com/tradingcards/
│   ├── config/          # Configuration classes
│   ├── controller/      # MVC and REST controllers
│   ├── model/          # JPA entities
│   ├── repository/     # Data access layer
│   ├── service/        # Business logic
│   └── util/          # Utility classes
├── src/main/resources/
│   ├── static/         # CSS, JavaScript, images
│   ├── templates/      # Thymeleaf HTML templates
│   └── application.properties
├── src/test/           # Unit and integration tests
├── docs/              # Documentation
└── pom.xml           # Maven configuration

#Database Schema
-- Main Entities
users          # User accounts
cards          # Card catalog
user_collections # User's collected cards
decks          # User-created decks
deck_cards     # Cards in decks
card_sets      # Card expansion sets
games          # Supported TCGs

🔧 API Endpoints
Card Management
GET /api/v1/cards - List all cards (paginated)

GET /api/v1/cards/{id} - Get card details

GET /api/v1/cards/search - Search cards with filters

Collection Management
GET /api/v1/collection - Get user's collection

POST /api/v1/collection - Add card to collection

DELETE /api/v1/collection/{id} - Remove from collection

Deck Management
GET /api/v1/decks - Get user's decks

POST /api/v1/decks - Create new deck

POST /api/v1/decks/{id}/cards - Add card to deck

# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report

📈 Features in Detail
Condition-Based Pricing
Cards are automatically priced based on condition:

Mint: 100% market value

Near Mint: 95% market value

Excellent: 85% market value

Good: 70% market value

Played: 50% market value

Poor: 25% market value

Multi-Game Support
Magic: The Gathering

Pokémon

Yu-Gi-Oh!

Vanguard

Search & Filtering
Search by name, type, rarity

Filter by game and price range

Sort by any column

Pagination (10 items per page)

#Development
mvn spring-boot:run

Production
Update application.properties with production DB credentials

Build JAR:

bash
mvn clean package -DskipTests

Deploy JAR to server

Docker (Optional)
dockerfile
FROM openjdk:21-jdk-slim
COPY target/trading-card-webapp.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

 Contributing
Fork the repository

Create a feature branch (git checkout -b feature/AmazingFeature)

Commit changes (git commit -m 'Add AmazingFeature')

Push to branch (git push origin feature/AmazingFeature)

Open a Pull Request
