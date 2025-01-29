# API Documentation

## Overview
This document describes the REST API endpoints available in the Three Player Chess game. The API provides interfaces for game management, move submission, and state retrieval.

## Base URL
```
http://localhost:8080/api/v1
```

## Authentication
Currently, the API does not require authentication. Future versions may implement authentication mechanisms.

## Endpoints

### Game Management

#### Create New Game
```http
POST /game
```

**Request Body**: None

**Response**:
```json
{
    "gameId": "string",
    "board": {
        "positions": {},
        "currentTurn": "BLUE"
    },
    "status": "ACTIVE"
}
```

#### Get Game State
```http
GET /game/{gameId}
```

**Parameters**:
- `gameId`: Unique game identifier

**Response**:
```json
{
    "gameId": "string",
    "board": {
        "positions": {},
        "currentTurn": "BLUE"
    },
    "status": "ACTIVE",
    "highlightPolygons": []
}
```

### Move Management

#### Submit Move
```http
POST /game/{gameId}/move
```

**Parameters**:
- `gameId`: Unique game identifier

**Request Body**:
```json
{
    "startPosition": "string",
    "endPosition": "string",
    "player": "BLUE"
}
```

**Response**:
```json
{
    "valid": true,
    "board": {
        "positions": {},
        "currentTurn": "RED"
    },
    "message": "Move successful"
}
```

#### Get Valid Moves
```http
GET /game/{gameId}/moves/{position}
```

**Parameters**:
- `gameId`: Unique game identifier
- `position`: Board position identifier

**Response**:
```json
{
    "validMoves": [
        "string"
    ]
}
```

### Game Status

#### Get Game Result
```http
GET /game/{gameId}/result
```

**Parameters**:
- `gameId`: Unique game identifier

**Response**:
```json
{
    "status": "COMPLETED",
    "winner": "BLUE",
    "reason": "CHECKMATE"
}
```

## Data Types

### Position Format
Board positions are represented as strings in the format:
- First character: Section (B: Blue, R: Red, G: Green)
- Second character: File (A-H)
- Third character: Rank (1-8)

Example: "BE4" represents Blue section, E file, rank 4

### Piece Representation
Pieces are represented as two-character strings:
- First character: Color (B: Blue, R: Red, G: Green)
- Second character: Piece type (K: King, Q: Queen, R: Rook, B: Bishop, N: Knight, P: Pawn, H: Hawk, V: Vortex)

Example: "BK" represents Blue King

### Game Status
Possible values:
- ACTIVE
- COMPLETED
- DRAW
- ABANDONED

### Player Colors
- BLUE
- RED
- GREEN

## Error Handling

### Error Response Format
```json
{
    "error": {
        "code": "string",
        "message": "string",
        "details": {}
    }
}
```

### Common Error Codes
- 400: Bad Request
- 404: Resource Not Found
- 409: Conflict
- 500: Internal Server Error

### Specific Error Cases
1. Invalid Move
```json
{
    "error": {
        "code": "INVALID_MOVE",
        "message": "The requested move is not valid",
        "details": {
            "reason": "PIECE_BLOCKED"
        }
    }
}
```

2. Wrong Turn
```json
{
    "error": {
        "code": "WRONG_TURN",
        "message": "Not the player's turn",
        "details": {
            "currentTurn": "RED",
            "attemptedPlayer": "BLUE"
        }
    }
}
```

## Rate Limiting
- 100 requests per minute per IP
- 1000 requests per hour per IP

## Versioning
The API uses URL versioning (v1). Future versions will be introduced as needed while maintaining backward compatibility.

## WebSocket Support
Real-time game updates are available through WebSocket connection:
```
ws://localhost:8080/ws/game/{gameId}
```

### WebSocket Events
1. Move Made
```json
{
    "type": "MOVE",
    "data": {
        "from": "BE2",
        "to": "BE4",
        "piece": "BP",
        "player": "BLUE"
    }
}
```

2. Game State Changed
```json
{
    "type": "STATE_CHANGE",
    "data": {
        "status": "CHECK",
        "player": "RED"
    }
}
```

## Future Enhancements
- Authentication/Authorization
- Game history retrieval
- Move analysis
- Player statistics
- Tournament support 