```plantuml
@startuml Chess Project Class Diagram

' Enums
enum Colour {
    WHITE
    BLACK
}

enum Direction {
    FORWARD
    BACKWARD
    LEFT
    RIGHT
}

enum PositionOnBoard {
    + get(colour: Colour, row: int, column: int): PositionOnBoard
    + getColour(): Colour
    + getRow(): int
    + getColumn(): int
}

' Abstract base class
abstract class ChessPiece {
    # colour: Colour
    # directions: Direction[][]
    + ChessPiece(colour: Colour)
    # {abstract} setupDirections(): void
    # getWallPieceMapping(boardMap: Map<PositionOnBoard, ChessPiece>): Map<ChessPiece, PositionOnBoard>
    + {abstract} getMovablePositions(boardMap: Map<PositionOnBoard, ChessPiece>, start: PositionOnBoard): Set<PositionOnBoard>
    + getColour(): Colour
    + toString(): String
}

' Concrete chess pieces
class King extends ChessPiece {
    + castlingPositionMapping: Map<Colour, List<PositionOnBoard>>
    + King(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

class Queen extends ChessPiece {
    + Queen(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

class Bishop extends ChessPiece {
    + Bishop(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

class Knight extends ChessPiece {
    + Knight(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

class Rook extends ChessPiece {
    + Rook(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

class Pawn extends ChessPiece {
    + Pawn(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

class Hawk extends Knight {
    + Hawk(colour: Colour)
    # setupDirections()
}

class Vortex extends ChessPiece {
    + Vortex(colour: Colour)
    # setupDirections()
    + getMovablePositions()
}

' Factory
class PieceFactory {
    + {static} createPiece(type: String, colour: Colour): ChessPiece
}

' Services
interface IGameInterface {
    + getBoard(): Map<String, String>
    + onClick(polygonLabel: String): GameState
    + getTurn(): Colour
}

class GameService implements IGameInterface {
    - board: IBoardService
    - moveStartPos: PositionOnBoard
    - moveEndPos: PositionOnBoard
    - highlightPolygons: Set<PositionOnBoard>
    + GameService(boardService: IBoardService)
    + getBoard(): Map<String, String>
    + onClick(polygonLabel: String): GameState
    + getTurn(): Colour
}

interface IBoardService {
    + isGameOver(): boolean
    + getWinner(): String
    + move(start: PositionOnBoard, end: PositionOnBoard): void
    + isLegalMove(start: PositionOnBoard, end: PositionOnBoard): boolean
    + getTurn(): Colour
    + getWebViewBoard(): Map<String, String>
    + getPossibleMoves(position: PositionOnBoard): Set<PositionOnBoard>
    + isCurrentPlayersPiece(position: PositionOnBoard): boolean
}

class BoardServiceImpl implements IBoardService {
    - boardMap: Map<PositionOnBoard, ChessPiece>
    - turn: Colour
    - gameOver: boolean
    - winner: String
    - highlightPolygons: Set<PositionOnBoard>
    - hasHawkMoved: boolean
    + BoardServiceImpl()
    - placeChessPieces(colour: Colour): void
    + isGameOver(): boolean
    + getWinner(): String
    + move(start: PositionOnBoard, end: PositionOnBoard): void
    + isLegalMove(start: PositionOnBoard, end: PositionOnBoard): boolean
    + getTurn(): Colour
    + getWebViewBoard(): Map<String, String>
    + getPossibleMoves(position: PositionOnBoard): Set<PositionOnBoard>
    + isCurrentPlayersPiece(position: PositionOnBoard): boolean
}

' Data Transfer Objects
class GameState {
    - board: Map<String, String>
    - highlightPolygons: List<String>
    - gameOver: boolean
    - winner: String
}

' Utilities
class MovementUtil {
    + {static} step(piece: ChessPiece, directions: Direction[], start: PositionOnBoard): PositionOnBoard
    + {static} stepOrNull(piece: ChessPiece, directions: Direction[], start: PositionOnBoard): PositionOnBoard
}

class BoardAdapter {
    + {static} calculatePolygonId(polygonLabel: String): int
    + {static} convertModelBoardToViewBoard(boardMap: Map<PositionOnBoard, ChessPiece>): Map<String, String>
    + {static} convertHighlightPolygonsToViewBoard(highlightPolygons: Set<PositionOnBoard>): List<String>
}

' Relationships
ChessPiece --> "1" Colour
ChessPiece --> "*" Direction
PieceFactory ..> ChessPiece : creates
BoardServiceImpl ..> PieceFactory : uses
ChessPiece ..> MovementUtil : uses
BoardServiceImpl --> "1" Map<PositionOnBoard, ChessPiece>
GameService --> "1" IBoardService
GameService ..> GameState : creates
BoardServiceImpl ..> BoardAdapter : uses
GameService ..> BoardAdapter : uses

@enduml
``` 