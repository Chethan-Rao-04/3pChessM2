package com.ccd.chess.model.entity.pieces;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.util.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.ccd.chess.util.MovementUtil.stepOrNull;

/**
 * Vortex class extends ChessPiece. Moves one square diagonally in any direction,
 * and then can move one square left from those diagonal positions.
 * Can capture enemy pieces but cannot land on friendly pieces.
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
        // Diagonal moves (one square)
        this.directions = new Direction[][] {
            {Direction.FORWARD, Direction.LEFT},   // Forward-left diagonal
            {Direction.FORWARD, Direction.RIGHT},  // Forward-right diagonal
            {Direction.BACKWARD, Direction.LEFT},  // Backward-left diagonal
            {Direction.BACKWARD, Direction.RIGHT}  // Backward-right diagonal
        };
    }

    /**
     * Fetch all possible positions where Vortex can move on board
     * First moves one square diagonally, then can move one square left from those positions
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions piece is allowed to move
     */
    @Override
    public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
        Set<Position> positionSet = new HashSet<>();

        // First get all diagonal positions (one square)
        for (Direction[] step : this.directions) {
            Position diagonalPos = stepOrNull(this, step, start);
            if (diagonalPos != null) {
                ChessPiece targetPiece = boardMap.get(diagonalPos);
                // Can move to or capture at diagonal position
                if (targetPiece == null || targetPiece.getColour() != this.getColour()) {
                    Logger.d(TAG, "Adding diagonal move position: " + diagonalPos);
                    positionSet.add(diagonalPos);
                    
                    // From diagonal position, try to move one square left
                    Position leftPos = stepOrNull(this, new Direction[]{Direction.LEFT}, diagonalPos);
                    if (leftPos != null) {
                        ChessPiece leftPiece = boardMap.get(leftPos);
                        if (leftPiece == null || leftPiece.getColour() != this.getColour()) {
                            Logger.d(TAG, "Adding left move position from diagonal: " + leftPos);
                            positionSet.add(leftPos);
                        }
                    }
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