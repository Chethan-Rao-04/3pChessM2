package com.ccd.chess.entity;

import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.entity.enums.Position;
import com.ccd.chess.utility.Logger;
import com.ccd.chess.utility.PieceFactory;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

    public class Board {

        private static final String TAG = "Board";

        /** A map from board positions to the pieces at that position **/
        protected Map<Position, ChessPiece> boardMap;
        private Colour turn;
        private boolean gameOver;
        private String winner;
        private Set<Position> highlightPolygons = new HashSet<>();

        /**
         * Board constructor. Places pieces on the board and initializes variables
         * */
        public Board(){
            boardMap = new HashMap<Position,ChessPiece>();
            turn = Colour.BLUE;
            gameOver = false;
            winner = null;
            try{
                // Blue, Green, Red
                for(Colour colour: Colour.values()) {
                    placeChessPieces(colour);
                }
            } catch(InvalidPositionException e) {
                Logger.e(TAG, "InvalidPositionException: "+e.getMessage());
            }
        }

        /**
         * Place all the pieces on the board initially at start positions
         * @param colour for each color place the pieces
         * */
        private void placeChessPieces(Colour colour) throws InvalidPositionException {
            // place ROOK
            Position[] rookStartPositions = new Position[] {Position.get(colour,0,0), Position.get(colour,0,7)};
            boardMap.put(rookStartPositions[0], PieceFactory.createPiece("Rook", colour));
            boardMap.put(rookStartPositions[1], PieceFactory.createPiece("Rook", colour));

            // place KNIGHT
            Position[] knightStartPositions = new Position[] {Position.get(colour,0,1), Position.get(colour,0,6)};
            boardMap.put(knightStartPositions[0], PieceFactory.createPiece("Knight",colour));
            boardMap.put(knightStartPositions[1], PieceFactory.createPiece("Knight",colour));

            // place BISHOP
            Position[] bishopStartPositions = new Position[] {Position.get(colour,0,2), Position.get(colour,0,5)};
            boardMap.put(bishopStartPositions[0], PieceFactory.createPiece("Bishop",colour));
            boardMap.put(bishopStartPositions[1], PieceFactory.createPiece("Bishop",colour));

            // place Queen
            Position queenStartingPosition = Position.get(colour,0,3);
            boardMap.put(queenStartingPosition, PieceFactory.createPiece("Queen",colour));

            // place KING
            Position kingStartingPosition = Position.get(colour,0,4);
            boardMap.put(kingStartingPosition, PieceFactory.createPiece("King",colour));

            // place PAWN
            for(int i = 1; i<7; i++){
                Position ithPawnPosition = Position.get(colour,1,i);
                boardMap.put(ithPawnPosition, PieceFactory.createPiece("Pawn",colour));
            }

            // place JESTER
            Position jesterStartPosition = Position.get(colour,1,0);
            boardMap.put(jesterStartPosition, PieceFactory.createPiece("Jester",colour));

            // place WALL
            Position wallStartPosition = Position.get(colour, 1, 7);
            ChessPiece wall = PieceFactory.createPiece("Wall",colour);
            boardMap.put(wallStartPosition, wall);
        }

        /**
         * To check if the game is over
         * @return boolean
         * */
        public boolean isGameOver() {
            return gameOver;
        }

        /**
         * To fetch the winner
         * @return String of Winner name
         * */
        public String getWinner() {
            return winner;
        }

        /**
         * Called to move a piece from one position to another
         * @param start The start position
         * @param end The end position
         * */
        public void move(Position start, Position end) throws InvalidMoveException, InvalidPositionException {
            if(isLegalMove(start, end)) {
                ChessPiece mover = boardMap.get(start);
                ChessPiece taken = boardMap.get(end);
                boardMap.remove(start);  //empty start polygon

                if(mover instanceof Pawn && end.getRow()==0 && end.getColour()!=mover.getColour()){
                    boardMap.put(end, new Queen(mover.getColour()));  //promote pawn
                } else {
                    boardMap.put(end,mover);  //move piece
                }

                if(mover instanceof King && start.getColumn()==4 && start.getRow()==0) {
                    if(end.getColumn()==2) {//castle left, update rook
                        Position rookPos = Position.get(mover.getColour(),0,0);
                        boardMap.put(Position.get(mover.getColour(),0,3), boardMap.get(rookPos));
                        boardMap.remove(rookPos);
                    }else if(end.getColumn()==6){//castle right, update rook
                        Position rookPos = Position.get(mover.getColour(),0,7);
                        boardMap.put(Position.get(mover.getColour(),0,5), boardMap.get(rookPos));
                        boardMap.remove(rookPos);
                    }
                }

                if(taken !=null){
                    // jester switch position with other piece
                    if (mover instanceof Jester){
                        // switch places
                        boardMap.put(end,mover);
                        boardMap.put(start, taken);
                    }
                }

                for(Colour c: Colour.values()) {
                    if(c!=turn) {
                        if(isCheckMate(c, boardMap)) {
                            gameOver = true;
                            winner = mover.getColour().toString();
                        }
                    }
                }

                turn = Colour.values()[(turn.ordinal()+1)%3];
            } else {
                throw new InvalidMoveException("Illegal Move: "+start+"-"+end);
            }
        }

        /**
         * Checks if the piece can move from start to end positions
         * @param start The start position
         * @param end The end position
         * @return boolean
         * */
        public boolean isLegalMove(Position start, Position end) {
            ChessPiece mover = getPiece(start);
            if (mover == null) {
                return false; // No piece present at start position
                // }