package com.ccd.chess.model.entity.enums;

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
