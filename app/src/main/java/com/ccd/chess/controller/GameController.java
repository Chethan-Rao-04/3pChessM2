package com.ccd.chess.controller;

import com.ccd.chess.model.dto.GameState;
import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.service.impl.GameService;
import com.ccd.chess.service.impl.BoardServiceImpl;
import com.ccd.chess.service.interfaces.IGameInterface;
import com.ccd.chess.service.interfaces.IBoardService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

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
 * - IGameInterface
 * - IBoardService
 * Any implementation of these interfaces can be used
 * 
 * Interface Segregation Principle (ISP):
 * ✅ Uses focused interfaces:
 * - IGameInterface for game operations
 * - IBoardService for board operations
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
    private IGameInterface game;
    private final IBoardService boardService;

    @Autowired
    public GameController(IBoardService boardService) {
        this.boardService = boardService;
        this.game = new GameService(boardService);
    }

    @GetMapping("/newGame")
    public ResponseEntity<Void> handleNewGame() {
        System.out.println("New Game");
        this.game = new GameService(boardService);
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