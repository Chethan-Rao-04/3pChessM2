package com.ccd.chess.service.interfaces;

import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.exceptions.InvalidPositionException;

import java.util.Map;
import java.util.Set;

/**
 * Interface for the BoardService
 * This interface is responsible for handling the game logic and board state
 */
public interface BoardService {
    boolean checkIfGameOver();
    
    String retrieveWinner();
    
    void executeMove(PositionOnBoard start, PositionOnBoard end) throws InvalidMoveException, InvalidPositionException;
    
    boolean validateMove(PositionOnBoard start, PositionOnBoard end);
    
    Colour getCurrentTurn();
    
    Map<String, String> convertBoardToWebView();
    
    Set<PositionOnBoard> calculatePossibleMoves(PositionOnBoard position);
    
    boolean isPieceOwnedByCurrentPlayer(PositionOnBoard position);
}