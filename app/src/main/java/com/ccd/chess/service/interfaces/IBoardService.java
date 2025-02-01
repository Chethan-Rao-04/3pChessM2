package com.ccd.chess.service.interfaces;

import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.exceptions.InvalidPositionException;

import java.util.Map;
import java.util.Set;

public interface IBoardService {
    boolean isGameOver();
    
    String getWinner();
    
    void move(PositionOnBoard start, PositionOnBoard end) throws InvalidMoveException, InvalidPositionException;
    
    boolean isLegalMove(PositionOnBoard start, PositionOnBoard end);
    
    Colour getTurn();
    
    Map<String, String> getWebViewBoard();
    
    Set<PositionOnBoard> getPossibleMoves(PositionOnBoard position);
    
    boolean isCurrentPlayersPiece(PositionOnBoard position);
}