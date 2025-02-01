# Testing Documentation

## Test Structure
All tests are located in the `app/src/test` directory:
```
app/src/test/
└── java/
    └── com/ccd/chess/
        ├── model/
        │   ├── entity/
        │   └── dto/
        ├── service/
        ├── controller/
        └── util/
```

## Running Tests
```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.ccd.chess.model.entity.PieceTest"

# Run with coverage report
./gradlew test jacocoTestReport
```

## Test Categories

### 1. Unit Tests
- Located in respective package directories
- Test individual components in isolation
- Mock dependencies as needed

### 2. Integration Tests
- Located in `controller` package
- Test API endpoints
- Test frontend-backend integration

### 3. Frontend Tests
- Located in `app/src/test/resources/static/js`
- Test UI components
- Test API integration

## Overview
This document outlines the testing strategy and implementation for the Three Player Chess game. The testing approach ensures comprehensive coverage of game logic, piece movements, and edge cases.

## Testing Framework
- JUnit 5 (Jupiter)
- Mockito for mocking
- JaCoCo for test coverage analysis
- Parameterized tests for comprehensive scenarios

## Test Organization

### 1. Piece Tests
Located in `app/src/test/java/com/ccd/chess/model/entity/pieces/`

#### Common Test Patterns
Each piece has dedicated test classes following these patterns:
- Setup validation
- Movement rules verification
- Special move validation
- Boundary condition testing
- Color-specific behavior

#### Specific Test Cases
1. **Pawn Tests** (`PawnTest.java`)
   - Forward movement
   - Diagonal capture
   - Initial two-square move
   - Cross-section movement

2. **King Tests** (`KingTest.java`)
   - Basic movement
   - Castling (both sides)
   - Check validation
   - Cross-section movement

3. **Queen Tests** (`QueenTest.java`)
   - Diagonal movement
   - Straight movement
   - Cross-section traversal
   - Capture validation

4. **Special Pieces**
   - Hawk movement patterns
   - Vortex special rules
   - Cross-board interactions

### 2. Board Tests
- Initial setup validation
- Position tracking
- Move validation
- Game state management

### 3. Game Logic Tests
- Turn management
- Check detection
- Checkmate scenarios
- Draw conditions
- Three-player interactions

## Test Data Management

### Data Providers
Located in `app/src/test/java/com/ccd/chess/test/DataProvider.java`
- Piece configurations
- Board positionOnBoards
- Move sequences
- Game states

### Test Utilities
- Board setup helpers
- Move validation utilities
- State verification tools

## Coverage Goals

### Line Coverage
- Minimum 85% coverage for core game logic
- 100% coverage for piece movement rules
- 90% coverage for board management

### Branch Coverage
- Minimum 80% for conditional logic
- 100% for move validation
- 90% for game state transitions

## Testing Best Practices

### 1. Test Independence
- Each test should be self-contained
- Clean board state between tests
- No test interdependencies

### 2. Naming Conventions
Format: `methodName_scenario_expectedOutcome`
Example: `isLegalMove_pawnMoveForwardToEmptySquare_True`

### 3. Test Documentation
- Clear test descriptions
- Documented test scenarios
- Explained edge cases

### 4. Parameterized Testing
Used for:
- Multiple piece colors
- Various board positionOnBoards
- Different game states
- Move combinations

## Edge Cases

### 1. Board Boundaries
- Piece movement at edges
- Cross-section movement
- Invalid positionOnBoard handling

### 2. Special Moves
- Castling requirements
- Pawn promotion
- Special piece interactions

### 3. Game States
- Check resolution
- Checkmate verification
- Draw conditions
- Three-player interactions

## Continuous Integration

### Build Pipeline
- Automated test execution
- Coverage reporting
- Failed test reporting
- Performance metrics

### Quality Gates
- Minimum coverage thresholds
- No failing tests
- Performance benchmarks
- Code style compliance

## Test Maintenance

### 1. Regular Updates
- Test case reviews
- Coverage analysis
- Performance optimization
- Documentation updates

### 2. Regression Testing
- Automated regression suite
- Critical path testing
- Performance regression checks

## Future Test Enhancements
- Integration tests
- End-to-end testing
- Load testing
- UI automation tests 