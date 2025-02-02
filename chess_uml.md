@startuml


' Set theme and styling
skinparam backgroundColor transparent
skinparam classAttributeIconSize 0
skinparam monochrome true
skinparam linetype ortho

' Set direction for better layout
left to right direction

' Package com.ccd.chess
package "com.ccd.chess" {
' Controller package
package "controller" {
class GameController {
- game: IGameService
- boardService: IBoardService
+ GameController(boardService: IBoardService)
+ getNewGame(): ResponseEntity<Void>
+ HandlePolygonCLick(polygonText: String): ResponseEntity<GameState>
+ getPlayerTurn(): ResponseEntity<String>
+ getBoard(): ResponseEntity<Map<String, String>>
}
}

    ' Exceptions package
    package "exceptions" {
        class InvalidMoveException {
            + InvalidMoveException(message: String)
        }
        class InvalidPositionException {
            + InvalidPositionException(message: String)
        }
    }

    ' Model package
    package "model" {
        package "dto" {
            class GameState {
                - board: Map<String, String>
                - turn: Colour
                - gameOver: boolean
                - winner: String
                - highlightedPolygons: List<String>
                + GameState(board: Map<String, String>, highlightPolygons: List<String>)
                + setGameOver(winner: String): void
            }
        }

        package "entity" {
            package "pieces" {
                abstract class ChessPiece {
                    # colour: Colour
                    # directions: Direction[][]
                    + ChessPiece(colour: Colour)
                    # {abstract} setupDirections(): void
                    + getMovablePositions(board: Map<Position, ChessPiece>, position: Position): Set<Position>
                    + getColour(): Colour
                    + toString(): String
                }

                class Bishop extends ChessPiece {
                    + Bishop(colour: Colour)
                    # setupDirections(): void
                }
                class Queen extends ChessPiece {
                    + Queen(colour: Colour)
                    # setupDirections(): void
                }
                class King extends ChessPiece {
                    + castlingPositionMapping: Map<Colour, List<Position>>
                    + King(colour: Colour)
                    # setupDirections(): void
                }
                class Knight extends ChessPiece {
                    + Knight(colour: Colour)
                    # setupDirections(): void
                }
                class Rook extends ChessPiece {
                    + Rook(colour: Colour)
                    # setupDirections(): void
                }
                class Pawn extends ChessPiece {
                    + Pawn(colour: Colour)
                    # setupDirections(): void
                }
                class Hawk extends Knight {
                    + Hawk(colour: Colour)
                    # setupDirections(): void
                }
                class Vortex extends ChessPiece {
                    + Vortex(colour: Colour)
                    # setupDirections(): void
                }
            }

            package "enums" {
                enum Colour {
                    GOLD
                    SILVER
                    BRONZE
                    + next(): Colour
                    + toString(): String
                }
                enum Direction {
                    FORWARD
                    BACKWARD
                    LEFT
                    RIGHT
                    + toString(): String
                }
                enum PositionOnBoard {
                    - colour: Colour
                    - row: int
                    - column: int
                    + getColour(): Colour
                    + getRow(): int
                    + getColumn(): int
                    + toString(): String
                    + {static} get(colour: Colour, row: int, column: int): PositionOnBoard
                    + {static} get(polygonIndex: int): PositionOnBoard
                    + neighbour(direction: Direction): PositionOnBoard
                }
            }
        }
    }

    ' Service package
    package "service" {
        package "impl" {
            class BoardServiceImpl implements IBoardService {
                - {static} TAG: String
                # boardMap: Map<Position, ChessPiece>
                - turn: Colour
                - gameOver: boolean
                - winner: String
                - highlightPolygons: Set<Position>
                - hasHawkMoved: boolean
                + BoardServiceImpl()
                - InitializePiecesOnBoard(colour: Colour): void
                + convertBoardToWebView(): Map<String, String>
                + executeMove(start: Position, end: Position): void
                + validateMove(start: Position, end: Position): boolean
                + isPieceOwnedByCurrentPlayer(position: Position): boolean
                + getPossibleMoves(position: Position): Set<Position>
                + checkIfGameOver(): boolean
                + retrieveWinner(): String
                + getCurrentTurn(): Colour
                - isKinginCheck(colour: Colour, boardMap: Map<Position, ChessPiece>): boolean
                - isKingInCheckMate(colour: Colour, boardMap: Map<Position, ChessPiece>): boolean
                - isKingInCheckAfterMove(colour: Colour, boardMap: Map<Position, ChessPiece>, start: Position, end: Position): boolean
                - findKingPosition(colour: Colour, boardMap: Map<PositionOnBoard, ChessPiece>): PositionOnBoard
            }

            class GameServiceImpl implements IGameService {
                - {static} TAG: String
                - board: IBoardService
                - moveStartPos: PositionOnBoard
                - moveEndPos: PositionOnBoard
                - highlightPolygons: Set<PositionOnBoard>
                + GameServiceImpl(boardService: IBoardService)
                + getBoard(): Map<String, String>
                + onClick(polygonLabel: String): GameState
                + getTurn(): Colour
            }
        }

        package "interfaces" {
            interface IBoardService {
                + getWebViewBoard(): Map<String, String>
                + move(start: PositionOnBoard, end: PositionOnBoard): void
                + isLegalMove(start: PositionOnBoard, end: PositionOnBoard): boolean
                + isCurrentPlayersPiece(position: PositionOnBoard): boolean
                + getPossibleMoves(position: PositionOnBoard): Set<PositionOnBoard>
                + checkIfGameOver(): boolean
                + FetchWinner(): String
                + getCurrentTurn(): Colour
            }

            interface IGameService {
                + getBoard(): Map<String, String>
                + onClick(polygonLabel: String): GameState
                + getTurn(): Colour
            }
        }
    }

    ' Util package
    package "util" {
        class BoardAdapter {
            + {static} ConvertBoardToStringRep(board: Map<PositionOnBoard, ChessPiece>): Map<String, String>
            + {static} convertPossibleMovesToStringRep(positions: List<PositionOnBoard>): List<String>
            + {static} GeneratePolygonID(polygonLabel: String): int
        }

        class Logger {
            + {static} TAG: String
            + {static} d(tag: String, message: String): void
            + {static} e(tag: String, error: String): void
        }

        class MovementUtil {
            + {static} calculateNextPosition(piece: ChessPiece, directions: Direction[], position: PositionOnBoard): PositionOnBoard
            + {static} calculateNextPositionOrNull(piece: ChessPiece, directions: Direction[], from: PositionOnBoard, to: PositionOnBoard): PositionOnBoard
        }

        class PieceFactory {
            + {static} createPiece(type: String, colour: Colour): ChessPiece
        }
    }
}

' Relationships
GameController --> interfaces: uses
GameServiceImpl --> IBoardService: uses
GameServiceImpl --> GameState: creates
BoardServiceImpl --> ChessPiece: manages
BoardAdapter --> ChessPiece: converts
PieceFactory --> ChessPiece: creates
MovementUtil --> ChessPiece: assists
ChessPiece --> Colour: has
ChessPiece --> Direction: uses
BoardServiceImpl --> PositionOnBoard: uses
GameServiceImpl --> PositionOnBoard: uses


@enduml