package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Direction;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.exceptions.InvalidPositionException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ccd.chess.util.Logger;

import static com.ccd.chess.util.MovementUtil.stepOrNull;


/**
 * King class extends ChessPiece. Move directions for the King, the polygons
 * to be highlighted, and its legal moves are checked here
 **/
public class King extends ChessPiece {

    public static final String TAG = "KING";

    public Map<Colour, List<Position>> castlingPositionMapping;

    /**
     * King constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public King(Colour colour) {
        super(colour);
        castlingPositionMapping = new HashMap<>();

        try {
            for(Colour c: Colour.values()) {
                List<Position> castlingPositions = castlingPositionMapping.getOrDefault(c, new ArrayList<>());
                castlingPositions.add(Position.get(c,0,6));
                castlingPositions.add(Position.get(c,0,2));
                castlingPositionMapping.put(c, castlingPositions);
            }
        } catch (Exception e) {
            Logger.e(TAG, "Exception while adding castling end position: "+e.getMessage());
        }
    }

    /**
     * Method to initialize directions for a chess piece
     **/
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.LEFT},{Direction.FORWARD,Direction.RIGHT},
                {Direction.LEFT,Direction.FORWARD},{Direction.RIGHT,Direction.FORWARD},{Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT},{Direction.LEFT,Direction.BACKWARD},{Direction.RIGHT,Direction.BACKWARD},
                {Direction.FORWARD},{Direction.BACKWARD},{Direction.LEFT},{Direction.RIGHT}};
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getMovablePositions(Map<Position, ChessPiece> boardMap, Position start) {
        Collection<Position> wallPiecePositions = getWallPieceMapping(boardMap).values();
        Set<Position> positionSet = new HashSet<>();
        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position end = stepOrNull(mover, step, start);
            if(wallPiecePositions.contains(end) || positionSet.contains(end)) {
                continue;
            }

            if (end != null) {
                if(boardMap.get(end)!=null) {
                    if(boardMap.get(end).getColour()!=mover.getColour()) {
                        Logger.d(TAG, "position enemy: " + end);
                        positionSet.add(end);
                    }
                } else {
                    Logger.d(TAG, "position: "+end);
                    positionSet.add(end);
                }
            }
        }

        List<Position> castlingPositions = castlingPositionMapping.getOrDefault(mover.getColour(), new ArrayList<>());
        for(Position end: castlingPositions) {
            if (boardMap.get(end)==null && isCastlingPossible(boardMap, start, end)) {
                Logger.d(TAG, "position castling: " + end);
                positionSet.add(end);
            }
        }

        return positionSet;
    }

    /**
     * Method to check if castling is possible between given positions
     * @param board: Board class instance representing current game board
     * @param start: start position of piece on board
     * @param end: start position of piece on board
     * @return bool if castling is possible
     * */
    private boolean isCastlingPossible(Map<Position, ChessPiece> board, Position start, Position end) {
        Logger.d(TAG, "isCastlingPossible: start: "+start+", end: "+end);
        ChessPiece mover = this;
        Colour moverCol = mover.getColour();
        try{
            if(start==Position.get(moverCol,0,4)){
                if(end==Position.get(moverCol,0,6)){
                    ChessPiece castle = board.get(Position.get(moverCol,0,7));
                    ChessPiece empty1 = board.get(Position.get(moverCol,0,5));
                    ChessPiece empty2 = board.get(Position.get(moverCol,0,6));
                    if(castle instanceof Rook && castle.getColour() == mover.getColour()
                            && empty1 == null && empty2 == null) {
                        Logger.d(TAG, "Castling Legal Move 1: True");
                        return true;
                    }
                }
                if(end==Position.get(moverCol,0,2)){
                    ChessPiece castle = board.get(Position.get(moverCol,0,0));
                    ChessPiece empty1 = board.get(Position.get(moverCol,0,1));
                    ChessPiece empty2 = board.get(Position.get(moverCol,0,2));
                    ChessPiece empty3 = board.get(Position.get(moverCol,0,3));
                    if(castle instanceof Rook && castle.getColour() == mover.getColour()
                            && empty1 == null && empty2 == null && empty3 == null) {
                        Logger.d(TAG, "Castling Legal Move 2: True");
                        return true;
                    }
                }
            }
        } catch (InvalidPositionException e) {
            //do nothing, steps went off board.
            Logger.e(TAG, "InvalidPositionException: " + e.getMessage());
        }
        return false;
    }

    /**
     * Returns custom string representation of the class
     * @return String
     * */
    @Override
    public String toString() {
        return this.colour.toString()+"K";
    }
}