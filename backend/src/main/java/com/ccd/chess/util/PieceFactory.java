package com.ccd.chess.util;
import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.pieces.*;


/**
 * PieceFactory - helper class to create objects chess pieces
 **/
public class PieceFactory {

    /**
     * createPiece - based on the type and colour, creates a chess piece
     * @param colour colour
     * @param type - piece name e.g. king
     * @return CheChessPiece
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
                case "hawk":
                    return new Hawk(colour);
                case "vortex":
                    return new Vortex(colour);
                default:
                    throw new IllegalArgumentException("Invalid chess piece type: " + type);
            }
        }
    }
