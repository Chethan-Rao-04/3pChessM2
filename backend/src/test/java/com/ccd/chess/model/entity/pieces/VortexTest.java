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
     * Tests if the vortex can move in all directions by checking if it has valid moves from a position.
     */
    @Test
    void setupDirections_vortexCanMoveInAllDirections_True() {
        ChessPiece vortex = new Vortex(Colour.GREEN);
        Position startPos = BE2;
        boardMap.clear();
        boardMap.put(startPos, vortex);
        Set<Position> moves = vortex.getHighlightPolygons(boardMap, startPos);
        assertFalse(moves.isEmpty());
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
