package com.ccd.chess.entity;

import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.enums.Direction;

/**
 * Moves like a knight. It cannot take a piece, however others can take it out.
 * When it takes any piece of different colour, it switches its position with that piece. (except Wall)
 * */
public class Jester extends Knight {

    /**
     * Jester constructor
     * @param colour: Colour of the chess piece being initiated
     * */
    public Jester(Colour colour) {
        super(colour);
    }
    @Override
    protected void setupDirections() {
        this.directions = new Direction[][] {{Direction.FORWARD,Direction.LEFT},
                {Direction.FORWARD,Direction.RIGHT},{Direction.LEFT,Direction.FORWARD},
                {Direction.RIGHT,Direction.FORWARD},{Direction.BACKWARD,Direction.LEFT},
                {Direction.BACKWARD,Direction.RIGHT},{Direction.LEFT,Direction.BACKWARD},
                {Direction.RIGHT,Direction.BACKWARD}};
    }
    /**
     * Returns custom string representation of the class
     * @return String
     * */
    @Override
    public String toString() {
        return this.colour.toString()+"J";
    }
}