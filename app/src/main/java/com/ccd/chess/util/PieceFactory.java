package com.ccd.chess.util;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Bishop;
import com.ccd.chess.model.entity.pieces.Queen;
import com.ccd.chess.model.entity.pieces.King;
import com.ccd.chess.model.entity.pieces.Knight;
import com.ccd.chess.model.entity.pieces.Rook;
import com.ccd.chess.model.entity.pieces.Pawn;
import com.ccd.chess.model.entity.pieces.Hawk;
import com.ccd.chess.model.entity.pieces.Vortex;

/**
 * PieceFactory - Factory class for creating chess pieces
 * 
 * SOLID Principles Implementation:
 * 
 * Single Responsibility Principle (SRP):
 * ✅ Single responsibility:
 * - Creates chess piece instances
 * - Encapsulates piece creation logic
 * 
 * Open/Closed Principle (OCP):
 * ✅ Open for extension:
 * - New piece types can be added by extending switch case
 * - Existing piece creation remains unchanged
 * - Example: Hawk and Vortex pieces added easily
 * 
 * Liskov Substitution Principle (LSP):
 * ✅ Returns ChessPiece abstraction:
 * - All created pieces are valid ChessPiece subtypes
 * - Can be used anywhere ChessPiece is expected
 * 
 * Interface Segregation Principle (ISP):
 * ✅ Simple, focused interface:
 * - Single factory method
 * - Clear parameters and return type
 * 
 * Dependency Inversion Principle (DIP):
 * ✅ Depends on and returns abstractions:
 * - Works with ChessPiece interface
 * - Uses Colour enum
 * - Clients depend on abstractions, not concrete pieces
 */
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
