package com.ccd.chess.model.entity.pieces;

import com.google.common.collect.ImmutableSet;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.ccd.chess.test.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;

import static com.ccd.chess.model.entity.enums.Position.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Queen class.
 */
class QueenTest {

    private BoardServiceImpl board;
    private Map<Position, ChessPiece> boardMap;

    /**
     * Initializes a new Board instance before each test.
     */
    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new BoardServiceImpl();
        boardMap = board.getBoardMap();
    }

    /**
     * Tests if the queen can move in all directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_queenCanMoveInAllDirections_True() {
        ChessPiece queen = new Queen(Colour.GREEN);
        Position startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, queen);
        Set<Position> moves = queen.getHighlightPolygons(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isLegalMove method when queen moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the queen
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_queenMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        Position queenPosition = BE2;
        ChessPiece queen = new Queen(colour);
        boardMap.put(queenPosition, queen);
        Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, queenPosition);
        // Test diagonal move
        assertTrue(actualQueenMoves.contains(BG4));
        // Test straight move
        assertTrue(actualQueenMoves.contains(BE4));
    }

    /**
     * Parameterized test for isLegalMove method when queen takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_queenTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece queen = new Queen(piece.getColour());
        boardMap.put(BE2, queen);
        boardMap.put(BG4, piece);
        Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, BE2);
        assertFalse(actualQueenMoves.contains(BG4));
    }

    /**
     * Parameterized test for isLegalMove method when queen takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_queenTakesDifferentColourPiece_True(ChessPiece piece) {
        ChessPiece queen = new Queen(piece.getColour().next());
        boardMap.put(BE2, queen);
        boardMap.put(BG4, piece);
        Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, BE2);
        assertTrue(actualQueenMoves.contains(BG4));
    }

    /**
     * Parameterized test for getHighlightPolygons method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the queen
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        Position startPosition = BE2;
        ChessPiece queen = new Queen(colour);
        boardMap.put(startPosition, queen);
        // Queen should be able to move to all these positions from BE2
        Set<Position> expectedQueenMoves = ImmutableSet.of(
            // Diagonal moves
            BG4, BF3, BD1, BC2,  // Forward diagonals
            BG2, BF1, BD3, BC4,  // Backward diagonals
            // Straight moves
            BE3, BE4, BE1,       // Vertical moves
            BF2, BG2, BD2, BC2   // Horizontal moves
        );
        Set<Position> actualQueenMoves = queen.getHighlightPolygons(boardMap, startPosition);
        assertEquals(expectedQueenMoves, actualQueenMoves);
    }

    /**
     * Tests that queen can move across board sections properly
     */
    @Test
    void getHighlightPolygons_queenMovesAcrossBoardSections_True() {
        boardMap.clear();
        Position startPosition = BE4; // Edge of blue section
        ChessPiece queen = new Queen(Colour.BLUE);
        boardMap.put(startPosition, queen);
        Set<Position> moves = queen.getHighlightPolygons(boardMap, startPosition);
        // Should be able to move to green section
        assertTrue(moves.contains(GE1));
        // Should be able to move diagonally to red section
        assertTrue(moves.contains(RF4));
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