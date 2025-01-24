package com.ccd.chess.utility;

import com.ccd.chess.exceptions.InvalidPositionException;
import com.ccd.chess.entity.ChessPiece;
import com.ccd.chess.entity.enums.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  Class BoardAdapter to convert information for web app representable form
 **/
public class BoardAdapter {

    /**
     *  Method to convert board data to Map of Strings for webapp
     * @param modelBoard a map of position and piece as input
     * @return Map of String and String
     **/
    public static Map<String, String> convertModelBoardToViewBoard(Map<Position, ChessPiece> modelBoard) {
        Map<String, String> viewBoard = new HashMap<>();

        for(Position position: modelBoard.keySet()) {
            ChessPiece piece = modelBoard.get(position);
            if(piece != null) {
                viewBoard.put(position.toString(), piece.toString());
            }
        }

        return  viewBoard;
    }

    /**
     *  Method to convert list of positions to highlight to list of strings
     * @param possibleMoves a list of positions to highlight
     * @return list of strings
     **/
    public static List<String> convertHighlightPolygonsToViewBoard(Set<Position> possibleMoves) {
        List<String> moves = new ArrayList<>();
        if(possibleMoves == null) {
            return Collections.emptyList();
        }
        for(Position pi: possibleMoves) {
            moves.add(pi.toString());
        }

        return moves;
    }


    /**
     * Calculates unique ID for each polygon based on label
     * @param  polygon The unique label of the polygon which is clicked by player
     * @return unique ID
     * */
    public static int calculatePolygonId(String polygon) throws InvalidPositionException {

        if(polygon==null || polygon.length() != 3 || !Character.isAlphabetic(polygon.charAt(0))
                || !Character.isAlphabetic(polygon.charAt(1)) || !Character.isDigit(polygon.charAt(2))) {
            throw new InvalidPositionException("Invalid String position: "+polygon);
        }

        char firstChar = Character.toLowerCase(polygon.charAt(0));
        char secondChar = Character.toLowerCase(polygon.charAt(1));
        int number = polygon.charAt(2)-'0';
        Logger.d("BoardAdapter", "firstChar: "+firstChar+", secondChar: "+secondChar+", number: "+number);
        boolean isFirstCharInvalid = (firstChar != 'g' && firstChar != 'r' && firstChar != 'b');
        boolean isSecondCharInvalid = (secondChar < 'a' || secondChar > 'h');
        boolean isNumberOutOfRange = (number < 1 || number > 4);
        if(isFirstCharInvalid || isSecondCharInvalid || isNumberOutOfRange) {
            throw new InvalidPositionException("Invalid String position: "+polygon);
        }

        char color = polygon.charAt(0);
        int y = polygon.charAt(1) - 'a';
        int x = polygon.charAt(2) - '1';
        int offset = 0;
        if(color == 'G'){
            offset = 32;
        }else if(color == 'R'){
            offset = 64;
        }
        return offset + x + 4*y;
    }
}
