package com.ccd.chess.controller;

import com.ccd.chess.model.dto.GameState;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.service.impl.GameServiceImpl;
import com.ccd.chess.service.interfaces.BoardService;
import com.ccd.chess.service.interfaces.GameService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * GameController - REST API endpoints for game interactions
 *
 * SOLID Principles Implementation:
 *
 * Single Responsibility Principle (SRP):
 * ✅ Controller has single responsibility:
 * - Handles HTTP endpoints and request routing
 * - Delegates game logic to service layer
 *
 * Open/Closed Principle (OCP):
 * ✅ New endpoints can be added without modifying existing ones
 * ✅ Uses ResponseEntity for flexible response handling
 *
 * Liskov Substitution Principle (LSP):
 * ✅ Works with service interfaces:
 * - GameService
 * - BoardService
 * Any implementation of these interfaces can be used
 *
 * Interface Segregation Principle (ISP):
 * ✅ Uses focused interfaces:
 * - GameService for game operations
 * - BoardService for board operations
 *
 * Dependency Inversion Principle (DIP):
 * ✅ Depends on abstractions:
 * - Injects service interfaces, not implementations
 * - Uses Spring DI for loose coupling
 */
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:8090")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private GameService game;
    private final BoardService boardService;

    @Autowired
    public GameController(BoardService boardService) {
        this.boardService = boardService;
        this.game = new GameServiceImpl(boardService);
    }

    @GetMapping("/newGame")
    public ResponseEntity<Void> getNewGame() {
        logger.info("New Game");
        this.game = new GameServiceImpl(boardService);
        return ResponseEntity.ok().build();
    }

    /** this method is called when a player clicks on a polygon
     * it
     * */
    @PostMapping("/onClick")
    public ResponseEntity<GameState> HandlePolygonCLick(@RequestBody String polygonText) throws InvalidPositionException {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("Polygon: " + polygonText);
        return ResponseEntity.ok(game.processClickEvent(polygonText));
    }

    @GetMapping("/currentPlayer")
    public ResponseEntity<String> getPlayerTurn() {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("Requesting current player");
        return ResponseEntity.ok(game.currentTurn().toString());
    }

    @GetMapping("/board")
    public ResponseEntity<Map<String, String>> getBoard() {
        if (game == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(game.retrieveBoardState());
    }
}