package com.ccd.chess.main;

import abstraction.IGameInterface;
import com.google.common.collect.ImmutableSet;
import com.ccd.chess.entity.Board;

import entity.Board;


import com.ccd.chess.entity.enums.Colour;
import com.ccd.chess.entity.enums.Position;

import com.ccd.chess.utility.Logger;


import java.util.Map;
import java.util.Set;

/**
 * Class containing the main Loggeric of the backend.
 * the click inputs from the webapp is communicated with the backend.
 */
public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();
    private final Board board;
    private Position moveStartPos, moveEndPos;
    private Set<Position> highlightPolygons;

    /**
     * GameMain Constructor. Entry point to the backend Loggeric
     * */
    public GameMain() {
        Logger.d(TAG, "initGame GameMain()");
        board = new Board();
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
     * @return returns which colour turn it is currently
     * */
    @Override
    public Colour getTurn() {
        return board.getTurn();
    }

}
