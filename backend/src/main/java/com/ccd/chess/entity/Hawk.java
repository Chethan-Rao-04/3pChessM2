package com.ccd.chess.entity;

import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.enums.Direction;

/**
 * Jester class that extends Knight class
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
    public void setupDirections() {
        // Knight-like L-shaped moves (moves 2 squares in one direction and 1 in a perpendicular direction)
        this.directions = new Direction[][] {
                {Direction.FORWARD, Direction.FORWARD},          // Move two steps forward
                {Direction.BACKWARD, Direction.BACKWARD},         // Move two steps backward
                {Direction.LEFT,Direction.LEFT},            // Move two steps left
                {Direction.RIGHT, Direction.RIGHT},

        };
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