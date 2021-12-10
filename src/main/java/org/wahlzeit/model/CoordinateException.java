package org.wahlzeit.model;

public class CoordinateException extends Exception{

    public CoordinateException(String message) {
        super(message);
    }

    public CoordinateException(String message, Throwable reason) {
        super(message, reason);
    }

    public CoordinateException(ArithmeticException exception) {
        super("An ArithmeticException occurred!", exception);
    }

}
