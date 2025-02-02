package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;

import static com.ccd.chess.model.entity.enums.PositionOnBoard.BE2;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains unit tests for the Bishop class.
 */
class BishopTest {

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
     * Test that bishop can executeMove to an empty square.
     *
     * @param colour Colour of the bishop
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_bishopMovesToEmptySquare_True(Colour colour) {
        boardMap.clear();
        ChessPiece bishop = new Bishop(colour);
        PositionOnBoard startPos = BE2;
        boardMap.put(startPos, bishop);
        Set<PositionOnBoard> moves = bishop.getMovablePositions(boardMap, startPos);
        assertFalse(moves.isEmpty());
    }

    /**
     * Test that bishop cannot take a piece of the same colour.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_bishopTakesItsColourPiece_False(ChessPiece piece) {
        boardMap.clear();
        ChessPiece bishop = new Bishop(piece.getColour());
        PositionOnBoard startPos = BE2;
        boardMap.put(startPos, bishop);
        boardMap.put(PositionOnBoard.BF4, piece);
        Set<PositionOnBoard> moves = bishop.getMovablePositions(boardMap, startPos);
        assertFalse(moves.contains(PositionOnBoard.BE4));
    }

//    /**
//     * Test that bishop can take a piece of a different colour.
//     *
//     * @param piece Piece to be placed on the board
//     */
//    @ParameterizedTest
//    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
//    void isLegalMove_bishopTakesDifferentColourPiece_True(ChessPiece piece) {
//        boardMap.clear();
//        ChessPiece bishop = new Bishop(Colour.GREEN);
//        PositionOnBoard startPos = BE2;
//        boardMap.put(startPos, bishop);
//        boardMap.put(PositionOnBoard.BE4, piece);
//        Set<PositionOnBoard> moves = bishop.getMovablePositions(boardMap, startPos);
//        assertTrue(moves.contains(PositionOnBoard.BE4));
//    }
//
//    /**
//     * Test that bishop can executeMove across board sections properly.
//     */
//    @Test
//    void testBishopMovementAcrossBoardSections() {
//        boardMap.clear();
//        ChessPiece bishop = new Bishop(Colour.BLUE);
//        PositionOnBoard startPos = BE2;
//        boardMap.put(startPos, bishop);
//        Set<PositionOnBoard> moves = bishop.getMovablePositions(boardMap, startPos);
//        assertFalse(moves.isEmpty());
//    }
//
//    /**
//     * Parameterized test for toString method,
//     * expecting correct string format for bishop initialization.
//     * BB: blue Bishop
//     * GB: green Bishop
//     * RB: red Bishop
//     *
//     * @param colour Colour of the bishop
//     */
//    @ParameterizedTest
//    @EnumSource(Colour.class)
//    void toString_initBishopAllColours_correctStringFormat(Colour colour) {
//        ChessPiece bishop = new Bishop(colour);
//        String expectedFormat = colour.toString() + "B";
//        assertEquals(expectedFormat, bishop.toString());
//    }
}