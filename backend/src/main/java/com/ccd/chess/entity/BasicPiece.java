package com.ccd.chess.entity;

public class BasicPiece {
    package model;

import common.Colour;
import common.Direction;
import common.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

    /**
     *  Abstract Base class for all chess pieces. All chess pieces must extend this class
     *  and provide implementation to abstract methods according to their rules.
     **/
    public abstract class BasePiece {

        private static final String TAG = "BasePiece";

        protected Colour colour; // colour of the chess piece [Red, Green, Blue]
        protected Direction[][] directions; // List of possible directions a piece can move. [Left, Right, Forward, Backward]

        /**
         * BasePiece constructor
         * @param colour: Colour of the chess piece being initiated
         * */
        public BasePiece(Colour colour) {
            this.colour = colour;
            setupDirections();
        }

        /**
         * Method to initialize directions for a chess piece
         **/
        protected abstract void setupDirections();


        public Colour getColour() {
            return this.colour;
        }
    }


}
