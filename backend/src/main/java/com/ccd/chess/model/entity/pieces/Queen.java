package com.ccd.chess.model.entity.pieces;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.util.Logger;
import static com.ccd.chess.util.MovementUtil.step;
import static com.ccd.chess.util.MovementUtil.stepOrNull;

import java.util.*;


/**
 * Queen class extends ChessPiece. Move directions for the Queen, the polygons
 * to be highlighted, and its legal moves are checked here. Queen can move like
 * both a Rook and Bishop combined.
 **/
public class Queen extends ChessPiece {

    private static final String TAG = "QUEEN";

    /**
     * Queen constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Queen(Colour colour) {
        super(colour);
    }

    /**
     * Method to initialize directions for a chess piece
     * Queen can move in any direction (like Rook + Bishop combined)
     **/
    @Override
    public void setupDirections() {
        this.directions = new Direction[][] {
            {Direction.FORWARD}, {Direction.BACKWARD}, {Direction.LEFT}, {Direction.RIGHT},  // Rook-like moves
            {Direction.FORWARD, Direction.LEFT}, {Direction.FORWARD, Direction.RIGHT},       // Bishop-like moves
            {Direction.BACKWARD, Direction.LEFT}, {Direction.BACKWARD, Direction.RIGHT}
        };
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        Set<Position> positionSet = new HashSet<>();
        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && !wallPiecePositions.contains(tmp) && !positionSet.contains(tmp)) {
                ChessPiece target = boardMap.get(tmp);
                if(target == null) {
                    Logger.d(TAG, "Empty position: " + tmp);
                    positionSet.add(tmp);
                    tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
                } else if(target.getColour() != mover.getColour()) {
                    Logger.d(TAG, "Can capture: " + tmp);
                    positionSet.add(tmp);
                    break;
                } else {
                    Logger.d(TAG, "Blocked by own piece: " + tmp);
                    break;
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
        return this.colour.toString() + "Q";
    }
}

