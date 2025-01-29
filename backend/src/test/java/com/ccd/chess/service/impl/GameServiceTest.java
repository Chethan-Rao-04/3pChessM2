package com.ccd.chess.service.impl;

import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.model.dto.GameState;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Pawn;
import com.ccd.chess.service.interfaces.IBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private IBoardService boardService;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService(boardService);
        // Setup default behavior
        when(boardService.getWebViewBoard()).thenReturn(new HashMap<>());
    }

    @Test
    void onClick_ValidMove_UpdatesGameState() throws InvalidPositionException {
        // Arrange
        Position expectedPosition = Position.BE1;
        when(boardService.isCurrentPlayersPiece(expectedPosition)).thenReturn(true);
        when(boardService.getPossibleMoves(expectedPosition)).thenReturn(Set.of(Position.BE2, Position.BE3));

        // Act
        GameState result = gameService.onClick("BE1");
        
        // Assert
        assertNotNull(result);
        verify(boardService).isCurrentPlayersPiece(expectedPosition);
        verify(boardService).getPossibleMoves(expectedPosition);
        verify(boardService).getWebViewBoard();
    }

    @Test
    void onClick_InvalidMove_ReturnsUnchangedState() throws InvalidPositionException {
        // Arrange
        Position expectedPosition = Position.BE1;
        when(boardService.isCurrentPlayersPiece(expectedPosition)).thenReturn(true);
        when(boardService.getPossibleMoves(expectedPosition)).thenReturn(Collections.emptySet());
        
        // Act
        GameState result = gameService.onClick("BE1");
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getHighlightedPolygons().isEmpty());
        verify(boardService).isCurrentPlayersPiece(expectedPosition);
        verify(boardService).getPossibleMoves(expectedPosition);
    }

    @Test
    void onClick_InvalidMoveException_HandlesError() throws InvalidMoveException, InvalidPositionException {
        // Arrange
        Position startPos = Position.BE1;
        Position endPos = Position.BE2;
        
        // Setup first click
        when(boardService.isCurrentPlayersPiece(startPos)).thenReturn(true);
        when(boardService.getPossibleMoves(startPos)).thenReturn(Set.of(endPos));
        
        // First click
        gameService.onClick("BE1");
        
        // Setup second click
        doThrow(new InvalidMoveException("Invalid move")).when(boardService).move(startPos, endPos);
        
        // Act - Second click
        GameState result = gameService.onClick("BE2");
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getHighlightedPolygons().isEmpty());
        verify(boardService).move(startPos, endPos);
    }

    @Test
    void getTurn_ReturnsCurrentTurn() {
        // Arrange
        when(boardService.getTurn()).thenReturn(Colour.BLUE);

        // Act
        Colour result = gameService.getTurn();

        // Assert
        assertEquals(Colour.BLUE, result);
        verify(boardService, times(1)).getTurn();
    }

    @Test
    void onClick_NoPieceSelected_NoHighlights() throws InvalidPositionException {
        // Arrange
        Position expectedPosition = Position.BE1;
        when(boardService.isCurrentPlayersPiece(expectedPosition)).thenReturn(false);
        
        // Act
        GameState result = gameService.onClick("BE1");
        
        // Assert
        assertTrue(result.getHighlightedPolygons().isEmpty());
        verify(boardService).isCurrentPlayersPiece(expectedPosition);
    }

    // Integration tests with real BoardServiceImpl
    @Test
    void integrationTest_FullGameFlow() {
        // Create a real GameService with real dependencies
        BoardServiceImpl realBoardService = new BoardServiceImpl();
        GameService realGameService = new GameService(realBoardService);
        
        // Initial state checks
        Map<String, String> initialBoard = realGameService.getBoard();
        assertNotNull(initialBoard);
        assertFalse(initialBoard.isEmpty());
        
        // Simulate a full move
        // 1. Select a piece (e.g., a pawn)
        GameState firstClick = realGameService.onClick("BE2"); // Blue pawn initial position
        assertNotNull(firstClick);
        assertFalse(firstClick.getHighlightedPolygons().isEmpty());
        
        // 2. Move the piece
        GameState secondClick = realGameService.onClick("BE3"); // Move pawn forward
        assertNotNull(secondClick);
        assertTrue(secondClick.getHighlightedPolygons().isEmpty());
        
        // Verify turn changed
        assertNotEquals(Colour.BLUE, realGameService.getTurn()); // Turn should have changed
        
        // Verify board state updated
        Map<String, String> updatedBoard = realGameService.getBoard();
        assertNotEquals(initialBoard, updatedBoard);
    }

    @Test
    void integrationTest_CompleteGame() {
        // Create a real GameService with real dependencies
        BoardServiceImpl realBoardService = new BoardServiceImpl();
        GameService realGameService = new GameService(realBoardService);
        
        // Play a sequence of valid moves
        // Blue's turn
        GameState state = realGameService.onClick("BE2"); // Select pawn
        assertNotNull(state);
        state = realGameService.onClick("BE3"); // Move pawn
        assertNotNull(state);
        
        // Green's turn
        state = realGameService.onClick("GE2"); // Select pawn
        assertNotNull(state);
        state = realGameService.onClick("GE3"); // Move pawn
        assertNotNull(state);
        
        // Red's turn
        state = realGameService.onClick("RE2"); // Select pawn
        assertNotNull(state);
        state = realGameService.onClick("RE3"); // Move pawn
        assertNotNull(state);
        
        // Verify game state
        assertNotNull(state);
        assertNotNull(state.getBoard());
        assertFalse(state.isGameOver());
    }
} 