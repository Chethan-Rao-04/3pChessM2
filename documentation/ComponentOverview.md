# Component Overview

This document provides a simple explanation of what each component in our chess application does.

## Frontend Components

### game.html
The main game page that shows the chess board and game interface.

### game.js
Handles all the interactive features of the game board, like moving pieces and updating the display.

## Controllers

### GameController.java
The traffic controller for the game - handles all incoming requests from the frontend and directs them to the right services.

## Services

### GameService.java
The brain of the game - manages game states, turns, and coordinates all game-related operations.

### BoardServiceImpl.java
Handles everything related to the chess board - piece placement, movement validation, and board state management.

### IMoveValidator.java
Defines rules for how pieces can move - like a rule book for chess piece movements.

## Models

### GameState.java
Keeps track of the current state of the game - whose turn it is, piece positions, etc.

### Pieces:
- **ChessPiece.java**: The parent class for all chess pieces, defines common piece behavior
- **King.java**: Rules and behavior for the King piece
- **Bishop.java**: Rules and behavior for the Bishop piece
- **Pawn.java**: Rules and behavior for the Pawn piece
- **Hawk.java**: Rules and behavior for the special Hawk piece

### PositionOnBoard.java
Handles the coordinates and positions of pieces on the chess board.

## Utilities

### MovementUtil.java
Helper functions for calculating and validating piece movements.

### BoardAdapter.java
Helps convert board data between different formats (like between the frontend and backend).

## Tests

### BishopTest.java & HawkTest.java
Test files that make sure the pieces move correctly according to chess rules.

## Documentation

### Architecture.md
Explains the overall structure and design of the application.

### API.md
Lists all the available API endpoints and how to use them.

### CHANGELOG.md
Keeps track of all changes and updates made to the application. 