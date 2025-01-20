package com.ccd.chess.entity;

import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.enums.Direction;
// import com.ccd.chess.entity.enums.Position;


    public abstract class ChessPiece {

        private static final String TAG = "BasePiece";

        protected Colour colour; // colour of the chess piece [White, Black]
        protected Direction[][] directions; // List of possible directions a piece can move. [Left, Right, Forward, Backward]


        public ChessPiece(Colour colour) {
            this.colour = colour;
            setupDirections();
        }

        protected abstract void setupDirections();

        /**
         * Fetch all the possible positions where a piece can move on board
         * @param boardMap: Board Map representing current game board
         * @param start: position of piece on board
         * @return Set of possible positions a piece is allowed to move
         * */
        public abstract Set<Position> getPossibleMoves(Map<Position, ChessPiece> boardMap, Position start);

        public Colour getColour() {
            return this.colour;
        }
    }


