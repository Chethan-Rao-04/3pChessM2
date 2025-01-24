package com.ccd.chess.utility;
import java.model.ChessPiece;
import java.model.Bishop;
import java.model.Jester;
import java.model.King;
import java.model.Knight;
import java.model.Pawn;
import java.model.Queen;
import java.model.Rook;
import java.model.Wall;

import common.Colour;

/**
 * PieceFactory - helper class to create objects chess pieces
 **/
public class PieceFactory {

    /**
     * createPiece - based on the type and colour, creates a chess piece
     * @param colour - piece colour
     * @param type - piece name e.g. king
     * @return ChessPiece
     **/
    public static ChessPiece createPiece(String type, Colour colour) {
        switch (type.toLowerCase()) {
            case "bishop":
                return new Bishop(colour);
            case "queen":
                return new Queen(colour);
            case "king":
                return new King(colour);
            case "knight":
                return new Knight(colour);
            case "rook":
                return new Rook(colour);
            case "pawn":
                return new Pawn(colour);
            case "jester":
                return new Jester(colour);
            case "wall":
                return new Wall(colour);
            default:
                throw new IllegalArgumentException("Invalid chess piece type: " + type);
        }
    }
}
