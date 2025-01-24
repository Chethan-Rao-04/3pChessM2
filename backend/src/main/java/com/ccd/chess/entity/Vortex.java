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
 * Vortex class extends ChessPiece. Moves diagonally like a bishop but can jump over pieces.
 * Can only land on empty squares.
 */
public class Vortex extends ChessPiece {

    private static final String TAG = "VORTEX";

    /**
     * Vortex constructor
     * @param colour: Colour of the chess piece being initiated
     */
    public Vortex(Colour colour) {
        super(colour);
    }

    /**
     * Method to initialize diagonal directions for the Vortex
     */
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {
            {Direction.FORWARD, Direction.LEFT}, {Direction.FORWARD, Direction.RIGHT},
            {Direction.LEFT, Direction.FORWARD}, {Direction.RIGHT, Direction.FORWARD},
            {Direction.BACKWARD, Direction.LEFT}, {Direction.BACKWARD, Direction.RIGHT},
            {Direction.LEFT, Direction.BACKWARD}, {Direction.RIGHT, Direction.BACKWARD}
        };
    }

    /**
     * Fetch all possible positions where Vortex can move on board
     * Can jump over pieces but can only land on empty squares
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions piece is allowed to move
     */
    @Override
    public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        Set<Position> positionSet = new HashSet<>();

        for (Direction[] step : this.directions) {
            Position tmp = stepOrNull(this, step, start);
            
            // Continue in direction until board edge
            while (tmp != null) {
                // Only add position if square is empty
                if (boardMap.get(tmp) == null) {
                    Logger.d(TAG, "Adding empty position: " + tmp);
                    positionSet.add(tmp);
                }
                // Continue past occupied squares (jumping)
                tmp = stepOrNull(this, step, tmp, tmp.getColour() != start.getColour());
            }
        }

        // Remove wall positions
        for (Position position : wallPiecePositions) {
            positionSet.remove(position);
        }

        return positionSet;
    }

    /**
     * Returns string representation of Vortex
     * @return String
     */
    @Override
    public String toString() {
        return this.colour.toString() + "V";
    }
} 