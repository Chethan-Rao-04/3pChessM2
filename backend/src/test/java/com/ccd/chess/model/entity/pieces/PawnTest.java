package com.ccd.chess.model.entity.pieces;

import com.google.common.collect.ImmutableSet;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Pawn;
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
 * Test class for the {@link Pawn} class.
 * Contains various tests to verify the behavior of the Pawn piece in the game.
 */
class PawnTest {

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
     * Tests the isLegalMove method when a pawn moves forward to an empty square,
     * expecting true.
     */
    @Test
    void isLegalMove_pawnMoveForwardToEmptySquare_True() {
        ChessPiece pawn = new Pawn(Colour.BLUE);
        boardMap.put(BE4, pawn);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, BE4);
        assertTrue(actualPawnMoves.contains(RD4));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn is absent from its starting position,
     * expecting false.
     *
     * @param colour Colour of the pawn
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void isLegalMove_pawnAbsentFromStartPosition_False(Colour colour) {
        ChessPiece pawn = new Pawn(colour);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, BE4);
        assertFalse(actualPawnMoves.contains(BD3));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn moves forward to take an opponent's piece,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_pawnMoveForwardToTakeOpponentPiece_False(ChessPiece piece) {
        ChessPiece pawn = new Pawn(Colour.BLUE);
        Position startPosition = BE4;
        Position endPosition = RD4;

        boardMap.put(startPosition, pawn);
        boardMap.put(endPosition, piece);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, startPosition);
        assertFalse(actualPawnMoves.contains(endPosition));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn moves diagonally to take an opponent's piece,
     * expecting true.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_pawnMoveDiagonalToTakeOpponentPiece_True(ChessPiece piece) {
        if(piece.getColour() == Colour.BLUE) return;

        ChessPiece pawn = new Pawn(Colour.BLUE);
        Position startPosition = BE4;
        Position endPosition = RC4;

        boardMap.put(startPosition, pawn);
        boardMap.put(endPosition, piece);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, startPosition);
        assertTrue(actualPawnMoves.contains(endPosition));
    }

    /**
     * Parameterized test for isLegalMove method
     * when a pawn takes a piece of its own colour,
     * expecting false.
     *
     * @param piece Piece to be placed on the board
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void isLegalMove_pawnTakesItsColourPiece_False(ChessPiece piece) {
        if(piece.getColour() != Colour.BLUE) return;

        ChessPiece pawn = new Pawn(Colour.BLUE);
        Position startPosition = BE4;
        Position endPosition = RC4;

        boardMap.put(startPosition, pawn);
        boardMap.put(endPosition, piece);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, startPosition);
        assertFalse(actualPawnMoves.contains(endPosition));
    }

    /**
     * Tests the getMovablePositions method for a pawn in its initial position,
     * expecting valid polygons to be present in the list.
     */
    @Test
    void getMovablePositions_pawnInitialPosition_presentInPolygonList() {
        Position startPosition = BB2;
        ChessPiece pawn = new Pawn(startPosition.getColour());
        Set<Position> expectedPawnMoves = ImmutableSet.of(BB3, BB4);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, startPosition);
        assertEquals(expectedPawnMoves, actualPawnMoves);
    }

    /**
     * Tests the getMovablePositions method for a pawn that has already moved,
     * expecting valid polygons to be present in the list.
     */
    @Test
    void getMovablePositions_pawnAlreadyMoved_presentInPolygonList() {
        Position startPosition = BE4;
        ChessPiece pawn = new Pawn(startPosition.getColour());
        Set<Position> expectedPawnMoves = ImmutableSet.of(RD4);
        Set<Position> actualPawnMoves = pawn.getMovablePositions(boardMap, startPosition);
        assertEquals(expectedPawnMoves, actualPawnMoves);
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for pawn initialization.
     * BP: blue Pawn
     * GP: green Pawn
     * RP: red Pawn
     *
     * @param colour Colour of the pawn
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initPawnAllColours_correctStringFormat(Colour colour) {
        ChessPiece pawn = new Pawn(colour);
        String expectedFormat = colour.toString() + "P";
        assertEquals(expectedFormat, pawn.toString());
    }
}