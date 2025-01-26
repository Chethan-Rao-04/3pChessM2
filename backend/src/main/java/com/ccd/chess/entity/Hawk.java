package com.ccd.chess.entity;

import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.enums.Direction;
import com.ccd.chess.entity.enums.Position;
import com.ccd.chess.utility.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.ccd.chess.utility.MovementUtil.stepOrNull;

/**
 * Hawk class
 * */
public class Hawk extends Knight {

    private static final String TAG = "HAWK";

    public Hawk(Colour colour) {
        super(colour);
    }

    @Override
    public void setupDirections() {
        // Knight-like L-shaped moves (moves 2 squares in one direction and 1 in a perpendicular direction)
        this.directions = new Direction[][]{
                {Direction.FORWARD, Direction.FORWARD},          // Move two steps forward
                {Direction.BACKWARD, Direction.BACKWARD},         // Move two steps backward
                {Direction.LEFT, Direction.LEFT},            // Move two steps left
                {Direction.RIGHT, Direction.RIGHT},

        };
    }

@Override
public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
    Set<Position> positionSet = new HashSet<>();

    for (Direction[] step : this.directions) {
        // Check the adjacent square first (one step)
        Position adjacent = stepOrNull(this, new Direction[]{step[0]}, start);

        if (adjacent != null) {
            // If adjacent square is blocked, can't move in this direction
            if (boardMap.get(adjacent) != null) {
                continue;
            }

            // Check the final destination (two steps)
            Position destination = stepOrNull(this, step, start);
            if (destination != null) {
                ChessPiece targetPiece = boardMap.get(destination);
                // Can move if square is empty or contains enemy piece
                if (targetPiece == null || targetPiece.getColour() != this.getColour()) {
                    positionSet.add(destination);
                }
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
        public String toString () {
            return this.colour.toString() + "H";
        }
    }




//    }@Override
//    public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
//        Set<Position> positionSet = new HashSet<>();
//
//        for (Direction[] step : this.directions) {
//            // Check the adjacent square first (one step)
//            Position adjacent = stepOrNull(this, new Direction[]{step[0]}, start);
//
//            if (adjacent != null) {
//                // If adjacent square is blocked, can't move in this direction
//                if (boardMap.get(adjacent) != null) {
//                    continue;
//                }
//
//                // Check the final destination (two steps)
//                Position destination = stepOrNull(this, step, start);
//                if (destination != null) {
//                    ChessPiece targetPiece = boardMap.get(destination);
//                    // Can move if square is empty or contains enemy piece
//                    if (targetPiece == null || targetPiece.getColour() != this.getColour()) {
//                        positionSet.add(destination);
//                    }
//                }
//            }
//        }
//
//        return positionSet;
//    }