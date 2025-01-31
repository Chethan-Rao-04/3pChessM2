package com.ccd.chess.service.impl;

import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.model.dto.GameState;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private IBoardService boardService;

    private GameServiceImpl gameServiceImpl;

    @BeforeEach
    void setUp() {
        gameServiceImpl = new GameServiceImpl(boardService);
    }

    @Test
    void onSelect_ValidMove_UpdatesGameState() throws InvalidPositionException {
        // Arrange
        PositionOnBoard expectedPositionOnBoard = PositionOnBoard.BE1;
        Set<PositionOnBoard> possibleMoves = Set.of(PositionOnBoard.BE2, PositionOnBoard.BE3);
        Map<String, String> mockBoard = new HashMap<>();
        mockBoard.put("Be1", "BP"); // Blue Pawn at BE1
        
        when(boardService.isSelfPiece(expectedPositionOnBoard)).thenReturn(true);
        when(boardService.fetchPossibleMovements(expectedPositionOnBoard)).thenReturn(possibleMoves);
        when(boardService.getWebViewBoard()).thenReturn(mockBoard);

        // Act
        GameState result = gameServiceImpl.onSelect("be1");
        
        // Assert
        assertNotNull(result);
        assertNotNull(result.getHighlightedPolygons());
        assertTrue(result.getHighlightedPolygons().contains("Be2"));
        assertTrue(result.getHighlightedPolygons().contains("Be3"));
    }

    @Test
    void onSelect_InvalidMove_ReturnsUnchangedState() throws InvalidPositionException {
        // Arrange
        PositionOnBoard expectedPositionOnBoard = PositionOnBoard.BE1;
        lenient().when(boardService.isSelfPiece(expectedPositionOnBoard)).thenReturn(true);
        lenient().when(boardService.fetchPossibleMovements(expectedPositionOnBoard)).thenReturn(Collections.emptySet());
        lenient().when(boardService.getWebViewBoard()).thenReturn(new HashMap<>());
        
        // Act
        GameState result = gameServiceImpl.onSelect("be1");
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getHighlightedPolygons().isEmpty());
    }

    @Test
    void onSelect_InvalidMoveException_HandlesError() throws InvalidMoveException, InvalidPositionException {
        // Arrange
        PositionOnBoard startPos = PositionOnBoard.BE1;
        PositionOnBoard endPos = PositionOnBoard.BE2;
        
        // Setup first click
        lenient().when(boardService.isSelfPiece(startPos)).thenReturn(true);
        lenient().when(boardService.fetchPossibleMovements(startPos)).thenReturn(Set.of(endPos));
        lenient().when(boardService.getWebViewBoard()).thenReturn(new HashMap<>());
        lenient().doThrow(new InvalidMoveException("Invalid move")).when(boardService).movePiece(startPos, endPos);
        
        // First click
        gameServiceImpl.onSelect("be1");
        
        // Act - Second click
        GameState result = gameServiceImpl.onSelect("be2");
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getHighlightedPolygons().isEmpty());
    }

    @Test
    void fetchTurn_ReturnsCurrentTurn() {
        // Arrange
        when(boardService.fetchTurn()).thenReturn(Colour.BLUE);

        // Act
        Colour result = gameServiceImpl.fetchTurn();

        // Assert
        assertEquals(Colour.BLUE, result);
    }

    @Test
    void onSelect_NoPieceSelected_NoHighlights() throws InvalidPositionException {
        // Arrange
        PositionOnBoard expectedPositionOnBoard = PositionOnBoard.BE1;
        lenient().when(boardService.isSelfPiece(expectedPositionOnBoard)).thenReturn(false);
        lenient().when(boardService.getWebViewBoard()).thenReturn(new HashMap<>());
        
        // Act
        GameState result = gameServiceImpl.onSelect("be1");
        
        // Assert
        assertTrue(result.getHighlightedPolygons().isEmpty());
    }

    // Integration tests with real BoardServiceImpl
    @Test
    void integrationTest_FullGameFlow() {
        // Create a real GameServiceImpl with real dependencies
        BoardServiceImpl realBoardService = new BoardServiceImpl();
        GameServiceImpl realGameServiceImpl = new GameServiceImpl(realBoardService);
        
        // Initial state checks
        Map<String, String> initialBoard = realGameServiceImpl.fetchBoard();
        assertNotNull(initialBoard);
        assertFalse(initialBoard.isEmpty());
        assertTrue(initialBoard.containsKey("Be2"), "Initial board should have a piece at Be2");
        assertTrue(initialBoard.get("Be2").endsWith("P"), "Be2 should contain a pawn");
        
        // Simulate a full move
        // 1. Select a piece (e.g., a pawn)
        GameState firstClick = realGameServiceImpl.onSelect("be2"); // Blue pawn initial position
        assertNotNull(firstClick);
        assertNotNull(firstClick.getHighlightedPolygons(), "Highlighted polygons should not be null");
        
        // The pawn should be able to move forward one or two squares from its starting position
        boolean canMoveForward = firstClick.getHighlightedPolygons().contains("Be3") || 
                                firstClick.getHighlightedPolygons().contains("Be4");
        assertTrue(canMoveForward, "Pawn should be able to move forward");
        
        // 2. Move the piece
        GameState secondClick = realGameServiceImpl.onSelect("be3"); // Move pawn forward
        assertNotNull(secondClick);
        
        // Verify turn changed
        assertNotEquals(Colour.BLUE, realGameServiceImpl.fetchTurn()); // Turn should have changed
        
        // Verify board state updated
        Map<String, String> updatedBoard = realGameServiceImpl.fetchBoard();
        assertNotNull(updatedBoard);
        assertNotEquals(initialBoard, updatedBoard);
        
        // Verify the pawn has moved
        assertFalse(updatedBoard.containsKey("Be2"), "Pawn should no longer be at Be2");
        assertTrue(updatedBoard.containsKey("Be3"), "Pawn should now be at Be3");
        String pawnPosition = updatedBoard.get("Be3");
        assertNotNull(pawnPosition, "Pawn should be at Be3");
        assertTrue(pawnPosition.endsWith("P"), "PositionOnBoard should contain a pawn");
    }

    @Test
    void integrationTest_CompleteGame() {
        // Create a real GameServiceImpl with real dependencies
        BoardServiceImpl realBoardService = new BoardServiceImpl();
        GameServiceImpl realGameServiceImpl = new GameServiceImpl(realBoardService);
        
        // Play a sequence of valid moves
        // Blue's turn
        GameState state = realGameServiceImpl.onSelect("be2"); // Select pawn
        assertNotNull(state);
        state = realGameServiceImpl.onSelect("be3"); // Move pawn
        assertNotNull(state);
        
        // Green's turn
        state = realGameServiceImpl.onSelect("ge2"); // Select pawn
        assertNotNull(state);
        state = realGameServiceImpl.onSelect("ge3"); // Move pawn
        assertNotNull(state);
        
        // Red's turn
        state = realGameServiceImpl.onSelect("re2"); // Select pawn
        assertNotNull(state);
        state = realGameServiceImpl.onSelect("re3"); // Move pawn
        assertNotNull(state);
        
        // Verify game state
        assertNotNull(state);
        assertNotNull(state.getBoard());
        assertFalse(state.isGameOver());
    }
} 