# Three Player Chess - Architecture Documentation

## System Overview
This document describes the architecture and design decisions for the Three Player Chess game. The system is built as a monolithic application using Spring Boot with an integrated frontend.

## Technology Stack

### Backend:
- Java 17
- Spring Boot 2.7.x
- JUnit 5 for testing
- JaCoCo for test coverage
- Gradle for build management

### Frontend (Integrated in app/src/main/resources/static):
- HTML/CSS/JavaScript
- RESTful API integration
- Browser-based game board rendering

## Core Components

### 1. Game Logic Layer
- Located in `app/src/main/java/com/ccd/chess/model`
- Implements core chess rules and piece movement
- Handles game state management
- Validates moves and enforces rules

### 2. Service Layer
- Located in `app/src/main/java/com/ccd/chess/service`
- Provides high-level game operations
- Manages game sessions and state
- Implements business logic interfaces

### 3. Controller Layer
- Located in `app/src/main/java/com/ccd/chess/controller`
- Handles HTTP requests
- Manages API endpoints
- Coordinates between frontend and backend

### 4. Frontend Layer
- Located in `app/src/main/resources/static`
- Implements user interface
- Handles client-side game rendering
- Manages user interactions

## Testing Strategy

### Unit Tests
- Comprehensive test coverage for all pieces
- Parameterized tests for different scenarios
- Test data providers for reusable test cases
- Movement validation tests
- Special move tests (castling, etc.)

### Test Organization
- Piece-specific test classes
- Board state tests
- Game flow tests
- Edge case coverage

### Deployment Strategy
- Single deployment artifact
- Docker containerization
- Kubernetes deployment
- Continuous integration pipeline
- Automated testing

## Key Design Decisions

### 1. Three-Player Support
- Custom board layout for three players
- Color-based player identification (Silver, bronze, gold)
- Turn management for three players
- Special rules adaptation

### 2. Move Validation
- Two-step validation process
  1. Piece-specific movement rules
  2. Board-level validation
- Special move handling
- Check and checkmate detection

### 3. State Management
- Immutable game states
- Clear state transitions
- History tracking
- Position tracking using enums

## API Design

### REST Endpoints
- Game creation
- Move submission
- Game state retrieval
- Player management

### Data Flow
1. Frontend sends move request
2. app validates move
3. Game state updated
4. New state returned to frontend

## Security Considerations
- Input validation
- Move validation
- Session management
- Player authentication

## Future Considerations
- Multiplayer support
- Game persistence
- AI opponents
- Move replay functionality