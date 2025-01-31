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
 * This class contains unit tests for the Hawk class.
 */
class HawkTest {

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
     * Tests if the hawk can move in its special pattern by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_hawkCanMoveInSpecialPattern_True() {
        ChessPiece hawk = new Hawk(Colour.GREEN);
        Position startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, hawk);
        Set<Position> moves = hawk.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isLegalMove method when hawk moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the hawk
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_hawkMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        Position hawkPosition = BE2;
        ChessPiece hawk = new Hawk(colour);
        boardMap.put(hawkPosition, hawk);
        Set<Position> actualHawkMoves = hawk.getMovablePositions(boardMap, hawkPosition);
        // Test two-step move
        assertTrue(actualHawkMoves.contains(BE4));
    }

    /**
     * Parameterized test for isLegalMove method when hawk takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_hawkTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece hawk = new Hawk(piece.getColour());
        boardMap.put(BE2, hawk);
        boardMap.put(BE4, piece);
        Set<Position> actualHawkMoves = hawk.getMovablePositions(boardMap, BE2);
        assertFalse(actualHawkMoves.contains(BE4));
    }

    /**
     * Parameterized test for isLegalMove method when hawk takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_hawkTakesDifferentColourPiece_True(ChessPiece piece) {
        ChessPiece hawk = new Hawk(piece.getColour().next());
        boardMap.put(BE2, hawk);
        boardMap.put(BE4, piece);
        Set<Position> actualHawkMoves = hawk.getMovablePositions(boardMap, BE2);
        assertTrue(actualHawkMoves.contains(BE4));
    }

    /**
     * Tests that hawk cannot move if the intermediate square is blocked
     */
    @Test
    void isLegalMove_hawkBlockedByIntermediateSquare_False() {
        boardMap.clear();
        Position startPosition = BE2;
        ChessPiece hawk = new Hawk(Colour.BLUE);
        ChessPiece blocker = new Pawn(Colour.GREEN);
        boardMap.put(startPosition, hawk);
        boardMap.put(BE3, blocker); // Place blocking piece
        Set<Position> moves = hawk.getMovablePositions(boardMap, startPosition);
        // Should not be able to move to BE4 since BE3 is blocked
        assertFalse(moves.contains(BE4));
    }

    /**
     * Parameterized test for getMovablePositions method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the hawk
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getMovablePositions_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        Position startPosition = BE2;
        ChessPiece hawk = new Hawk(colour);
        boardMap.put(startPosition, hawk);
        // Hawk should be able to move two steps in each direction from BE2
        Set<Position> expectedHawkMoves = ImmutableSet.of(
            BE4,       // Forward two steps
            BG2, BC2   // Horizontal two steps
        );
        Set<Position> actualHawkMoves = hawk.getMovablePositions(boardMap, startPosition);
        assertEquals(expectedHawkMoves, actualHawkMoves);
    }

    /**
     * Tests that hawk can move across board sections properly
     */
    @Test
    void getMovablePositions_hawkMovesAcrossBoardSections_True() {
        boardMap.clear();
        Position startPosition = BE4; // Edge of blue section
        ChessPiece hawk = new Hawk(Colour.BLUE);
        boardMap.put(startPosition, hawk);
        Set<Position> moves = hawk.getMovablePositions(boardMap, startPosition);
        // Should be able to move to green section
        assertTrue(moves.contains(GE1));
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for hawk initialization.
     * BH: blue Hawk
     * GH: green Hawk
     * RH: red Hawk
     *
     * @param colour Colour of the hawk
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initHawkAllColours_correctStringFormat(Colour colour) {
        ChessPiece hawk = new Hawk(colour);
        String expectedFormat = colour.toString() + "H";
        assertEquals(expectedFormat, hawk.toString());
    }
} 