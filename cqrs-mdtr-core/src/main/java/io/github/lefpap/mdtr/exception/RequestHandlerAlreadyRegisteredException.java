package io.github.lefpap.mdtr.exception;

public class RequestHandlerAlreadyRegisteredException extends RuntimeException {
    public RequestHandlerAlreadyRegisteredException(String message) {
        super(message);
    }
}
