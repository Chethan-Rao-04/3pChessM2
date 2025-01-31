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
 * This class contains unit tests for the Bishop class.
 */
class BishopTest {

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
     * Tests if the bishop can move in diagonal directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_bishopCanMoveInDiagonalDirections_True() {
        ChessPiece bishop = new Bishop(Colour.GREEN);
        Position startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, bishop);
        Set<Position> moves = bishop.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isLegalMove method when bishop moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the bishop
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_bishopMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        Position bishopPosition = BE2;
        ChessPiece bishop = new Bishop(colour);
        boardMap.put(bishopPosition, bishop);
        Set<Position> actualBishopMoves = bishop.getMovablePositions(boardMap, bishopPosition);
        // Test diagonal move
        assertTrue(actualBishopMoves.contains(BG4));
    }

    /**
     * Parameterized test for isLegalMove method when bishop takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_bishopTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece bishop = new Bishop(piece.getColour());
        boardMap.put(BE2, bishop);
        boardMap.put(BG4, piece);
        Set<Position> actualBishopMoves = bishop.getMovablePositions(boardMap, BE2);
        assertFalse(actualBishopMoves.contains(BG4));
    }

    /**
     * Parameterized test for isLegalMove method when bishop takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_bishopTakesDifferentColourPiece_True(ChessPiece piece) {
        ChessPiece bishop = new Bishop(piece.getColour().next());
        boardMap.put(BE2, bishop);
        boardMap.put(BG4, piece);
        Set<Position> actualBishopMoves = bishop.getMovablePositions(boardMap, BE2);
        assertTrue(actualBishopMoves.contains(BG4));
    }

    /**
     * Parameterized test for getMovablePositions method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the bishop
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getMovablePositions_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        Position startPosition = BE2;
        ChessPiece bishop = new Bishop(colour);
        boardMap.put(startPosition, bishop);
        // Bishop should be able to move to all these diagonal positions from BE2
        Set<Position> expectedBishopMoves = ImmutableSet.of(
            BG4, BF3, BD1,  // Forward diagonals
            BG2, BF1, BD3   // Backward diagonals
        );
        Set<Position> actualBishopMoves = bishop.getMovablePositions(boardMap, startPosition);
        assertEquals(expectedBishopMoves, actualBishopMoves);
    }

    /**
     * Tests that bishop can move across board sections properly
     */
    @Test
    void getMovablePositions_bishopMovesAcrossBoardSections_True() {
        boardMap.clear();
        Position startPosition = BE4; // Edge of blue section
        ChessPiece bishop = new Bishop(Colour.BLUE);
        boardMap.put(startPosition, bishop);
        Set<Position> moves = bishop.getMovablePositions(boardMap, startPosition);
        // Should be able to move diagonally to red section
        assertTrue(moves.contains(RF4));
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for bishop initialization.
     * BB: blue Bishop
     * GB: green Bishop
     * RB: red Bishop
     *
     * @param colour Colour of the bishop
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initBishopAllColours_correctStringFormat(Colour colour) {
        ChessPiece bishop = new Bishop(colour);
        String expectedFormat = colour.toString() + "B";
        assertEquals(expectedFormat, bishop.toString());
    }
} 