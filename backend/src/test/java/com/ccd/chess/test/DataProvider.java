package com.ccd.chess.test;

import com.ccd.chess.model.entity.pieces.Bishop;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.King;
import com.ccd.chess.model.entity.pieces.Knight;
import com.ccd.chess.model.entity.pieces.Pawn;
import com.ccd.chess.model.entity.pieces.Queen;
import com.ccd.chess.model.entity.pieces.Rook;
import com.ccd.chess.model.entity.enums.Colour;

import java.util.stream.Stream;

/**
 * Provides test data for parameterized tests.
 */
public class DataProvider {

    /**
     * Provides a stream of chess pieces for testing.
     * Returns pieces of all colors (BLUE, GREEN, RED) for comprehensive testing.
     * @return Stream of chess pieces
     */
    public static Stream<ChessPiece> pieceProvider() {
        return Stream.of(
            // Blue pieces
            new Pawn(Colour.BLUE),
            new Rook(Colour.BLUE),
            new Knight(Colour.BLUE),
            new Bishop(Colour.BLUE),
            new Queen(Colour.BLUE),
            new King(Colour.BLUE),
            // Green pieces
            new Pawn(Colour.GREEN),
            new Rook(Colour.GREEN),
            new Knight(Colour.GREEN),
            new Bishop(Colour.GREEN),
            new Queen(Colour.GREEN),
            new King(Colour.GREEN),
            // Red pieces
            new Pawn(Colour.RED),
            new Rook(Colour.RED),
            new Knight(Colour.RED),
            new Bishop(Colour.RED),
            new Queen(Colour.RED),
            new King(Colour.RED)
        );
    }
} 