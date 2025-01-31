package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;




import java.util.Map;
import java.util.Set;
import java.util.HashMap;


public abstract class ChessPiece {

    private static final String TAG = "ChessPiece";

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