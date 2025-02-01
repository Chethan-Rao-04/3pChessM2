# Frontend Documentation

## Overview
This document details the frontend architecture and implementation of the Three Player Chess game. The frontend is built as a modern web application that provides an interactive and responsive gaming experience.

## Technology Stack

### Core Technologies
- HTML5 Canvas for game board rendering
- CSS3 for styling and animations
- JavaScript (ES6+) for game logic
- WebSocket for real-time updates

### Dependencies
- No external JavaScript frameworks (vanilla JS)
- Custom CSS (no frameworks)
- WebSocket client library

## Architecture

### Component Structure
```
app/
├── src/
│   └── main/
│       └── resources/
│           └── static/
│               ├── css/
│               │   ├── board.css
│               │   ├── pieces.css
│               │   └── main.css
│               ├── js/
│               │   ├── board/
│               │   │   ├── renderer.js
│               │   │   └── interaction.js
│               │   ├── game/
│               │   │   ├── state.js
│               │   │   └── moves.js
│               │   ├── network/
│               │   │   ├── api.js
│               │   │   └── websocket.js
│               │   └── main.js
│               └── index.html
```

## UI Components

### Game Board
- Hexagonal layout
- Three player sections
- Square highlighting
- Move indicators
- Piece animations

### Pieces
- SVG-based piece designs
- Color-coded for players
- Drag and drop support
- Hover effects
- Movement animations

### Game Controls
- Turn indicator
- Move history
- Game status
- Player information
- Action buttons

## Interaction Design

### Mouse Controls
1. Piece Selection
   - Click to select
   - Highlight valid moves
   - Click again to deselect

2. Move Execution
   - Click and drag
   - Drop on valid square
   - Automatic move animation

3. Special Moves
   - Castling via king movement
   - Pawn promotion dialog
   - Special piece interactions

### Touch Support
1. Mobile Interactions
   - Tap to select
   - Tap to move
   - Pinch to zoom
   - Drag to pan

2. Gesture Recognition
   - Double tap for information
   - Long press for options
   - Swipe for history

## State Management

### Game State
```javascript
{
   board: {
      positions: Map<Position, Piece>,
              currentTurn: PlayerColor,
              moveHistory: Array<Move>,
              validMoves: Array<Position>
   },
   ui: {
      selectedPiece: Piece | null,
              highlightedSquares: Array<Position>,
              animation: AnimationState,
              dialog: DialogState
   },
   network: {
      connected: boolean,
              lastSync: timestamp,
              pendingMoves: Array<Move>
   }
}
```

### State Updates
1. User Actions
   - Piece selection
   - Move execution
   - UI interactions

2. Network Events
   - Opponent moves
   - Game state changes
   - Connection status

## Network Communication

### REST API Integration
- Game state retrieval
- Move submission
- Player management
- Error handling

### WebSocket Events
1. Outgoing Messages
   - Move submissions
   - State requests
   - Player actions

2. Incoming Messages
   - State updates
   - Opponent moves
   - Game events

## Rendering Engine

### Canvas Rendering
1. Board Layout
   - Hexagonal grid
   - Section boundaries
   - Square colors

2. Piece Rendering
   - SVG sprites
   - Position calculation
   - Animation frames

3. Effects
   - Move highlights
   - Selection indicators
   - Last move marker

### Performance Optimization
1. Render Optimization
   - Request animation frame
   - Layer caching
   - Sprite batching

2. Memory Management
   - Asset preloading
   - Garbage collection
   - Event cleanup

## Error Handling

### User Feedback
- Invalid move indicators
- Network error messages
- State sync notifications
- Loading states

### Recovery Strategies
1. Network Issues
   - Automatic reconnection
   - State resynchronization
   - Move queue management

2. State Inconsistencies
   - Forced refresh
   - State rollback
   - Error logging

## Accessibility

### Keyboard Support
- Arrow key navigation
- Piece selection (Enter)
- Move execution (Space)
- Menu access (Alt)

### Screen Readers
- ARIA labels
- Role annotations
- State announcements
- Move descriptions

## Testing

### Unit Tests
- Component logic
- State management
- Move validation
- Event handling

### Integration Tests
- User interactions
- Network communication
- State transitions
- Error scenarios

## Build Process

### Development
```bash
# Install dependencies
npm install

# Start development server
npm start

# Run tests
npm test
```

### Production
```bash
# Create production build
npm run build

# Run production preview
npm run serve
```

## Future Enhancements
1. UI Improvements
   - Themes support
   - Responsive design
   - Animation options
   - Custom piece sets

2. Features
   - Move analysis
   - Game replay
   - Tournament UI
   - Statistics display

3. Performance
   - WebGL rendering
   - Worker threads
   - Asset optimization
   - State persistence 