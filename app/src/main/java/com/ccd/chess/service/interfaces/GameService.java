package com.ccd.chess.service.interfaces;

import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.dto.GameState;
import java.util.Map;

public interface GameService {
    /**
     * Get the current board map being used by backend for current game session
     * @return Board map
     * */
    Map<String, String> retrieveBoardState();

    /**
     * Responsible for sending mouse click events to backend and apply game logic over it to display
     * updated board layout to player.
     * @param  polygonLabel The unique label of the polygon which is clicked by player
     * @return GameState which contains current game board layout and list of polygons to highlight
     **/
    GameState processClickEvent(String polygonLabel);

    /**
     * @return returns which colour turn it is currently
     * */
    Colour currentTurn();
}
