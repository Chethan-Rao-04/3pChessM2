package com.ccd.chess.model.dto;

import java.util.List;
import java.util.Map;

/**
 * GameState class to store the state of the game
 */
public class GameState {

    private final List<String> highlightedPolygons;
    private final Map<String, String> board;
    private boolean isGameOver;
    private String winner;

    /**
     * GameState constructor
     */
    public GameState(Map<String, String> board, List<String> highlightedPolygons) {
        this.board = board;
        this.highlightedPolygons = highlightedPolygons;
    }

    /**
     * Method returns list of polygons to be highlighted
     * @return List of Strings
     */
    public List<String> getHighlightedPolygons() {
        return highlightedPolygons;
    }

    /**
     * Method to share the board info to the web app
     * @return Map with board position and piece
     */
    public Map<String, String> getBoard() {
        return board;
    }

    /**
     * Method to share the winner info to the web app
     * @param winner is set
     */
    public void setGameOver(String winner) {
        this.isGameOver = true;
        this.winner = winner;
    }

    /**
     * method to check if the game is over.
     **/
    public boolean isGameOver() {
        return this.isGameOver;
    }

    /**
     * Method to get the winner of the game.
     * @return String representing the winner
     */
    public String getWinner() {
        return this.winner;
    }

    /**
     * method to get the highlighted polygons and the board.
     * @return String
     **/
    @Override
    public String toString() {
        return "GameState{" +
                "highlightedPolygons=" + highlightedPolygons +
                ", board=" + board +
                '}';
    }
}
