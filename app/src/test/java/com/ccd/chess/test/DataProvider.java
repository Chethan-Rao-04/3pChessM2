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
            // Silver pieces
            new Pawn(Colour.SILVER),
            new Rook(Colour.SILVER),
            new Knight(Colour.SILVER),
            new Bishop(Colour.SILVER),
            new Queen(Colour.SILVER),
            new King(Colour.SILVER),
            // Bronze pieces
            new Pawn(Colour.BRONZE),
            new Rook(Colour.BRONZE),
            new Knight(Colour.BRONZE),
            new Bishop(Colour.BRONZE),
            new Queen(Colour.BRONZE),
            new King(Colour.BRONZE),
            // Gold pieces
            new Pawn(Colour.GOLD),
            new Rook(Colour.GOLD),
            new Knight(Colour.GOLD),
            new Bishop(Colour.GOLD),
            new Queen(Colour.GOLD),
            new King(Colour.GOLD)
        );
    }
} 