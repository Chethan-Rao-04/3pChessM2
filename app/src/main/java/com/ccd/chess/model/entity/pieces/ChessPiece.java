package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * ChessPiece - Abstract base class for all chess pieces
 * 
 * SOLID Principles Implementation:
 * 
 * Single Responsibility Principle (SRP):
 * ✅ Each piece class has single responsibility:
 * - Defines movement rules for specific piece type
 * - Validates moves according to piece rules
 * 
 * Open/Closed Principle (OCP):
 * ✅ Class hierarchy is open for extension:
 * - New piece types can be added by extending ChessPiece
 * - Existing pieces don't need modification when adding new ones
 * - Example: Hawk and Vortex pieces added without changing others
 * 
 * Liskov Substitution Principle (LSP):
 * ✅ All derived pieces maintain base class contract:
 * - Can be used anywhere ChessPiece is expected
 * - Movement patterns extend but don't violate base expectations
 * - Verified through comprehensive test suite
 * 
 * Interface Segregation Principle (ISP):
 * ✅ Piece interface is focused and minimal:
 * - Only methods needed by all pieces
 * - Special behaviors implemented in specific classes
 * 
 * Dependency Inversion Principle (DIP):
 * ✅ Depends on abstractions:
 * - Uses enums for Color and Direction
 * - Movement logic depends on abstract position concept
 */
public abstract class ChessPiece {

    protected Colour colour; // Chess piece color
    protected Direction[][] directions; // List of possible directions


    public ChessPiece(Colour colour) {
        this.colour = colour;
        setupDirections();
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    protected abstract void setupDirections();

    /**
     * Fetch all the positions of the wall
     * @param boardMap: Board Map representing current game board
     * @return map of piece and positions
     * */
    protected Map<ChessPiece, PositionOnBoard> getWallPieceMapping(Map<PositionOnBoard, ChessPiece> boardMap) {
        return new HashMap<>();  // No more wall pieces
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    public abstract Set<PositionOnBoard> getMovablePositions(Map<PositionOnBoard, ChessPiece> boardMap, PositionOnBoard start);

    /**
     * @return Colour of the chess piece
     * */
    public Colour getColour() {
        return this.colour;
    }
}