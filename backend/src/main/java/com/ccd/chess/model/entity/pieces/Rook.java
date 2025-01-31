package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.Position;

import java.util.Map;
import java.util.Set;


import com.ccd.chess.util.Logger;


import java.util.Collection;
import java.util.HashSet;

import static com.ccd.chess.util.MovementUtil.step;
import static com.ccd.chess.util.MovementUtil.stepOrNull;

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
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getMovablePositions(Map<Position, ChessPiece> boardMap, Position start) {
        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        Set<Position> positionSet = new HashSet<>();
        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && !positionSet.contains(tmp) && boardMap.get(tmp)==null) {
                Logger.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp);
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
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

        for(Position position: wallPiecePositions) {
            if(positionSet.contains(position)) {
                Logger.d(TAG, "Removed a wallPiecePos: "+position);
                positionSet.remove(position);
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
