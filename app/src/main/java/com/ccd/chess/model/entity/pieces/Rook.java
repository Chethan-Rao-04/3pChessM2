package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.util.Logger;
import com.ccd.chess.util.MovementUtil;

import static com.ccd.chess.util.MovementUtil.calculateNextPositionOrNull;

import java.util.*;

/**
 * Rook class extends ChessPiece. Move directions for the Rook, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class Rook extends ChessPiece {

    private static final String TAG = "ROOK";

    /**
     * Rook constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Rook(Colour colour) {
        super(colour);
        setupDirections();
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.BACKWARD},{Direction.LEFT},{Direction.RIGHT},{Direction.FORWARD}};
    }

    /**
     * Fetch all the possible positions where a piece can executeMove on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to executeMove
     * */
    @Override
    public Set<PositionOnBoard> getMovablePositions(Map<PositionOnBoard, ChessPiece> boardMap, PositionOnBoard start) {
        Set<PositionOnBoard> positionSet = new HashSet<>();
        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            PositionOnBoard tmp = MovementUtil.calculateNextPositionOrNull(mover, step, start);
            while(tmp != null && !positionSet.contains(tmp) && boardMap.get(tmp)==null) {
                Logger.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp);
                tmp = calculateNextPositionOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }

            // found a piece in direction
            if(tmp!=null && boardMap.get(tmp)!=null) {
                if(boardMap.get(tmp).getColour()!=mover.getColour()) {
                    Logger.d(TAG, "Opponent tmp: " + tmp);
                    positionSet.add(tmp);
                } else {
                    Logger.d(TAG, "Mine tmp: " + tmp);
                }
            }
        }

        return positionSet;
    }

    /**
     * Returns custom string representation of the class
     * @return String
     * */
    @Override
    public String toString() {
        return this.colour.toString()+"R";
    }
}