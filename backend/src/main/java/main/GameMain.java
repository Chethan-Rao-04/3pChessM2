package main;

import abstraction.IGameInterface;
import com.google.common.collect.ImmutableSet;
import common.Colour;
import common.Position;
import entity.Board;
import utility.Log;

import java.util.Map;
import java.util.Set;

/**
 * Class containing the main logic of the backend.
 * the click inputs from the webapp is communicated with the backend.
 */
public class GameMain implements IGameInterface {

    private static final String TAG = GameMain.class.getSimpleName();
    private final Board board;
    private Position moveStartPos, moveEndPos;
    private Set<Position> highlightPolygons;

    /**
     * GameMain Constructor. Entry point to the backend logic
     * */
    public GameMain() {
        Log.d(TAG, "initGame GameMain()");
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
