package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.service.impl.BoardServiceImpl;
import com.google.common.collect.ImmutableSet;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
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
 * This class contains unit tests for the Hawk class.
 */
class HawkTest {

    private BoardServiceImpl board;
    private Map<PositionOnBoard, ChessPiece> boardMap;

    /**
     * Initializes a new Board instance before each test.
     */
    @BeforeEach
    void initBeforeEachBoardTest() {
        board = new BoardServiceImpl();
        boardMap = board.getBoardMap();
    }

    /**
     * Tests if the hawk can executeMove in its special pattern by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_hawkCanMoveInSpecialPattern_True() {
        ChessPiece hawk = new Hawk(Colour.GREEN);
        PositionOnBoard startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, hawk);
        Set<PositionOnBoard> moves = hawk.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isAllowedMove method when hawk moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the hawk
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_hawkMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        PositionOnBoard hawkPositionOnBoard = BE2;
        ChessPiece hawk = new Hawk(colour);
        boardMap.put(hawkPositionOnBoard, hawk);
        Set<PositionOnBoard> actualHawkMoves = hawk.getMovablePositions(boardMap, hawkPositionOnBoard);
        // Test two-stepDirection executeMove
        assertTrue(actualHawkMoves.contains(BE4));
    }

    /**
     * Parameterized test for isAllowedMove method when hawk takes a piece of its own color,
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
        Set<PositionOnBoard> actualHawkMoves = hawk.getMovablePositions(boardMap, BE2);
        assertFalse(actualHawkMoves.contains(BE4));
    }

    /**
     * Parameterized test for isAllowedMove method when hawk takes a piece of a different color,
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
        Set<PositionOnBoard> actualHawkMoves = hawk.getMovablePositions(boardMap, BE2);
        assertTrue(actualHawkMoves.contains(BE4));
    }

    /**
     * Tests that hawk cannot executeMove if the intermediate square is blocked
     */
    @Test
    void isLegalMove_hawkBlockedByIntermediateSquare_False() {
        boardMap.clear();
        PositionOnBoard startPositionOnBoard = BE2;
        ChessPiece hawk = new Hawk(Colour.BLUE);
        ChessPiece blocker = new Pawn(Colour.GREEN);
        boardMap.put(startPositionOnBoard, hawk);
        boardMap.put(BE3, blocker); // Place blocking piece
        Set<PositionOnBoard> moves = hawk.getMovablePositions(boardMap, startPositionOnBoard);
        // Should not be able to executeMove to BE4 since BE3 is blocked
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
        PositionOnBoard startPositionOnBoard = BE2;
        ChessPiece hawk = new Hawk(colour);
        boardMap.put(startPositionOnBoard, hawk);
        // Hawk should be able to executeMove two steps in each direction from BE2
        Set<PositionOnBoard> expectedHawkMoves = ImmutableSet.of(
                BE4,       // Forward two steps
                BG2, BC2   // Horizontal two steps
        );
        Set<PositionOnBoard> actualHawkMoves = hawk.getMovablePositions(boardMap, startPositionOnBoard);
        assertEquals(expectedHawkMoves, actualHawkMoves);
    }

    /**
     * Tests that hawk can executeMove across board sections properly
     */
    @Test
    void getMovablePositions_hawkMovesAcrossBoardSections_True() {
        boardMap.clear();
        PositionOnBoard startPos = BE4; // Edge of blue section
        ChessPiece hawk = new Hawk(Colour.BLUE);
        boardMap.put(startPos, hawk);
        Set<PositionOnBoard> moves = hawk.getMovablePositions(boardMap, startPos);

        // Test that some moves exist
        assertFalse(moves.isEmpty(), "Hawk should have valid moves at section edge");

        // Test that at least one two-stepDirection executeMove works
        boolean hasValidMove = moves.contains(BE2) || // Two steps backward
                moves.contains(BG4) || // Two steps right
                moves.contains(BC4);   // Two steps left
        assertTrue(hasValidMove, "Hawk should be able to executeMove two steps in at least one direction");
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