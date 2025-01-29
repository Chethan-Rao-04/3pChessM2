package com.ccd.chess.service.impl;

import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.model.dto.GameState;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.Position;
import com.ccd.chess.service.interfaces.IGameInterface;
import com.ccd.chess.service.interfaces.IBoardService;
import com.ccd.chess.util.BoardAdapter;
import com.google.common.collect.ImmutableSet;
import com.ccd.chess.exceptions.InvalidMoveException;
import com.ccd.chess.util.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ccd.chess.util.BoardAdapter.calculatePolygonId;

public class GameService implements IGameInterface {

    private static final String TAG = GameService.class.getSimpleName();
    private final IBoardService board;
    private Position moveStartPos, moveEndPos;
    private Set<Position> highlightPolygons;

    /**
     * GameMain Constructor. Entry point to the backend logic
     * */
    public GameService() {
        this(new BoardServiceImpl());
    }

    /**
     * Constructor with dependency injection for testing
     * @param boardService The board service implementation to use
     * */
    public GameService(IBoardService boardService) {
        Logger.d(TAG, "initGame GameMain()");
        this.board = boardService;
        moveStartPos = null;
        moveEndPos = null;
        highlightPolygons = ImmutableSet.of();
    }

    /**
     * Get the current board map being used by backend for current game session
     * @return Board map
     * */
    @Override
    public Map<String, String> getBoard() {
        return board.getWebViewBoard();
    }

    /**
     * Responsible for sending mouse click events to backend and apply game logic over it to display
     * updated board layout to player.
     * @param  polygonLabel The unique label of the polygon which is clicked by player
     * @return GameState which contains current game board layout and list of polygons to highlight
     **/
    @Override
    public GameState onClick(String polygonLabel) {
        try {
            // polygonPos must be in range [0, 95]
            Logger.d(TAG, ">>> onClick called: polygonLabel: "+polygonLabel);
            int polygonPos = calculatePolygonId(polygonLabel);
            Logger.d(TAG, ">>> onClick called: polygonPos:  "+polygonPos);

            Position position = Position.get(polygonPos);
            if (board.isCurrentPlayersPiece(position)) { // player selects his own piece - first move
                moveStartPos = position;
                Logger.d(TAG, ">>> moveStartPos: " + moveStartPos);
                highlightPolygons = board.getPossibleMoves(moveStartPos);
                if(highlightPolygons.isEmpty()) { // Selected piece has no polygon to move, reset selection
                    moveStartPos = null;
                }
            } else if(moveStartPos != null){
                moveEndPos = Position.get(polygonPos);
                board.move(moveStartPos, moveEndPos);
                Logger.d(TAG, ">>> moveStartPos: " + moveStartPos + ", moveEndPos: " + moveEndPos);

                moveStartPos = moveEndPos = null;
                highlightPolygons = null;
            }
        } catch (InvalidMoveException e) {
            Logger.e(TAG, "InvalidMoveException onClick: "+e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightPolygons = null;
        } catch (InvalidPositionException e) {
            Logger.e(TAG, "InvalidPositionException onClick: "+e.getMessage());
            moveStartPos = moveEndPos = null;
            highlightPolygons = null;
        }
        List<String> highlightPolygonsList = BoardAdapter.convertHighlightPolygonsToViewBoard(highlightPolygons);
        GameState clickResponse = new GameState(getBoard(), highlightPolygonsList);
        if(board.isGameOver()) {
            String winner = board.getWinner();
            if (winner.equals("B")) {
                winner = "SILVER";
            } else if (winner.equals("G")) {
                winner = "BRONZE";
            } else if (winner.equals("R")) {
                winner = "GOLD";
            }
            Logger.d(TAG, "Winner: "+winner);
            clickResponse.setGameOver(winner);
        }
        Logger.d(TAG, "ClickResponse: "+clickResponse);
        return clickResponse;
    }

    /**
     * @return returns which colour turn it is currently
     * */
    @Override
    public Colour getTurn() {
        Logger.d(TAG, "Current turn: " + board.getTurn());
        return board.getTurn();

    }

}