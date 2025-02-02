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
 * This class contains unit tests for the Knight class.
 */
class KnightTest {

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
     * Tests if the knight can move in all directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_knightCanMoveInAllDirections_True() {
        ChessPiece knight = new Knight(Colour.BRONZE);
        PositionOnBoard startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, knight);
        Set<PositionOnBoard> moves = knight.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isAllowedMove method when knight moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the knight
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_knightMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        PositionOnBoard knightPositionOnBoard = BE2;
        ChessPiece knight = new Knight(colour);
        boardMap.put(knightPositionOnBoard, knight);
        Set<PositionOnBoard> actualKnightMoves = knight.getMovablePositions(boardMap, knightPositionOnBoard);
        assertTrue(actualKnightMoves.contains(BF4));
    }

    /**
     * Parameterized test for isAllowedMove method when knight takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_knightTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece knight = new Knight(piece.getColour());
        boardMap.put(BE4, knight);
        boardMap.put(BC3, piece);
        Set<PositionOnBoard> actualKnightMoves = knight.getMovablePositions(boardMap, BE4);
        assertFalse(actualKnightMoves.contains(BC3));
    }

    /**
     * Parameterized test for isAllowedMove method when knight takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_knightTakesDifferentColourPiece_True(ChessPiece piece) {
        ChessPiece knight = new Knight(piece.getColour().next());
        boardMap.put(BE4, knight);
        boardMap.put(BC3, piece);
        Set<PositionOnBoard> actualKnightMoves = knight.getMovablePositions(boardMap, BE4);
        assertTrue(actualKnightMoves.contains(BC3));
    }

    /**
     * Parameterized test for getMovablePositions method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the knight
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getMovablePositions_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        PositionOnBoard startPositionOnBoard = BE4;
        ChessPiece knight = new Knight(colour);
        boardMap.put(startPositionOnBoard, knight);
        Set<PositionOnBoard> expectedKnightMoves = ImmutableSet.of(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3);
        Set<PositionOnBoard> actualKnightMoves = knight.getMovablePositions(boardMap, startPositionOnBoard);
        assertEquals(expectedKnightMoves, actualKnightMoves);
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for knight initialization.
     * BN: blue Knight
     * GN: green Knight
     * RN: red Knight
     *
     * @param colour Colour of the knight
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKnightAllColours_correctStringFormat(Colour colour) {
        ChessPiece knight = new Knight(colour);
        String expectedFormat = colour.toString() + "N";
        assertEquals(expectedFormat, knight.toString());
    }
}