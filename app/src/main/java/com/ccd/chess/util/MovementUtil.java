package com.ccd.chess.util;

import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Pawn;

/**
 * MovementUtil - helper class for the movement of chess pieces
 * To validate the calculateNextPosition with each executeMove in different directions
 **/
public class MovementUtil {

    /**
     * calculateNextPosition method to get the next position based on the direction input
     * @param piece piece to be moved
     * @param step directions to executeMove
     * @param current current position of the piece
     * @return Position of the piece after the calculateNextPosition
     **/
    public static PositionOnBoard calculateNextPosition(ChessPiece piece, Direction[] step, PositionOnBoard current) throws InvalidPositionException {
        boolean reverse = false;
        PositionOnBoard newPosition = current;
        for (Direction direction : step) {
            Direction newDirection = direction;
            if ((piece.getColour() != newPosition.getColour() && piece instanceof Pawn) || reverse) { // reverse directions for knights
                switch (newDirection) {
                    case FORWARD: newDirection = Direction.BACKWARD; break;
                    case BACKWARD: newDirection = Direction.FORWARD; break;
                    case LEFT: newDirection = Direction.RIGHT; break;
                    case RIGHT: newDirection = Direction.LEFT; break;
                }
            }
            PositionOnBoard next = newPosition.neighbour(newDirection);
            if (next.getColour() != newPosition.getColour()) { // need to reverse directions when switching between sections of the board
                reverse = true;
            }
            newPosition = next;
        }
        return newPosition;
    }

    /**
     * calculateNextPosition method to get the next position based on the direction input
     * @param piece piece to be moved
     * @param step directions to executeMove
     * @param current current position of the piece
     * @param reverse if movement is in reverse direction
     * @return Position of the piece after the calculateNextPosition
     **/
    public static PositionOnBoard calculateNextPosition(ChessPiece piece, Direction[] step, PositionOnBoard current, boolean reverse) throws InvalidPositionException {
        PositionOnBoard newPosition = current;
        for (Direction direction : step) {
            Direction newDirection = direction;
            if ((piece.getColour() != newPosition.getColour() && piece instanceof Pawn) || reverse) { // reverse directions for knights
                switch (newDirection) {
                    case FORWARD: newDirection = Direction.BACKWARD; break;
                    case BACKWARD: newDirection = Direction.FORWARD; break;
                    case LEFT: newDirection = Direction.RIGHT; break;
                    case RIGHT: newDirection = Direction.LEFT; break;
                }
            }
            PositionOnBoard next = newPosition.neighbour(newDirection);
            if (next.getColour() != newPosition.getColour()) { // need to reverse directions when switching between sections of the board
                reverse = true;
            }
            newPosition = next;
        }
        return newPosition;
    }

    /**
     * calculateNextPosition method to get the next position based on the direction input, return null if not valid
     * @param piece piece to be moved
     * @param step directions to executeMove
     * @param current current position of the piece
     * @return Position of the piece after the calculateNextPosition
     **/
    public static PositionOnBoard calculateNextPositionOrNull(ChessPiece piece, Direction[] step, PositionOnBoard current) {
        try {
            return calculateNextPosition(piece, step, current);
        } catch (InvalidPositionException e) {
            return null;
        }
    }

    /**
     * calculateNextPosition method to get the next position based on the direction input, return null if not valid
     * @param piece piece to be moved
     * @param step directions to executeMove
     * @param current current position of the piece
     * @param reverse if movement is in reverse direction
     * @return Position of the piece after the calculateNextPosition
     **/
    public static PositionOnBoard calculateNextPositionOrNull(ChessPiece piece, Direction[] step, PositionOnBoard current, boolean reverse) {
        try {
            return calculateNextPosition(piece, step, current, reverse);
        } catch (InvalidPositionException e) {
            return null;
        }
    }
}