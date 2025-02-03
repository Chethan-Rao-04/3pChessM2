package com.ccd.chess.model.entity.enums;
public enum Direction{

    /**
     * Enum to represent the directions of the pieces
     */
    FORWARD,BACKWARD,LEFT,RIGHT;

    @Override
    public String toString() {
        switch (this) {
            case FORWARD: return "FORWARD";
            case BACKWARD: return "BACKWARD";
            case LEFT: return "LEFT";
            default:case RIGHT: return "RIGHT";
        }
    }
}
