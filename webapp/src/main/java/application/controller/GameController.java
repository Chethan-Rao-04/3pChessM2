package application.controller;

import com.ccd.chess.common.GameState;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.service.GameService;
import com.ccd.chess.service.IGameInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * GameController class - interacts with the backend logic.
 * New game instances are created here.
 **/
@RestController
public class GameController {
    private IGameInterface game;

    public GameController() {
        // Initialize game by default
        this.game = new GameService();
    }

    /**
     * Method to create new game instance
     **/
    @GetMapping("/newGame")
    public ResponseEntity<Void> handleNewGame(){
        System.out.println("New Game");
        this.game = new GameService();
        return ResponseEntity.ok().build();
    }

    /**
     * Method to notify click events to the backend
     **/
    @PostMapping("/onClick")
    public ResponseEntity<GameState> handleMove(@RequestBody String polygonText) throws InvalidPositionException {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        System.out.println("Polygon: " + polygonText);
        return ResponseEntity.ok(game.onClick(polygonText));
    }

    /**
     * Method to fetch the current player information from backend
     **/
    @GetMapping("/currentPlayer")
    public ResponseEntity<String> handlePlayerTurn(){
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        System.out.println("Requesting current player");
        return ResponseEntity.ok(game.getTurn().toString());
    }

    /**
     * Method to fetch the current board information from backend
     **/
    @GetMapping("/board")
    public ResponseEntity<Map<String, String>> handleBoardRequest(){
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(game.getBoard());
    }
}