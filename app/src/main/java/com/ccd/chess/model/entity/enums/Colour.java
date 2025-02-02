package com.ccd.chess.model.entity.enums;

/**
 * Enum to represent the colours of the pieces
 * Currently changed in frontend as Golden, Silver and Bronze
 * But the backend still uses the old colours
 */
public enum Colour {

    BLUE, GREEN, RED;

    public Colour next() {
        switch (this) {
            case BLUE: return GREEN;
            case GREEN: return RED;
            default: return BLUE;
        }
    }
    @Override
    public String toString() {
        switch (this) {
            case BLUE: return "B";
            case GREEN: return "G";
            default: return "R";
        }
    }
}
