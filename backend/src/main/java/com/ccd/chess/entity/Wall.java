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
 * Wall class extends Rook. The polygons
 * to be highlighted, and its legal moves are checked here
 * Move like a rook. It cannot take any piece and other pieces cannot take it too.
 * Pieces of different colours cannot pass it through, however, pieces of the same colour
 * as the wall can pass it through.
 **/
public class Wall extends Rook {

    private static final String TAG = "WALL";

    /**
     * Wall constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Wall(Colour colour) {
        super(colour);
    }

    /**
     * Fetch all the possible positions where a piece can move on board
     * @param boardMap: Board Map instance representing current game board
     * @param start: position of piece on board
     * @return Set of possible positions a piece is allowed to move
     * */
    @Override
    public Set<Position> getHighlightPolygons(Map<Position, ChessPiece> boardMap, Position start) {
        //List<Position> positions = new ArrayList<>();
        Set<Position> positionSet = new HashSet<>();
        ChessPiece mover = this;
        Direction[][] steps = this.directions;

        for (Direction[] step : steps) {
            Position tmp = stepOrNull(mover, step, start);
            while(tmp != null && boardMap.get(tmp)==null) {
                Logger.d(TAG, "tmp: "+tmp);
                positionSet.add(tmp);
                tmp = stepOrNull(mover, step, tmp, tmp.getColour()!=start.getColour());
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
        return this.colour.toString()+"W";
    }
}