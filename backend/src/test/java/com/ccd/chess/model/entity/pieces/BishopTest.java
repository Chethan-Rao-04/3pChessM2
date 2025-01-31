package com.ccd.chess.model.entity.pieces;

import com.ccd.chess.service.impl.BoardServiceImpl;
import com.google.common.collect.ImmutableSet;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.model.entity.enums.PositionOnBoard;
import org.junit.jupiter.api.BeforeEach;
import com.ccd.chess.model.entity.enums.Colour;
import com.ccd.chess.service.impl.BoardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
* This class contains unit tests for the Bishop class.
*/
class BishopTest   {

   private BoardServiceImpl board;
   private Map<PositionOnBoard, ChessPiece> boardMap;

   /**
    * Initializes a new Board instance before each test.
    */
   @BeforeEach
   void initBeforeEachBoardTest() {
       board = new BoardServiceImpl();
       boardMap = board.getBoardMap();
   }

   /**
    * Tests if the bishop can move in diagonal directions by checking if it has valid moves from a position.
    */
   @Test
   void setupDirections_bishopCanMoveInDiagonalDirections_True() {
       boardMap.put(startPos, bishop);
       assertFalse(moves.isEmpty());
   }

   /**
    * expecting true.
    *
    * @param colour Colour of the bishop
    */
   @ParameterizedTest
   @EnumSource(Colour.class)
   void isLegalMove_bishopMovesToEmptySquare_True(Colour colour) {
       boardMap.clear();
       ChessPiece bishop = new Bishop(colour);
   }

   /**
    * expecting false.
    *
    * @param piece Piece to be placed on the board
    */
   @ParameterizedTest
   @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
   void isLegalMove_bishopTakesItsColourPiece_False(ChessPiece piece) {
       ChessPiece bishop = new Bishop(piece.getColour());
   }

   /**
    * expecting true.
    *
    * @param piece Piece to be placed on the board
    */
   @ParameterizedTest
   @MethodSource("com.ccd.chess.test.DataProvider#pieceProvider")
   void isLegalMove_bishopTakesDifferentColourPiece_True(ChessPiece piece) {
   }

   /**
    * expecting valid polygons to be present in the list.
    *
    * @param colour Colour of the bishop
    */
       boardMap.clear();
   }

   /**
    * Tests that bishop can move across board sections properly
    */
   @Test
       boardMap.clear();
       ChessPiece bishop = new Bishop(Colour.BLUE);
   }

   /**
    * Parameterized test for toString method,
    * expecting correct string format for bishop initialization.
    * BB: blue Bishop
    * GB: green Bishop
    * RB: red Bishop
    *
    * @param colour Colour of the bishop
    */
   @ParameterizedTest
   @EnumSource(Colour.class)
   void toString_initBishopAllColours_correctStringFormat(Colour colour) {
       ChessPiece bishop = new Bishop(colour);
       String expectedFormat = colour.toString() + "B";
       assertEquals(expectedFormat, bishop.toString());
   }
}