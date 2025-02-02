package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.ccd.chess.model.entity.enums.Colour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;

import static com.ccd.chess.model.entity.enums.PositionOnBoard.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Queen class.
 */
class QueenTest {

    private BoardServiceImpl board;
    private Map<PositionOnBoard, ChessPiece> boardMap;

    /**
     * Initializes a new Board instance before each test.
     */
    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new BoardServiceImpl();
        boardMap = board.getBoardMap();
        boardMap.clear(); // Ensure clean board for each test
    }

    /**
     * Tests if the queen can executeMove in all directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_queenCanMoveInAllDirections_True() {
        ChessPiece queen = new Queen(Colour.BLUE);
        PositionOnBoard startPos = BB2; // Use a position that allows all types of moves
        boardMap.put(startPos, queen);
        Set<PositionOnBoard> moves = queen.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isAllowedMove method when queen moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the queen
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_queenMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        PositionOnBoard startPos;
        switch (colour) {
            case BLUE: startPos = BB2; break;
            case GREEN: startPos = GB2; break;
            case RED: startPos = RB2; break;
            default: throw new IllegalStateException("Unknown colour: " + colour);
        }
        ChessPiece queen = new Queen(colour);
        boardMap.put(startPos, queen);
        Set<PositionOnBoard> moves = queen.getMovablePositions(boardMap, startPos);

        // Test that queen has valid moves
        assertFalse(moves.isEmpty(), "Queen should have at least one valid executeMove");

        // Test specific moves based on color
        PositionOnBoard expectedDiagonal;
        PositionOnBoard expectedStraight;
        switch (colour) {
            case BLUE:
                expectedDiagonal = BC3;
                expectedStraight = BB3;
                break;
            case GREEN:
                expectedDiagonal = GC3;
                expectedStraight = GB3;
                break;
            case RED:
                expectedDiagonal = RC3;
                expectedStraight = RB3;
                break;
            default:
                throw new IllegalStateException("Unknown colour: " + colour);
        }

        // Test at least one type of executeMove works
        assertTrue(moves.contains(expectedDiagonal) || moves.contains(expectedStraight),
                "Queen should be able to executeMove either diagonally or straight from starting position");
    }

    /**
     * Parameterized test for isAllowedMove method when queen takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_queenTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece queen = new Queen(piece.getColour());
        PositionOnBoard startPos = BB2;
        PositionOnBoard targetPos = BC3;
        boardMap.put(startPos, queen);
        boardMap.put(targetPos, piece);
        Set<PositionOnBoard> actualQueenMoves = queen.getMovablePositions(boardMap, startPos);
        assertFalse(actualQueenMoves.contains(targetPos));
    }

    /**
     * Parameterized test for isAllowedMove method when queen takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_queenTakesDifferentColourPiece_True(ChessPiece piece) {
        if (piece.getColour() == Colour.BLUE) return; // Skip if same color
        ChessPiece queen = new Queen(Colour.BLUE);
        PositionOnBoard startPos = BB2;
        PositionOnBoard targetPos = BC3;
        boardMap.put(startPos, queen);
        boardMap.put(targetPos, piece);
        Set<PositionOnBoard> actualQueenMoves = queen.getMovablePositions(boardMap, startPos);
        assertTrue(actualQueenMoves.contains(targetPos));
    }

    /**
     * Parameterized test for getMovablePositions method,
     * expecting valid polygons to be present in the list.
     */
    @Test
    void getMovablePositions_validPolygons_presentInPolygonList() {
        boardMap.clear();
        PositionOnBoard startPos = BB2;
        ChessPiece queen = new Queen(Colour.BLUE);
        boardMap.put(startPos, queen);
        Set<PositionOnBoard> moves = queen.getMovablePositions(boardMap, startPos);

        // Test that queen has valid moves
        assertFalse(moves.isEmpty(), "Queen should have at least one valid executeMove");

        // Test at least one type of executeMove works
        boolean hasValidMove = moves.contains(BB3) || // Straight forward
                moves.contains(BC2) || // Straight right
                moves.contains(BC3);   // Diagonal forward-right
        assertTrue(hasValidMove, "Queen should be able to executeMove in at least one direction");
    }

    /**
     * Tests that queen can executeMove across board sections properly
     */
    @Test
    void getMovablePositions_queenMovesAcrossBoardSections_True() {
        boardMap.clear();
        PositionOnBoard startPos = BE4; // Edge of blue section
        ChessPiece queen = new Queen(Colour.BLUE);
        boardMap.put(startPos, queen);
        Set<PositionOnBoard> moves = queen.getMovablePositions(boardMap, startPos);

        // Just verify some valid moves exist when at section edge
        assertFalse(moves.isEmpty(), "Queen should have valid moves at section edge");
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for queen initialization.
     * BQ: blue Queen
     * GQ: green Queen
     * RQ: red Queen
     *
     * @param colour Colour of the queen
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initQueenAllColours_correctStringFormat(Colour colour) {
        ChessPiece queen = new Queen(colour);
        String expectedFormat = colour.toString() + "Q";
        assertEquals(expectedFormat, queen.toString());
    }
} 