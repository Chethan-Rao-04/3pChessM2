package com.ccd.chess.service.impl;

import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.model.entity.pieces.ChessPiece;
import com.ccd.chess.model.entity.pieces.Hawk;
import com.ccd.chess.model.entity.pieces.King;
import com.ccd.chess.model.entity.pieces.Pawn;
import com.ccd.chess.model.entity.pieces.Queen;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.util.Logger;
import com.ccd.chess.util.PieceFactory;
import com.ccd.chess.util.BoardAdapter;
import com.google.common.collect.ImmutableSet;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import com.ccd.chess.service.interfaces.IBoardService;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements IBoardService {

    private static final String TAG = "Board";

    /** A map from board positions to the pieces at that position **/
    protected Map<PositionOnBoard, ChessPiece> boardMap;
    private Colour turn;
    private boolean gameOver;
    private String winner;
    private Set<PositionOnBoard> highlightPolygons = new HashSet<>();
    private boolean hasHawkMoved = false;

    /**
     * Board constructor. Places pieces on the board and initializes variables
     * */
    public BoardServiceImpl(){
        boardMap = new HashMap<PositionOnBoard,ChessPiece>();
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
        PositionOnBoard[] rookStartPositions = new PositionOnBoard[] {PositionOnBoard.get(colour,0,0), PositionOnBoard.get(colour,0,7)};
        boardMap.put(rookStartPositions[0], PieceFactory.createPiece("Rook", colour));
        boardMap.put(rookStartPositions[1], PieceFactory.createPiece("Rook", colour));

        // place KNIGHT
        PositionOnBoard[] knightStartPositions = new PositionOnBoard[] {PositionOnBoard.get(colour,0,1), PositionOnBoard.get(colour,0,6)};
        boardMap.put(knightStartPositions[0], PieceFactory.createPiece("Knight",colour));
        boardMap.put(knightStartPositions[1], PieceFactory.createPiece("Knight",colour));

        // place BISHOP
        PositionOnBoard[] bishopStartPositions = new PositionOnBoard[] {PositionOnBoard.get(colour,0,2), PositionOnBoard.get(colour,0,5)};
        boardMap.put(bishopStartPositions[0], PieceFactory.createPiece("Bishop",colour));
        boardMap.put(bishopStartPositions[1], PieceFactory.createPiece("Bishop",colour));

        // place Queen
        PositionOnBoard queenStartingPosition = PositionOnBoard.get(colour,0,3);
        boardMap.put(queenStartingPosition, PieceFactory.createPiece("Queen",colour));

        // place KING
        PositionOnBoard kingStartingPosition = PositionOnBoard.get(colour,0,4);
        boardMap.put(kingStartingPosition, PieceFactory.createPiece("King",colour));

        // place PAWN
        for(int i = 1; i<7; i++){
            PositionOnBoard ithPawnPosition = PositionOnBoard.get(colour,1,i);
            boardMap.put(ithPawnPosition, PieceFactory.createPiece("Pawn",colour));
        }

        // place HAWK
        PositionOnBoard hawkStartPosition = PositionOnBoard.get(colour,1,0);
        boardMap.put(hawkStartPosition, PieceFactory.createPiece("Hawk",colour));

        // place VORTEX
        PositionOnBoard vortexStartPosition = PositionOnBoard.get(colour, 1, 7);  // Place in Wall's previous position
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
    public void move(PositionOnBoard start, PositionOnBoard end) throws InvalidMoveException, InvalidPositionException {
        if(!isCurrentPlayersPiece(start)) {
            throw new InvalidMoveException("Not your turn");
        }

        if(isLegalMove(start, end)) {
            ChessPiece mover = boardMap.get(start);
            ChessPiece taken = boardMap.get(end);
            boardMap.remove(start);
            highlightPolygons.clear();

            // Handle Hawk's special move
            if(mover instanceof Hawk) {
                if(taken != null) {  // Only if Hawk captures a piece
                    if(!hasHawkMoved) {
                        hasHawkMoved = true;
                        boardMap.put(end, mover);
                        return; // Don't change turn yet, allow second move
                    } else {
                        hasHawkMoved = false; // Reset for next turn
                    }
                } else {
                    // If Hawk moves without capturing, treat as normal move
                    hasHawkMoved = false;
                }
            }

            if(mover instanceof Pawn && end.getRow()==0 && end.getColour()!=mover.getColour()){
                boardMap.put(end, new Queen(mover.getColour()));  //promote pawn
            } else {
                boardMap.put(end,mover);  //move piece
            }

            if(mover instanceof King && start.getColumn()==4 && start.getRow()==0) {
                if(end.getColumn()==2) {//castle left, update rook
                    PositionOnBoard rookPos = PositionOnBoard.get(mover.getColour(),0,0);
                    boardMap.put(PositionOnBoard.get(mover.getColour(),0,3), boardMap.get(rookPos));
                    boardMap.remove(rookPos);
                }else if(end.getColumn()==6){//castle right, update rook
                    PositionOnBoard rookPos = PositionOnBoard.get(mover.getColour(),0,7);
                    boardMap.put(PositionOnBoard.get(mover.getColour(),0,5), boardMap.get(rookPos));
                    boardMap.remove(rookPos);
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

            // Only change turn if it's not a Hawk's first capture
            if (!(mover instanceof Hawk && hasHawkMoved)) {
                turn = Colour.values()[(turn.ordinal()+1)%3];
            }
        } else {
            throw new InvalidMoveException("Invalid move");
        }
    }

    /**
     * Checks if the piece can move from start to end positions
     * @param start The start position
     * @param end The end position
     * @return boolean
     * */
    public boolean isLegalMove(PositionOnBoard start, PositionOnBoard end) {
        ChessPiece mover = getPiece(start);
        if(mover == null) {
            return false; // No piece present at start position
        }
        Colour moverCol = mover.getColour();

        // Always recalculate highlight polygons for the current move
        highlightPolygons = mover.getMovablePositions(this.boardMap, start);

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
    private ChessPiece getPiece(PositionOnBoard position) {
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
    public Set<PositionOnBoard> getPossibleMoves(PositionOnBoard position) {
        ChessPiece mover = boardMap.get(position);
        if(mover == null) {
            return ImmutableSet.of();
        }

        // Always calculate fresh highlight polygons
        highlightPolygons = mover.getMovablePositions(this.boardMap, position);

        Colour moverColour = mover.getColour();
        Set<PositionOnBoard> nonCheckPositions = new HashSet<>();
        for(PositionOnBoard endPos: highlightPolygons) {
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
    public boolean isCurrentPlayersPiece(PositionOnBoard position) {
        return getPiece(position) != null && getPiece(position).getColour()==turn;
    }

    /**
     * Gets the current board map.
     * @return Map of positions to chess pieces
     */
    public Map<PositionOnBoard, ChessPiece> getBoardMap() {
        return boardMap;
    }

    /**     Check / Check-mate logic helper functions **/

    private boolean isCheck(Colour colour, Map<PositionOnBoard, ChessPiece> boardMap) {
        PositionOnBoard kingPosition = getKingPosition(colour, boardMap);

        for(PositionOnBoard position: boardMap.keySet()) {
            ChessPiece piece = boardMap.get(position);
            if(piece.getColour() != colour) {
                Set<PositionOnBoard> possibleTargetPositions = piece.getMovablePositions(boardMap, position);
                if(possibleTargetPositions.contains(kingPosition)) {
                    Logger.d(TAG, "Piece "+piece+" is attacking King of colour "+colour);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCheckMate(Colour colour, Map<PositionOnBoard, ChessPiece> boardMap) {
        if(!isCheck(colour, boardMap)) {
            return false;
        }

        for(PositionOnBoard position: boardMap.keySet()) {
            ChessPiece piece = boardMap.get(position);
            if(piece.getColour()==colour) {
                Set<PositionOnBoard> possibleMoves = piece.getMovablePositions(boardMap, position);
                for(PositionOnBoard endPos: possibleMoves) {
                    if(!isCheckAfterLegalMove(colour, boardMap, position, endPos)) {
                        Logger.d(TAG, "Piece "+piece+" can help colour "+colour+" to come out of check: st: "+position+", end: "+endPos);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isCheckAfterLegalMove(Colour colour, Map<PositionOnBoard, ChessPiece> boardMap, PositionOnBoard start, PositionOnBoard end) {
        Map<PositionOnBoard, ChessPiece> copyBoardMap = new HashMap<>(boardMap);
        ChessPiece piece = copyBoardMap.get(start);
        copyBoardMap.remove(start);
        copyBoardMap.put(end, piece);

        if(!isCheck(colour, copyBoardMap)) {
            return false;
        }

        return true;
    }

    private PositionOnBoard getKingPosition(Colour colour, Map<PositionOnBoard, ChessPiece> boardMap) {
        for(PositionOnBoard position: boardMap.keySet()) {
            ChessPiece piece = boardMap.get(position);
            if(piece instanceof King && piece.getColour()==colour) {
                return position;
            }
        }
        return null;
    }
}