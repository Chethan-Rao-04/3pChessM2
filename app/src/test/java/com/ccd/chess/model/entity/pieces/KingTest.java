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
 * This class contains unit tests for the King class.
 */
class KingTest {

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
     * Parameterized test to check if the king is present in its initial positionOnBoard,
     * expecting true.
     *
     * @param positionOnBoard Initial positionOnBoard of the king
     */
    @ParameterizedTest
    @EnumSource(value = PositionOnBoard.class, names = {"BE1", "RE1", "GE1"})
    void check_kingPresentInInitialPosition_True(PositionOnBoard positionOnBoard) {
        ChessPiece piece = boardMap.get(positionOnBoard);
        assertInstanceOf(King.class, piece);
    }

    /**
     * Parameterized test to check if the king is not present in its initial positionOnBoard,
     * expecting false.
     *
     * @param positionOnBoard Initial positionOnBoard of the king
     */
    @ParameterizedTest
    @EnumSource(value = PositionOnBoard.class, names = {"BD2", "RD2", "GD2"})
    void check_kingPresentInInitialPosition_False(PositionOnBoard positionOnBoard) {
        ChessPiece piece = boardMap.get(positionOnBoard);
        assertFalse(piece instanceof King);
    }

    /**
     * Parameterized test for isAllowedMove method when king moves to an empty square,
     * expecting true.
     *
     * @param colour Colour of the king
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_kingMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        PositionOnBoard kingPositionOnBoard = BE2;
        ChessPiece king = new King(colour);
        boardMap.put(kingPositionOnBoard, king);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, kingPositionOnBoard);
        assertTrue(actualKingMoves.contains(BE3));
    }

    /**
     * Parameterized test for isAllowedMove method when king takes a piece of its own color,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_kingTakesItsColourPiece_False(ChessPiece piece) {
        ChessPiece king = new King(piece.getColour());
        PositionOnBoard startPositionOnBoard = BE4;
        PositionOnBoard endPositionOnBoard = BD3;
        boardMap.put(startPositionOnBoard, king);
        boardMap.put(endPositionOnBoard, piece);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, startPositionOnBoard);
        assertFalse(actualKingMoves.contains(endPositionOnBoard));
    }

    /**
     * Parameterized test for isAllowedMove method when king takes a piece of a different color,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_kingTakesDifferentColourPiece_True(ChessPiece piece) {
        ChessPiece king = new King(piece.getColour().next());
        PositionOnBoard startPositionOnBoard = BE4;
        PositionOnBoard endPositionOnBoard = BD3;
        boardMap.put(startPositionOnBoard, king);
        boardMap.put(endPositionOnBoard, piece);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, startPositionOnBoard);
        assertTrue(actualKingMoves.contains(endPositionOnBoard));
    }

    /**
     * Parameterized test for getMovablePositions method,
     * expecting valid polygons to be present in the list.
     *
     * @param colour Colour of the king
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void getMovablePositions_validPolygons_presentInPolygonList(Colour colour) {
        boardMap.clear();
        PositionOnBoard startPositionOnBoard = BE4;
        ChessPiece king = new King(colour);
        boardMap.put(startPositionOnBoard, king);
        Set<PositionOnBoard> expectedKingMoves = ImmutableSet.of(GE4, BD3, RC4, BF3, BD4, BE3, RE4, BF4, RD4);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, startPositionOnBoard);
        assertEquals(expectedKingMoves, actualKingMoves);
    }

    /**
     * Test for short castle legal move, expecting true.
     */
    @Test
    void isLegalMove_shortCastle_True() {
        boardMap.clear();
        PositionOnBoard kingPositionOnBoard = RE1;
        PositionOnBoard rookPositionOnBoard = RH1;
        ChessPiece king = new King(kingPositionOnBoard.getColour());
        ChessPiece rook = new Rook(rookPositionOnBoard.getColour());
        boardMap.put(kingPositionOnBoard, king);
        boardMap.put(rookPositionOnBoard, rook);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, kingPositionOnBoard);
        assertTrue(actualKingMoves.contains(RG1));
    }

    /**
     * Test for short castle legal move when the square is occupied, expecting false.
     */
    @Test
    void isLegalMove_shortCastleOccupiedSquare_False() {
        boardMap.clear();
        PositionOnBoard kingPositionOnBoard = RE1;
        PositionOnBoard rookPositionOnBoard = RH1;
        PositionOnBoard knightPositionOnBoard = RG1;
        ChessPiece king = new King(kingPositionOnBoard.getColour());
        ChessPiece rook = new Rook(rookPositionOnBoard.getColour());
        ChessPiece knight = new Knight(knightPositionOnBoard.getColour());
        boardMap.put(kingPositionOnBoard, king);
        boardMap.put(rookPositionOnBoard, rook);
        boardMap.put(knightPositionOnBoard, knight);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, kingPositionOnBoard);
        assertFalse(actualKingMoves.contains(RG1));
    }

    /**
     * Test for long castle legal move, expecting true.
     */
    @Test
    void isLegalMove_longCastle_True() {
        boardMap.clear();
        PositionOnBoard kingPositionOnBoard = RE1;
        PositionOnBoard rookPositionOnBoard = RA1;
        ChessPiece king = new King(kingPositionOnBoard.getColour());
        ChessPiece rook = new Rook(rookPositionOnBoard.getColour());
        boardMap.put(kingPositionOnBoard, king);
        boardMap.put(rookPositionOnBoard, rook);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, kingPositionOnBoard);
        assertTrue(actualKingMoves.contains(RC1));
    }

    /**
     * Test for long castle legal move when the square is occupied, expecting false.
     */
    @Test
    void isLegalMove_longCastleOccupiedSquare_False() {
        boardMap.clear();
        PositionOnBoard kingPositionOnBoard = RE1;
        PositionOnBoard rookPositionOnBoard = RA1;
        PositionOnBoard knightPositionOnBoard = RC1;
        ChessPiece king = new King(kingPositionOnBoard.getColour());
        ChessPiece rook = new Rook(rookPositionOnBoard.getColour());
        ChessPiece knight = new Knight(knightPositionOnBoard.getColour());
        boardMap.put(kingPositionOnBoard, king);
        boardMap.put(rookPositionOnBoard, rook);
        boardMap.put(knightPositionOnBoard, knight);
        Set<PositionOnBoard> actualKingMoves = king.getMovablePositions(boardMap, kingPositionOnBoard);
        assertFalse(actualKingMoves.contains(RC1));
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for king initialization.
     * BK: blue King
     * GK: green King
     * RK: red King
     *
     * @param colour Colour of the king
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initKingAllColours_correctStringFormat(Colour colour) {
        ChessPiece king = new King(colour);
        String expectedFormat = colour.toString() + "K";
        assertEquals(expectedFormat, king.toString());
    }
}