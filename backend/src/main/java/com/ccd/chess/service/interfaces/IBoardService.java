package com.ccd.chess.service.interfaces;

import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.exceptions.InvalidPositionException;

import java.util.Map;
import java.util.Set;

public interface IBoardService {
    boolean isGameOver();
    
    String getWinner();
    
    void move(Position start, Position end) throws InvalidMoveException, InvalidPositionException;
    
    boolean isLegalMove(Position start, Position end);
    
    Colour getTurn();
    
    Map<String, String> getWebViewBoard();
    
    Set<Position> getPossibleMoves(Position position);
    
    boolean isCurrentPlayersPiece(Position position);
}