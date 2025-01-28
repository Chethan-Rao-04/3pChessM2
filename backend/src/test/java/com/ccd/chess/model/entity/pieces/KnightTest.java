package com.ccd.chess.model.entity.pieces;

import com.google.common.collect.ImmutableSet;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Knight;
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
 * This class contains unit tests for the Knight class.
 */
class KnightTest {

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
     * Tests if the knight can move in all directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_knightCanMoveInAllDirections_True() {
        ChessPiece knight = new Knight(Colour.GREEN);
        Position startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, knight);
        Set<Position> moves = knight.getHighlightPolygons(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Parameterized test for isLegalMove method when knight moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the knight
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_knightMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        Position knightPosition = BE2;
        ChessPiece knight = new Knight(colour);
        boardMap.put(knightPosition, knight);
        Set<Position> actualKnightMoves = knight.getHighlightPolygons(boardMap, knightPosition);
        assertTrue(actualKnightMoves.contains(BF4));
    }

    /**
     * Parameterized test for isLegalMove method when knight takes a piece of its own color,
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
        Set<Position> actualKnightMoves = knight.getHighlightPolygons(boardMap, BE4);
        assertFalse(actualKnightMoves.contains(BC3));
    }

    /**
     * Parameterized test for isLegalMove method when knight takes a piece of a different color,
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
        Set<Position> actualKnightMoves = knight.getHighlightPolygons(boardMap, BE4);
        assertTrue(actualKnightMoves.contains(BC3));
    }

    /**
     * Parameterized test for getHighlightPolygons method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the knight
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getHighlightPolygons_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        Position startPosition = BE4;
        ChessPiece knight = new Knight(colour);
        boardMap.put(startPosition, knight);
        Set<Position> expectedKnightMoves = ImmutableSet.of(BG3, BF2, BD2, BC3, GF4, GE3, RB4, RC3, RF4, RE3);
        Set<Position> actualKnightMoves = knight.getHighlightPolygons(boardMap, startPosition);
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