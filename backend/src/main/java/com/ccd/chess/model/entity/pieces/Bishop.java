package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.Position;

import com.ccd.chess.util.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static com.ccd.chess.util.MovementUtil.stepOrNull;

/**
 * Bishop class extends ChessPiece. Move directions for the bishop, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class Bishop extends ChessPiece {

    private static final String TAG = "BISHOP";

    /**
     * Bishop constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Bishop(Colour colour) {
        super(colour);
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.LEFT},
                {Direction.FORWARD,Direction.RIGHT},
                {Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT}};
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
//        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        Set<Position> positionSet = new HashSet<>();

        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && !positionSet.contains(tmp) && boardMap.get(tmp)==null) {
                Logger.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp); // to prevent same position to add in list again
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
            }

            // found a piece diagonally
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
        return this.colour.toString()+"B";
    }
}