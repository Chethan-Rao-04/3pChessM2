package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.google.common.collect.ImmutableSet;
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
 * This class contains unit tests for the Rook class.
 */
class RookTest {

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
     * Tests if the rook can move in straight directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_rookCanMoveInStraightDirections_True() {
        ChessPiece rook = new Rook(Colour.BRONZE);
        PositionOnBoard startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, rook);
        Set<PositionOnBoard> moves = rook.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isAllowedMove method when rook moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the rook
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_rookMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        PositionOnBoard rookPositionOnBoard = BE2;
        ChessPiece rook = new Rook(colour);
        boardMap.put(rookPositionOnBoard, rook);
        Set<PositionOnBoard> actualRookMoves = rook.getMovablePositions(boardMap, rookPositionOnBoard);
        // Test straight move
        assertTrue(actualRookMoves.contains(BE4));
    }

    /**
     * Parameterized test for isAllowedMove method when rook takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_rookTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece rook = new Rook(piece.getColour());
        boardMap.put(BE2, rook);
        boardMap.put(BE4, piece);
        Set<PositionOnBoard> actualRookMoves = rook.getMovablePositions(boardMap, BE2);
        assertFalse(actualRookMoves.contains(BE4));
    }

    /**
     * Parameterized test for isAllowedMove method when rook takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_rookTakesDifferentColourPiece_True(ChessPiece piece) {
        ChessPiece rook = new Rook(piece.getColour().next());
        boardMap.put(BE2, rook);
        boardMap.put(BE4, piece);
        Set<PositionOnBoard> actualRookMoves = rook.getMovablePositions(boardMap, BE2);
        assertTrue(actualRookMoves.contains(BE4));
    }

    /**
     * Parameterized test for getMovablePositions method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the rook
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getMovablePositions_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        PositionOnBoard startPositionOnBoard = BE2;
        ChessPiece rook = new Rook(colour);
        boardMap.put(startPositionOnBoard, rook);
        // Rook should be able to move to all these straight positions from BE2
        Set<PositionOnBoard> expectedRookMoves = ImmutableSet.of(
                BE3, BE4, BE1, RD4, RD3, RD2 ,RD1, // Vertical moves
                BF2, BG2,BH2 , BD2, BC2 ,BB2, BA2 // Horizontal moves
        );
        Set<PositionOnBoard> actualRookMoves = rook.getMovablePositions(boardMap, startPositionOnBoard);
        assertEquals(expectedRookMoves, actualRookMoves);
    }

    /**
     * Tests that rook can move across board sections properly
     */
    @Test
    void getMovablePositions_rookMovesAcrossBoardSections_True() {
        boardMap.clear();
        PositionOnBoard startPositionOnBoard = BE4; // Edge of blue section
        ChessPiece rook = new Rook(Colour.SILVER);
        boardMap.put(startPositionOnBoard, rook);
        Set<PositionOnBoard> moves = rook.getMovablePositions(boardMap, startPositionOnBoard);
        // Should be able to move to bronze section
        assertTrue(moves.contains(RD1));
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for rook initialization.
     * BR: blue Rook
     * GR: green Rook
     * RR: red Rook
     *
     * @param colour Colour of the rook
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initRookAllColours_correctStringFormat(Colour colour) {
        ChessPiece rook = new Rook(colour);
        String expectedFormat = colour.toString() + "R";
        assertEquals(expectedFormat, rook.toString());
    }
} 