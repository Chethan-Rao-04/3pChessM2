package com.ccd.chess.entity;

import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.enums.Direction;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.entity.enums.Position;

import com.ccd.chess.utility.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.ccd.chess.utility.MovementUtil.step;
import static com.ccd.chess.utility.MovementUtil.stepOrNull;

/**
 * Bishop class extends ChessPiece. Move directions for the bishop, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class Bishop extends ChessPiece {

    private static final String TAG = "BasePiece";

    protected Colour colour; // colour of the chess piece [Red, Green, Blue]
    protected Direction[][] directions; // List of possible directions a piece can move. [Left, Right, Forward, Backward]

    /**
     * BasePiece constructor
     * @param colour: Colour of the chess piece being initiated
     * */
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
    protected Map<ChessPiece, Position> getWallPieceMapping(Map<Position, ChessPiece> boardMap) {
        Map<ChessPiece, Position> res = new HashMap<>();
        for(Position pos: boardMap.keySet()) {
            ChessPiece piece = boardMap.get(pos);
            if(piece instanceof Wall) {
                res.put(piece, pos);
            }
        }

        return res;
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    public abstract Set<Position> getHighlightPolygons(Map<Position, BasePiece> boardMap, Position start);

    /**
     * @return Colour of the chess piece
     * */
    public Colour getColour() {
        return this.colour;
    }
}