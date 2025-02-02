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
        for(Direction d: step){
            if((piece.getColour()!=current.getColour() && piece instanceof Pawn) || reverse){//reverse directions for knights
                switch(d){
                    case FORWARD: d = Direction.BACKWARD; break;
                    case BACKWARD: d = Direction.FORWARD; break;
                    case LEFT: d = Direction.RIGHT; break;
                    case RIGHT: d = Direction.LEFT; break;
                }
            }
           PositionOnBoard next = current.neighbour(d);
            if(next.getColour()!= current.getColour()){//need to reverse directions when switching between sections of the board
                reverse=true;
            }
            current = next;
        }
        return current;
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
        for(Direction d: step){
            if((piece.getColour()!=current.getColour() && piece instanceof Pawn) || reverse){//reverse directions for knights
                switch(d){
                    case FORWARD: d = Direction.BACKWARD; break;
                    case BACKWARD: d = Direction.FORWARD; break;
                    case LEFT: d = Direction.RIGHT; break;
                    case RIGHT: d = Direction.LEFT; break;
                }
            }
           PositionOnBoard next = current.neighbour(d);
            if(next.getColour()!= current.getColour()){//need to reverse directions when switching between sections of the board
                reverse=true;
            }
            current = next;
        }
        return current;
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
//            Logger.e(TAG, "Exception: "+e.getMessage());
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
//            Logger.e(TAG, "Exception: "+e.getMessage());
            return null;
        }
    }
}