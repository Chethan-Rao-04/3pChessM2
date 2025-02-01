package com.ccd.chess.util;

/**
 * method to print information to debug
 **/
public class Logger{

    /**
     * method to print information to debug
     **/
    public static void d(String tag, Object message) {
        System.out.println(tag+" : " + message);
    }

    /**
     * method to error information
     **/
    public static void e(String tag, Object message) {
        System.err.println(tag+" : " + message);
    }
}