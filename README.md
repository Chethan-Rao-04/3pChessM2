# Three Player Chess

A sophisticated implementation of three-player chess with a Spring Boot backend and web-based frontend.

## Features

- Three-player chess gameplay
- Modern web interface
- Real-time move validation
- Special pieces (Hawk, Vortex)
- Cross-board movement
- Castling support
- Check/Checkmate detection

## Prerequisites

- JDK 17 or higher
- Gradle 7.x or higher
- Modern web browser
- Git

## Quick Start

1. Clone the repository:
```bash
git clone https://github.com/yourusername/3pChessM2.git
cd 3pChessM2
```

2. Build the project:
```bash
./gradlew clean build
```

3. Run the application:
```bash
./gradlew bootRun
```

4. Open your browser and navigate to:
```
http://localhost:8080
```

## Project Structure

The project is organized into two main components:

### Backend (`/backend`)
- Spring Boot application
- Game logic implementation
- REST API endpoints
- Test suites

### Frontend (`/webapp`)
- Web interface
- Game board rendering
- Move submission
- State management

## Development

### Running Tests
```bash
./gradlew test
```

### Building for Production
```bash
./gradlew build
```

### API Documentation
API documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

## Game Rules

### Pieces
- Standard chess pieces (King, Queen, Rook, Bishop, Knight, Pawn)
- Special pieces:
  - Hawk: Combines Knight and Bishop movements
  - Vortex: Special movement patterns

### Movement
- Clockwise turn order
- Cross-board movement allowed
- Special rules for three-player interactions

### Victory Conditions
- Checkmate any opponent
- Last player standing wins
- Special victory conditions for three-way conflicts

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Documentation

Detailed documentation is available in the `/documentation` directory:
- Architecture.md: System design and components
- Testing.md: Testing strategy and coverage
- Development.md: Development guidelines and workflow

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Chess piece designs
- Game rule adaptations
- Community contributions

## Support

For support, please open an issue in the GitHub repository or contact the development team.

## Roadmap

- AI opponent implementation
- Tournament mode
- Game replay system
- Statistics tracking
- Mobile optimization
