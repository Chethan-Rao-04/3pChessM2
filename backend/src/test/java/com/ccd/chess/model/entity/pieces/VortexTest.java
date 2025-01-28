package com.ccd.chess.model.entity.pieces;

import com.google.common.collect.ImmutableSet;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Vortex;
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
 * Test class for the {@link Vortex} class.
 * Contains various tests to verify the behavior of the Vortex piece in the game.
 */
class VortexTest {

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
     * Tests if the vortex can move diagonally and then left from those positions.
     */
    @Test
    void getHighlightPolygons_vortexMovesInValidPattern_True() {
        ChessPiece vortex = new Vortex(Colour.GREEN);
        Position startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, vortex);
        Set<Position> moves = vortex.getHighlightPolygons(boardMap, startPos);
        // Should include diagonal moves and left moves from those diagonals
        Set<Position> expectedMoves = ImmutableSet.of(
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
    void getHighlightPolygons_vortexCapturesEnemyPiece_True(ChessPiece piece) {
        if(piece.getColour() == Colour.GREEN) return;

        ChessPiece vortex = new Vortex(Colour.GREEN);
        Position startPos = BE2;
        Position capturePos = BF1;  // Diagonal position
        boardMap.clear();
        boardMap.put(startPos, vortex);
        boardMap.put(capturePos, piece);
        Set<Position> moves = vortex.getHighlightPolygons(boardMap, startPos);
        assertTrue(moves.contains(capturePos));
    }

    /**
     * Tests if the vortex cannot move through friendly pieces.
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void getHighlightPolygons_vortexBlockedByFriendlyPiece_False(ChessPiece piece) {
        if(piece.getColour() != Colour.GREEN) return;

        ChessPiece vortex = new Vortex(Colour.GREEN);
        Position startPos = BE2;
        Position blockingPos = BF1;  // Diagonal position
        Position leftPos = BE1;      // Left move from diagonal
        boardMap.clear();
        boardMap.put(startPos, vortex);
        boardMap.put(blockingPos, piece);
        Set<Position> moves = vortex.getHighlightPolygons(boardMap, startPos);
        assertFalse(moves.contains(blockingPos));
        assertFalse(moves.contains(leftPos));  // Should not be able to move left from blocked diagonal
    }

    /**
     * Tests if the vortex can capture enemy pieces with left moves from diagonals.
     */
    @ParameterizedTest
    @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
    void getHighlightPolygons_vortexCapturesWithLeftMove_True(ChessPiece piece) {
        if(piece.getColour() == Colour.GREEN) return;

        ChessPiece vortex = new Vortex(Colour.GREEN);
        Position startPos = BE2;
        Position capturePos = BE1;  // Left move from diagonal
        boardMap.clear();
        boardMap.put(startPos, vortex);
        boardMap.put(capturePos, piece);
        Set<Position> moves = vortex.getHighlightPolygons(boardMap, startPos);
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
