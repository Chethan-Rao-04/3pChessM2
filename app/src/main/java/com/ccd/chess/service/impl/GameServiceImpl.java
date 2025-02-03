package com.ccd.chess.service.impl;

import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.model.dto.GameState;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import com.ccd.chess.service.interfaces.GameService;
import com.ccd.chess.service.interfaces.BoardService;
import com.ccd.chess.util.BoardAdapter;
import com.google.common.collect.ImmutableSet;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.util.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ccd.chess.util.BoardAdapter.GeneratePolygonID;

public class GameServiceImpl implements GameService {

    private static final String TAG = GameServiceImpl.class.getSimpleName();
    private final BoardService board;
    private PositionOnBoard moveStartPos;
    private PositionOnBoard moveEndPos;
    private Set<PositionOnBoard> highlightPolygons;

    /**
     * Constructor with dependency injection for testing
     * @param boardService The board service implementation to use
     */
    public GameServiceImpl(BoardService boardService) {
        Logger.d(TAG, "initGame GameMain()");
        this.board = boardService;
        this.moveStartPos = null;
        this.moveEndPos = null;
        this.highlightPolygons = ImmutableSet.of();
    }

    /**
     * Get the current board map being used by backend for current game session
     * @return Board map
     */
    @Override
    public Map<String, String> retrieveBoardState() {
        return board.convertBoardToWebView();
    }

    /**
     * Responsible for sending mouse click events to backend and apply game logic over it to display
     * updated board layout to player.
     * @param polygonLabel The unique label of the polygon which is clicked by player
     * @return GameState which contains current game board layout and list of polygons to highlight
     */
    @Override
    public GameState processClickEvent(String polygonLabel) {
        try {
            // polygonPos must be in range [0, 95]
            Logger.d(TAG, ">>> processClickEvent called: polygonLabel: " + polygonLabel);
            int polygonPos = GeneratePolygonID(polygonLabel);
            Logger.d(TAG, ">>> processClickEvent called: polygonPos: " + polygonPos);

            PositionOnBoard position = PositionOnBoard.get(polygonPos);
            if (board.isPieceOwnedByCurrentPlayer(position)) { // player selects his own piece - first move
                moveStartPos = position;
                Logger.d(TAG, ">>> moveStartPos: " + moveStartPos);
                highlightPolygons = board.calculatePossibleMoves(moveStartPos);
                if (highlightPolygons.isEmpty()) { // Selected piece has no polygon to move, reset selection
                    moveStartPos = null;
                }
            } else if (moveStartPos != null) {
                moveEndPos = PositionOnBoard.get(polygonPos);
                board.executeMove(moveStartPos, moveEndPos);
                Logger.d(TAG, ">>> moveStartPos: " + moveStartPos + ", moveEndPos: " + moveEndPos);

                moveStartPos = null;
                moveEndPos = null;
                highlightPolygons = null;
            }
        } catch (InvalidMoveException e) {
            Logger.e(TAG, "InvalidMoveException processClickEvent: " + e.getMessage());
            moveStartPos = null;
            moveEndPos = null;
            highlightPolygons = null;
        } catch (InvalidPositionException e) {
            Logger.e(TAG, "InvalidPositionException processClickEvent: " + e.getMessage());
            moveStartPos = null;
            moveEndPos = null;
            highlightPolygons = null;
        }
        List<String> highlightPolygonsList = BoardAdapter.convertPossibleMovesToStringRep(highlightPolygons);
        GameState clickResponse = new GameState(retrieveBoardState(), highlightPolygonsList);
        if (board.checkIfGameOver()) {
            String winner = board.retrieveWinner();
            if ("B".equals(winner)) {
                winner = "SILVER";
            } else if ("G".equals(winner)) {
                winner = "BRONZE";
            } else if ("R".equals(winner)) {
                winner = "GOLD";
            }
            Logger.d(TAG, "Winner: " + winner);
            clickResponse.setGameOver(winner);
        }
        Logger.d(TAG, "ClickResponse: " + clickResponse);
        return clickResponse;
    }

    /**
     * @return returns which colour turn it is currently
     */
    @Override
    public Colour currentTurn() {
        Logger.d(TAG, "Current turn: " + board.getCurrentTurn());
        return board.getCurrentTurn();
    }
}