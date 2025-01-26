package com.ccd.chess.service;

import com.ccd.chess.entity.ChessPiece;
import com.ccd.chess.entity.King;
import com.ccd.chess.entity.Pawn;
import com.ccd.chess.entity.Queen;
import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.entity.enums.Position;
import com.ccd.chess.utility.Logger;
import com.ccd.chess.utility.PieceFactory;
import com.ccd.chess.utility.BoardAdapter;
import com.google.common.collect.ImmutableSet;
import com.ccd.chess.entity.Hawk;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

        public class BoardService {

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
            public BoardService(){
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

                // place HAWK
                Position hawkStartPosition = Position.get(colour,1,0);
                boardMap.put(hawkStartPosition, PieceFactory.createPiece("Hawk",colour));

                // place VORTEX
                Position vortexStartPosition = Position.get(colour, 1, 7);  // Place in Wall's previous position
                boardMap.put(vortexStartPosition, PieceFactory.createPiece("Vortex",colour));
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
                    
                    // Clear highlight polygons after validating the move
                    highlightPolygons.clear();

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

//                    if(taken !=null){
//                        // hawk switch position with other piece
//                        if (mover instanceof Hawk){
//                            // switch places
//                            boardMap.put(end,mover);
//                            boardMap.put(start, taken);
//                        }
//                    }

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
                if(mover == null) {
                    return false; // No piece present at start position
                }
                Colour moverCol = mover.getColour();
                
                // Always recalculate highlight polygons for the current move
                highlightPolygons = mover.getHighlightPolygons(this.boardMap, start);
                
                if(highlightPolygons.contains(end)) {
                    if(isCheck(turn, boardMap) && isCheckAfterLegalMove(turn, boardMap, start, end)) {
                        Logger.d(TAG, "Colour "+moverCol+" is in check, this move doesn't help. Do again!!");
                        return false;
                    } else if(isCheckAfterLegalMove(turn, boardMap, start, end)) {
                        Logger.d(TAG, "Colour "+moverCol+" will be in check after this move");
                        return false;
                    } else{
                        return true;
                    }
                }

                return false;
            }

            /**
             * Get the current player turn
             * @return Colour
             * */
            public Colour getTurn() {
                return turn;
            }

            /**
             * Get the piece on the selected position
             * @param position The current selected position
             * @return ChessPiece
             * */
            private ChessPiece getPiece(Position position) {
                return boardMap.get(position);
            }

            /**
             * For the web app to use, board map is converted to string and returned
             * @return map of position and piece converted to strings
             * */
            public Map<String, String> getWebViewBoard() {
                return BoardAdapter.convertModelBoardToViewBoard(this.boardMap);
            }

            /**
             * For the current selected piece, returns the possible moves
             * @param position The current selected piece position
             * @return Set of possible movements
             * */
            public Set<Position> getPossibleMoves(Position position) {
                ChessPiece mover = boardMap.get(position);
                if(mover == null) {
                    return ImmutableSet.of();
                }
                
                // Always calculate fresh highlight polygons
                highlightPolygons = mover.getHighlightPolygons(this.boardMap, position);

                Colour moverColour = mover.getColour();
                Set<Position> nonCheckPositions = new HashSet<>();
                for(Position endPos: highlightPolygons) {
                    if(!isCheckAfterLegalMove(moverColour, this.boardMap, position, endPos)) {
                        nonCheckPositions.add(endPos);
                    }
                }

                return nonCheckPositions;
            }

            /**
             * Tells if the current player has selected his own piece
             * @param position The current position of the piece
             * @return boolean
             * */
            public boolean isCurrentPlayersPiece(Position position) {
                return getPiece(position) != null && getPiece(position).getColour()==turn;
            }


            /**     Check / Check-mate logic helper functions **/

            private boolean isCheck(Colour colour, Map<Position, ChessPiece> boardMap) {
                Position kingPosition = getKingPosition(colour, boardMap);

                for(Position position: boardMap.keySet()) {
                    ChessPiece piece = boardMap.get(position);
                    // hawk piece has no power to take out any piece
                    if(piece.getColour() != colour && !(piece instanceof Hawk)) {
                        Set<Position> possibleTargetPositions = piece.getHighlightPolygons(boardMap, position);
                        if(possibleTargetPositions.contains(kingPosition)) {
                            Logger.d(TAG, "Piece "+piece+" is attacking King of colour "+colour);
                            return true;
                        }
                    }
                }
                return false;
            }

            private boolean isCheckMate(Colour colour, Map<Position, ChessPiece> boardMap) {
                if(!isCheck(colour, boardMap)) {
                    return false;
                }

                for(Position position: boardMap.keySet()) {
                    ChessPiece piece = boardMap.get(position);
                    if(piece.getColour()==colour) {
                        Set<Position> possibleMoves = piece.getHighlightPolygons(boardMap, position);
                        for(Position endPos: possibleMoves) {
                            if(!isCheckAfterLegalMove(colour, boardMap, position, endPos)) {
                                Logger.d(TAG, "Piece "+piece+" can help colour "+colour+" to come out of check: st: "+position+", end: "+endPos);
                                return false;
                            }
                        }
                    }
                }

                return true;
            }

            private boolean isCheckAfterLegalMove(Colour colour, Map<Position, ChessPiece> boardMap, Position start, Position end) {
                Map<Position, ChessPiece> copyBoardMap = new HashMap<>(boardMap);
                ChessPiece piece = copyBoardMap.get(start);
                copyBoardMap.remove(start);
                copyBoardMap.put(end, piece);

                if(!isCheck(colour, copyBoardMap)) {
                    return false;
                }

                return true;
            }

            private Position getKingPosition(Colour colour, Map<Position, ChessPiece> boardMap) {
                for(Position position: boardMap.keySet()) {
                    ChessPiece piece = boardMap.get(position);
                    if(piece instanceof King && piece.getColour()==colour) {
                        return position;
                    }
                }
                return null;
            }
        }       // }