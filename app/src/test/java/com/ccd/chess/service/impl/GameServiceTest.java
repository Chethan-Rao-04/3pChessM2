//package com.ccd.chess.service.impl;
//
//import com.ccd.chess.exceptions.InvalidMoveException;
//import com.ccd.chess.exceptions.InvalidPositionException;
//import com.ccd.chess.model.dto.GameState;
//import com.ccd.chess.model.entity.enums.Colour;
//import com.ccd.chess.model.entity.enums.PositionOnBoard;
//import com.ccd.chess.service.interfaces.BoardService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(MockitoExtension.class)
//class GameServiceTest {
//
//    @Mock
//    private BoardServiceImpl boardService;
//
//    private GameServiceImpl GameServiceImpl;
//
//    @BeforeEach
//    void setUp() {
//        GameServiceImpl = new GameServiceImpl(boardService);
//    }
//
//    @Test
//    void onClick_ValidMove_UpdatesGameState() throws InvalidPositionException {
//        // Arrange
//        PositionOnBoard expectedPositionOnBoard = PositionOnBoard.BE1;
//        Set<PositionOnBoard> possibleMoves = Set.of(PositionOnBoard.BE2, PositionOnBoard.BE3);
//        Map<String, String> mockBoard = new HashMap<>();
//        mockBoard.put("Be1", "BP"); // Blue Pawn at BE1
//
//        when(boardService.isPieceOwnedByCurrentPlayer(expectedPositionOnBoard)).thenReturn(true);
//        when(boardService.calculatePossibleMoves(expectedPositionOnBoard)).thenReturn(possibleMoves);
//        when(boardService.convertBoardToWebView()).thenReturn(mockBoard);
//
//        // Act
//        GameState result = GameServiceImpl.processClickEvent("be1");
//
//        // Assert
//        assertNotNull(result);
//        assertNotNull(result.getHighlightedPolygons());
//        assertTrue(result.getHighlightedPolygons().contains("Be2"));
//        assertTrue(result.getHighlightedPolygons().contains("Be3"));
//    }
//
//    @Test
//    void onClick_InvalidMove_ReturnsUnchangedState() throws InvalidPositionException {
//        // Arrange
//        PositionOnBoard expectedPositionOnBoard = PositionOnBoard.BE1;
//        lenient().when(boardService.isPieceOwnedByCurrentPlayer(expectedPositionOnBoard)).thenReturn(true);
//        lenient().when(boardService.calculatePossibleMoves(expectedPositionOnBoard)).thenReturn(Collections.emptySet());
//        lenient().when(boardService.convertBoardToWebView()).thenReturn(new HashMap<>());
//
//        // Act
//        GameState result = GameServiceImpl.processClickEvent("be1");
//
//        // Assert
//        assertNotNull(result);
//        assertTrue(result.getHighlightedPolygons().isEmpty());
//    }
//
//    @Test
//    void onClick_InvalidMoveException_HandlesError() throws InvalidMoveException, InvalidPositionException {
//        // Arrange
//        PositionOnBoard startPos = PositionOnBoard.BE1;
//        PositionOnBoard endPos = PositionOnBoard.BE2;
//
//        // Setup first click
//        lenient().when(boardService.isPieceOwnedByCurrentPlayer(startPos)).thenReturn(true);
//        lenient().when(boardService.calculatePossibleMoves(startPos)).thenReturn(Set.of(endPos));
//        lenient().when(boardService.convertBoardToWebView()).thenReturn(new HashMap<>());
//        lenient().doThrow(new InvalidMoveException("Invalid executeMove")).when(boardService).executeMove(startPos, endPos);
//
//        // First click
//        GameServiceImpl.processClickEvent("be1");
//
//        // Act - Second click
//        GameState result = GameServiceImpl.processClickEvent("be2");
//
//        // Assert
//        assertNotNull(result);
//        assertTrue(result.getHighlightedPolygons().isEmpty());
//    }
//
//    @Test
//    void getTurn_ReturnsCurrentTurn() {
//        // Arrange
//        when(boardService.currentTurn()).thenReturn(Colour.BLUE);
//
//        // Act
//        Colour result = GameServiceImpl.currentTurn();
//
//        // Assert
//        assertEquals(Colour.BLUE, result);
//    }
//
//    @Test
//    void onClick_NoPieceSelected_NoHighlights() throws InvalidPositionException {
//        // Arrange
//        PositionOnBoard expectedPositionOnBoard = PositionOnBoard.BE1;
//        lenient().when(boardService.isPieceOwnedByCurrentPlayer(expectedPositionOnBoard)).thenReturn(false);
//        lenient().when(boardService.convertBoardToWebView()).thenReturn(new HashMap<>());
//
//        // Act
//        GameState result = GameServiceImpl.processClickEvent("be1");
//
//        // Assert
//        assertTrue(result.getHighlightedPolygons().isEmpty());
//    }
//
//    // Integration tests with real BoardServiceImpl
//    @Test
//    void integrationTest_FullGameFlow() {
//        // Create a real GameServiceImpl with real dependencies
//        BoardServiceImpl realBoardService = new BoardServiceImpl();
//        GameServiceImpl realGameService = new GameServiceImpl(realBoardService);
//
//        // Initial state checks
//        Map<String, String> initialBoard = realGameService.retrieveBoardState();
//        assertNotNull(initialBoard);
//        assertFalse(initialBoard.isEmpty());
//        assertTrue(initialBoard.containsKey("Be2"), "Initial board should have a piece at Be2");
//        assertTrue(initialBoard.get("Be2").endsWith("P"), "Be2 should contain a pawn");
//
//        // Simulate a full executeMove
//        // 1. Select a piece (e.g., a pawn)
//        GameState firstClick = realGameService.processClickEvent("be2"); // Blue pawn initial position
//        assertNotNull(firstClick);
//        assertNotNull(firstClick.getHighlightedPolygons(), "Highlighted polygons should not be null");
//
//        // The pawn should be able to executeMove forward one or two squares from its starting position
//        boolean canMoveForward = firstClick.getHighlightedPolygons().contains("Be3") ||
//                                firstClick.getHighlightedPolygons().contains("Be4");
//        assertTrue(canMoveForward, "Pawn should be able to executeMove forward");
//
//        // 2. Move the piece
//        GameState secondClick = realGameService.processClickEvent("be3"); // Move pawn forward
//        assertNotNull(secondClick);
//
//        // Verify turn changed
//        assertNotEquals(Colour.BLUE, realGameService.currentTurn()); // Turn should have changed
//
//        // Verify board state updated
//        Map<String, String> updatedBoard = realGameService.retrieveBoardState();
//        assertNotNull(updatedBoard);
//        assertNotEquals(initialBoard, updatedBoard);
//
//        // Verify the pawn has moved
//        assertFalse(updatedBoard.containsKey("Be2"), "Pawn should no longer be at Be2");
//        assertTrue(updatedBoard.containsKey("Be3"), "Pawn should now be at Be3");
//        String pawnPosition = updatedBoard.get("Be3");
//        assertNotNull(pawnPosition, "Pawn should be at Be3");
//        assertTrue(pawnPosition.endsWith("P"), "PositionOnBoard should contain a pawn");
//    }
//
//    @Test
//    void integrationTest_CompleteGame() {
//        // Create a real GameServiceImpl with real dependencies
//        BoardServiceImpl realBoardService = new BoardServiceImpl();
//        GameServiceImpl realGameService = new GameServiceImpl(realBoardService);
//
//        // Play a sequence of valid moves
//        // Blue's turn
//        GameState state = realGameService.processClickEvent("be2"); // Select pawn
//        assertNotNull(state);
//        state = realGameService.processClickEvent("be3"); // Move pawn
//        assertNotNull(state);
//
//        // Green's turn
//        state = realGameService.processClickEvent("ge2"); // Select pawn
//        assertNotNull(state);
//        state = realGameService.processClickEvent("ge3"); // Move pawn
//        assertNotNull(state);
//
//        // Red's turn
//        state = realGameService.processClickEvent("re2"); // Select pawn
//        assertNotNull(state);
//        state = realGameService.processClickEvent("re3"); // Move pawn
//        assertNotNull(state);
//
//        // Verify game state
//        assertNotNull(state);
//        assertNotNull(state.retrieveBoardState());
//        assertFalse(state.checkIfGameOver());
//    }
//}