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
 * Test class for the {@link Vortex} class.
 * Contains various tests to verify the behavior of the Vortex piece in the game.
 */
class VortexTest {

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
     * Tests if the vortex can executeMove diagonally and then left from those positions.
     */
    @Test
    void getMovablePositions_vortexMovesInValidPattern_True() {
        ChessPiece vortex = new Vortex(Colour.GREEN);
        PositionOnBoard startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, vortex);
        Set<PositionOnBoard> moves = vortex.getMovablePositions(boardMap, startPos);
        // Should include diagonal moves and left moves from those diagonals
        Set<PositionOnBoard> expectedMoves = ImmutableSet.of(
                // Diagonal moves
                BF1, BF3, BD1, BD3,
                // Left moves from diagonals
                BE1, BE3, BC1, BC3
        );
        assertEquals(expectedMoves, moves);
    }

    /**
     * Tests if the vortex can capture enemy pieces.
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void getMovablePositions_vortexCapturesEnemyPiece_True(ChessPiece piece) {
        if(piece.getColour() == Colour.GREEN) return;

        ChessPiece vortex = new Vortex(Colour.GREEN);
        PositionOnBoard startPos = BE2;
        PositionOnBoard capturePos = BF1;  // Diagonal position
        boardMap.clear();
        boardMap.put(startPos, vortex);
        boardMap.put(capturePos, piece);
        Set<PositionOnBoard> moves = vortex.getMovablePositions(boardMap, startPos);
        assertTrue(moves.contains(capturePos));
    }

    /**
     * Tests if the vortex cannot executeMove through friendly pieces.
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void getMovablePositions_vortexBlockedByFriendlyPiece_False(ChessPiece piece) {
        if(piece.getColour() != Colour.GREEN) return;

        ChessPiece vortex = new Vortex(Colour.GREEN);
        PositionOnBoard startPos = BE2;
        PositionOnBoard blockingPos = BF1;  // Diagonal position
        PositionOnBoard leftPos = BE1;      // Left executeMove from diagonal
        boardMap.clear();
        boardMap.put(startPos, vortex);
        boardMap.put(blockingPos, piece);
        Set<PositionOnBoard> moves = vortex.getMovablePositions(boardMap, startPos);
        assertFalse(moves.contains(blockingPos));
        assertFalse(moves.contains(leftPos));  // Should not be able to executeMove left from blocked diagonal
    }

    /**
     * Tests if the vortex can capture enemy pieces with left moves from diagonals.
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void getMovablePositions_vortexCapturesWithLeftMove_True(ChessPiece piece) {
        if(piece.getColour() == Colour.GREEN) return;

        ChessPiece vortex = new Vortex(Colour.GREEN);
        PositionOnBoard startPos = BE2;
        PositionOnBoard capturePos = BE1;  // Left executeMove from diagonal
        boardMap.clear();
        boardMap.put(startPos, vortex);
        boardMap.put(capturePos, piece);
        Set<PositionOnBoard> moves = vortex.getMovablePositions(boardMap, startPos);
        assertTrue(moves.contains(capturePos));
    }

    /**
     * Parameterized test for toString method,
     * expecting correct string format for vortex initialization.
     * BV: blue Vortex
     * GV: green Vortex
     * RV: red Vortex
     *
     * @param colour Colour of the vortex
     */
    @ParameterizedTest
    @EnumSource(Colour.class)
    void toString_initVortexAllColours_correctStringFormat(Colour colour) {
        ChessPiece vortex = new Vortex(colour);
        String expectedFormat = colour.toString() + "V";
        assertEquals(expectedFormat, vortex.toString());
    }
}