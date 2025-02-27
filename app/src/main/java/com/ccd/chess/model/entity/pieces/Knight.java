package com.ccd.chess.model.entity.pieces;



import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;

import com.ccd.chess.util.Logger;
import com.ccd.chess.util.MovementUtil;




/**
 * Knight class extends ChessPiece. Move directions for the Knight, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class Knight extends ChessPiece {

    private static final String TAG = "KNIGHT";

    /**
     * Knight constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Knight(Colour colour) {
        super(colour);
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.FORWARD,Direction.LEFT},
                {Direction.FORWARD,Direction.FORWARD,Direction.RIGHT},{Direction.FORWARD,Direction.LEFT,Direction.LEFT},
                {Direction.FORWARD,Direction.RIGHT,Direction.RIGHT},{Direction.BACKWARD,Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.BACKWARD,Direction.RIGHT},{Direction.BACKWARD,Direction.LEFT,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT,Direction.RIGHT},{Direction.LEFT,Direction.LEFT,Direction.FORWARD},
                {Direction.LEFT,Direction.LEFT,Direction.BACKWARD},{Direction.LEFT,Direction.FORWARD,Direction.FORWARD},
                {Direction.LEFT,Direction.BACKWARD,Direction.BACKWARD},{Direction.RIGHT,Direction.RIGHT,Direction.FORWARD},
                {Direction.RIGHT,Direction.RIGHT,Direction.BACKWARD},{Direction.RIGHT,Direction.FORWARD,Direction.FORWARD},
                {Direction.RIGHT,Direction.BACKWARD,Direction.BACKWARD}};
    }

    /**
     * Fetch all the possible positions where a piece can executeMove on board
     * @param boardMap: Board class instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to executeMove
     * */
    @Override
    public Set<PositionOnBoard> getMovablePositions(Map<PositionOnBoard, ChessPiece> boardMap, PositionOnBoard start) {
        Set<PositionOnBoard> positionSet = new HashSet<>();
        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for(Direction[] step: steps) {
            PositionOnBoard end = MovementUtil.calculateNextPositionOrNull(mover, step, start);

            if(positionSet.contains(end)) {
                continue;
            }

            if(end != null) {
                ChessPiece target = boardMap.get(end);

                if(target!=null) {
                    if(target.getColour()!=mover.getColour()) {
                        Logger.d(TAG, "position enemy: "+end);
                        positionSet.add(end);
                    }
                } else {
                    Logger.d(TAG, "position: "+end);
                    positionSet.add(end);
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
        return this.colour.toString()+"N";
    }
}