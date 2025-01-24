package com.ccd.chess.utility;
import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.ChessPiece;
import com.ccd.chess.entity.Bishop;
import com.ccd.chess.entity.NewPiece1;
import com.ccd.chess.entity.King;
import com.ccd.chess.entity.Knight;
import com.ccd.chess.entity.Pawn;
import com.ccd.chess.entity.Queen;
import com.ccd.chess.entity.Rook;
import com.ccd.chess.entity.NewPiece2;



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
            case "NewPiece1":
                return new NewPiece1(colour);
            case "NewPiece2":
                return new NewPiece2(colour);
            default:
                throw new IllegalArgumentException("Invalid chess piece type: " + type);
        }
    }
}
