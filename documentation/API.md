# API Documentation

## Overview
This document describes the REST API endpoints available in the Three Player Chess game. The API provides interfaces for game management, move submission, and state retrieval.

## Base URL
All API endpoints are served from the main application:
```
http://localhost:8090/
```

## Implementation Location
The API implementations can be found in:
```
app/src/main/java/com/ccd/chess/controller/
```

## Frontend Integration
The frontend components consuming these APIs are located in:
```
app/src/main/resources/static/js/
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
        "positionOnBoards": {},
        "currentTurn": "SILVER"
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
        "positionOnBoards": {},
        "currentTurn": "SILVER"
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
    "startPositionOnBoard": "string",
    "endPositionOnBoard": "string",
    "player": "SILVER"
}
```

**Response**:
```json
{
    "valid": true,
    "board": {
        "positionOnBoards": {},
        "currentTurn": "SILVER"
    },
    "message": "Move successful"
}
```

#### Get Valid Moves
```http
GET /game/{gameId}/moves/{positionOnBoard}
```

**Parameters**:
- `gameId`: Unique game identifier
- `positionOnBoard`: Board positionOnBoard identifier

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
    "winner": "GOLD",
    "reason": "CHECKMATE"
}
```

## Data Types

### Position Format
Board positionOnBoards are represented as strings in the format:
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

### Player Colors
- GOLD
- SILVER
- BRONZE

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
      "currentTurn": "GOLD",
      "attemptedPlayer": "Silver"
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
    "player": "GOLD"
  }
}
```

2. Game State Changed
```json
{
  "type": "STATE_CHANGE",
  "data": {
    "status": "CHECK",
    "player": "SILVER"
  }
}
```

## Future Enhancements
- Authentication/Authorization
- Game history retrieval
- Move analysis
- Player statistics
- Tournament support 