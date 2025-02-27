package com.ccd.chess.model.entity.enums;

import com.ccd.chess.exceptions.InvalidPositionException;

/**
 * Enum to represent the positions on the board
 */

public enum PositionOnBoard{

    BA1(Colour.SILVER,0,0), BA2(Colour.SILVER,1,0), BA3(Colour.SILVER,2,0), BA4(Colour.SILVER,3,0),
    BB1(Colour.SILVER,0,1), BB2(Colour.SILVER,1,1), BB3(Colour.SILVER,2,1), BB4(Colour.SILVER,3,1),
    BC1(Colour.SILVER,0,2), BC2(Colour.SILVER,1,2), BC3(Colour.SILVER,2,2), BC4(Colour.SILVER,3,2),
    BD1(Colour.SILVER,0,3), BD2(Colour.SILVER,1,3), BD3(Colour.SILVER,2,3), BD4(Colour.SILVER,3,3),
    BE1(Colour.SILVER,0,4), BE2(Colour.SILVER,1,4), BE3(Colour.SILVER,2,4), BE4(Colour.SILVER,3,4),
    BF1(Colour.SILVER,0,5), BF2(Colour.SILVER,1,5), BF3(Colour.SILVER,2,5), BF4(Colour.SILVER,3,5),
    BG1(Colour.SILVER,0,6), BG2(Colour.SILVER,1,6), BG3(Colour.SILVER,2,6), BG4(Colour.SILVER,3,6),
    BH1(Colour.SILVER,0,7), BH2(Colour.SILVER,1,7), BH3(Colour.SILVER,2,7), BH4(Colour.SILVER,3,7),

    GA1(Colour.BRONZE,0,0), GA2(Colour.BRONZE,1,0), GA3(Colour.BRONZE,2,0), GA4(Colour.BRONZE,3,0),
    GB1(Colour.BRONZE,0,1), GB2(Colour.BRONZE,1,1), GB3(Colour.BRONZE,2,1), GB4(Colour.BRONZE,3,1),
    GC1(Colour.BRONZE,0,2), GC2(Colour.BRONZE,1,2), GC3(Colour.BRONZE,2,2), GC4(Colour.BRONZE,3,2),
    GD1(Colour.BRONZE,0,3), GD2(Colour.BRONZE,1,3), GD3(Colour.BRONZE,2,3), GD4(Colour.BRONZE,3,3),
    GE1(Colour.BRONZE,0,4), GE2(Colour.BRONZE,1,4), GE3(Colour.BRONZE,2,4), GE4(Colour.BRONZE,3,4),
    GF1(Colour.BRONZE,0,5), GF2(Colour.BRONZE,1,5), GF3(Colour.BRONZE,2,5), GF4(Colour.BRONZE,3,5),
    GG1(Colour.BRONZE,0,6), GG2(Colour.BRONZE,1,6), GG3(Colour.BRONZE,2,6), GG4(Colour.BRONZE,3,6),
    GH1(Colour.BRONZE,0,7), GH2(Colour.BRONZE,1,7), GH3(Colour.BRONZE,2,7), GH4(Colour.BRONZE,3,7),

    RA1(Colour.GOLD,0,0), RA2(Colour.GOLD,1,0), RA3(Colour.GOLD,2,0), RA4(Colour.GOLD,3,0),
    RB1(Colour.GOLD,0,1), RB2(Colour.GOLD,1,1), RB3(Colour.GOLD,2,1), RB4(Colour.GOLD,3,1),
    RC1(Colour.GOLD,0,2), RC2(Colour.GOLD,1,2), RC3(Colour.GOLD,2,2), RC4(Colour.GOLD,3,2),
    RD1(Colour.GOLD,0,3), RD2(Colour.GOLD,1,3), RD3(Colour.GOLD,2,3), RD4(Colour.GOLD,3,3),
    RE1(Colour.GOLD,0,4), RE2(Colour.GOLD,1,4), RE3(Colour.GOLD,2,4), RE4(Colour.GOLD,3,4),
    RF1(Colour.GOLD,0,5), RF2(Colour.GOLD,1,5), RF3(Colour.GOLD,2,5), RF4(Colour.GOLD,3,5),
    RG1(Colour.GOLD,0,6), RG2(Colour.GOLD,1,6), RG3(Colour.GOLD,2,6), RG4(Colour.GOLD,3,6),
    RH1(Colour.GOLD,0,7), RH2(Colour.GOLD,1,7), RH3(Colour.GOLD,2,7), RH4(Colour.GOLD,3,7);

