package edu.course.lab05;

public final class DataLoadingException extends Exception {

    private static final long serialVersionUID = 1L;

    public DataLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataLoadingException(String message) {
        super(message);
    }
}
