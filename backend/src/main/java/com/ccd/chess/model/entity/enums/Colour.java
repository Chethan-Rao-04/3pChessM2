package com.ccd.chess.model.entity.enums;

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
