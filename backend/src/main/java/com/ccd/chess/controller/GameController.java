package com.ccd.chess.controller;

import com.ccd.chess.entity.dto.GameState;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.service.GameService;
import com.ccd.chess.service.interfaces.IGameInterface;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:8090")
public class GameController {
    private IGameInterface game;

    public GameController() {
        this.game = new GameService();
    }

    @GetMapping("/newGame")
    public ResponseEntity<Void> handleNewGame() {
        System.out.println("New Game");
        this.game = new GameService();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/onClick")
    public ResponseEntity<GameState> handleMove(@RequestBody String polygonText) throws InvalidPositionException {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        System.out.println("Polygon: " + polygonText);
        return ResponseEntity.ok(game.onClick(polygonText));
    }

    @GetMapping("/currentPlayer")
    public ResponseEntity<String> handlePlayerTurn() {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        System.out.println("Requesting current player");
        return ResponseEntity.ok(game.getTurn().toString());
    }

    @GetMapping("/board")
    public ResponseEntity<Map<String, String>> handleBoardRequest() {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(game.getBoard());
    }
}