    private final Colour colour; //silver red bronze
    private final int row; //0-3
    private final int column; //0-7

    /**
     * Position enum constructor
     **/
    PositionOnBoard(Colour colour, int row, int column){
        this.colour = colour; this.row = row; this.column = column;
    }

    /**
     * Method to get the colour
     * @return Colour
     **/
    public Colour getColour(){return colour;}

    /**
     * Method to get the Row number
     * @return int row number
     **/
    public int getRow(){return row;}

    /**
     * Method to get the column number
     * @return int column number
     **/
    public int getColumn(){return column;}

    /**
     * Method to get the string representation of the enum
     * @return string representation of the polygon ID
     **/
    @Override
    public String toString() {
        return colour.toString()+getColumnChar(column)+(row+1);
    }

    /**
     * Gets the position corresponding to the specified colour, row and column.
     * @return the position of the specified colour, row and column
     * @throws InvalidPositionException if outside the bounds of the board.
     **/
    public static PositionOnBoard get(Colour colour, int row, int column) throws InvalidPositionException {
        int index= row+4*column;
        if(index>=0 && index<32){
            switch(colour){
                case SILVER: return PositionOnBoard.values()[index];
                case BRONZE: return PositionOnBoard.values()[index+32];
                case GOLD: return PositionOnBoard.values()[index+64];
            }
        }
        throw new InvalidPositionException("No such position.");
    }

    /**
     * Gets the position corresponding to the specified colour, row and column.
     * @return the position of the specified polygon Index
     * @throws InvalidPositionException if outside the bounds of the board.
     **/
    public static PositionOnBoard get(int polygonIndex) throws InvalidPositionException {
        if(polygonIndex >=0 && polygonIndex <=95) {
            return PositionOnBoard.values()[polygonIndex];
        }
        throw new InvalidPositionException("No such position.");
    }

    /**
     * Get the position of next neighbour after a calculateNextPosition
     * @param direction direction input to get its neighbour
     * @return Position the position of the specified polygon Index
     * @throws InvalidPositionException if outside the bounds of the board.
     **/
    public PositionOnBoard neighbour(Direction direction) throws InvalidPositionException {
        switch(direction){
            case FORWARD:
                if(row<3) {
                    return get(colour, row+1, column);
                }
                if(column<4) {
                    return get(Colour.values()[(colour.ordinal()+1)%3], 3, 7-column);
                }
                return get(Colour.values()[(colour.ordinal()+2)%3],3,7-column);
            case BACKWARD:
                if(row==0) {
                    throw new InvalidPositionException("Moved off board");
                }
                return get(colour,row-1,column);
            case LEFT:
                if(column==0) {
                    throw new InvalidPositionException("Moved off board");
                }
                return get(colour,row,column-1);
            case RIGHT:
                if(column==7) {
                    throw new InvalidPositionException("Moved off board");
                }
                return get(colour,row,column+1);
        }
        throw new InvalidPositionException("Unreachable code?");
    }

    /**
     * Given the column input, fetch the character linked
     * @param column direction input to get its neighbour
     * @return char
     **/
    private char getColumnChar(int column) {
        switch (column) {
            case 0: return 'a';
            case 1: return 'b';
            case 2: return 'c';
            case 3: return 'd';
            case 4: return 'e';
            case 5: return 'f';
            case 6: return 'g';
            case 7: return 'h';
            default: return '\0';
        }
    }
}