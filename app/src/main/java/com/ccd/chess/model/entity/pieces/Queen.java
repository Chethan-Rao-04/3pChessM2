package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.util.Logger;
import com.ccd.chess.util.MovementUtil;

import static com.ccd.chess.util.MovementUtil.calculateNextPositionOrNull;

import java.util.*;

/**
 * Queen class extends ChessPiece. Move directions for the Queen, the polygons
 * to be highlighted, and its legal moves are checked here. Queen can executeMove like
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
     * Queen can executeMove in any direction (like Rook + Bishop combined)
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
            while(tmp != null && !positionSet.contains(tmp)) {
                ChessPiece target = boardMap.get(tmp);
                if(target == null) {
                    Logger.d(TAG, "Empty position: " + tmp);
                    positionSet.add(tmp);
                    tmp = calculateNextPositionOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
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