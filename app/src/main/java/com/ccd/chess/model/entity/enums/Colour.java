package com.ccd.chess.model.entity.enums;

/**
 * Enum to represent the colours of the pieces
 * Currently changed in frontend as Golden, Silver and Bronze
 * But the backend still uses the old colours
 */
public enum Colour {

    SILVER, BRONZE, GOLD;

    public Colour next() {
        switch (this) {
            case SILVER: return BRONZE;
            case BRONZE: return GOLD;
            default: return SILVER;
        }
    }
    @Override
    public String toString() {
        switch (this) {
            case SILVER: return "B";
            case BRONZE: return "G";
            default: return "R";
        }
    }
}
