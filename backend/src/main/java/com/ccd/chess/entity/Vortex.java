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
 * Can also move exactly two squares in any straight direction (forward, backward, left, right).
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
     * Method to initialize diagonal and straight directions for the Vortex
     */
    @Override
    protected void setupDirections() {
        // Diagonal moves (like bishop)
        Direction[][] diagonalMoves = {
            {Direction.FORWARD, Direction.LEFT}, {Direction.FORWARD, Direction.RIGHT},
            {Direction.LEFT, Direction.FORWARD}, {Direction.RIGHT, Direction.FORWARD},
            {Direction.BACKWARD, Direction.LEFT}, {Direction.BACKWARD, Direction.RIGHT},
            {Direction.LEFT, Direction.BACKWARD}, {Direction.RIGHT, Direction.BACKWARD}
        };
        
        // Two-square straight moves
        Direction[][] straightMoves = {
            {Direction.FORWARD, Direction.FORWARD},       // Two squares forward
            {Direction.BACKWARD, Direction.BACKWARD},     // Two squares backward
            {Direction.LEFT, Direction.LEFT},             // Two squares left
            {Direction.RIGHT, Direction.RIGHT}            // Two squares right
        };

        // Combine both move types
        this.directions = new Direction[diagonalMoves.length + straightMoves.length][];
        System.arraycopy(diagonalMoves, 0, this.directions, 0, diagonalMoves.length);
        System.arraycopy(straightMoves, 0, this.directions, diagonalMoves.length, straightMoves.length);
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
        Set<Position> positionSet = new HashSet<>();
        Set<Position> visitedPositions = new HashSet<>(); // Track visited positions to prevent loops

        for (Direction[] step : this.directions) {
            Position tmp = stepOrNull(this, step, start);
            visitedPositions.clear(); // Reset visited positions for each direction
            
            if (step.length == 2 && step[0] == step[1]) {
                // For two-square straight moves, only consider the final position
                if (tmp != null && !visitedPositions.contains(tmp)) {
                    visitedPositions.add(tmp);
                    // Only add if square is empty
                    if (boardMap.get(tmp) == null) {
                        Logger.d(TAG, "Adding two-square move position: " + tmp);
                        positionSet.add(tmp);
                    }
                }
            } else {
                // For diagonal moves, continue until board edge
                while (tmp != null && !visitedPositions.contains(tmp)) {
                    visitedPositions.add(tmp);
                    
                    // Only add position if square is empty
                    if (boardMap.get(tmp) == null) {
                        Logger.d(TAG, "Adding diagonal move position: " + tmp);
                        positionSet.add(tmp);
                    }
                    
                    // Continue past occupied squares (jumping)
                    tmp = stepOrNull(this, step, tmp, false);
                }
            }
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