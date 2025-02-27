package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.util.MovementUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Hawk class
 * */
public class Hawk extends Knight {

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
public Set<PositionOnBoard> getMovablePositions(Map<PositionOnBoard, ChessPiece> boardMap, PositionOnBoard start) {
    Set<PositionOnBoard> positionSet = new HashSet<>();

    for (Direction[] step : this.directions) {
        // Check the adjacent square first (one calculateNextPosition)
        PositionOnBoard adjacent = MovementUtil.calculateNextPositionOrNull(this, new Direction[]{step[0]}, start);

        if (adjacent != null) {
            // If adjacent square is blocked, can't executeMove in this direction
            if (boardMap.get(adjacent) != null) {
                continue;
            }

            // Check the final destination (two steps)
            PositionOnBoard destination = MovementUtil.calculateNextPositionOrNull(this, step, start);
            if (destination != null) {
                ChessPiece targetPiece = boardMap.get(destination);
                // Can executeMove if square is empty or contains enemy piece
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

