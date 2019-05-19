package me.vitblokhin.jwtappdemo.exception;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException() {
    }

    public ItemAlreadyExistsException(String message) {
        super(message);
    }
} // class ItemAlreadyExistsException
