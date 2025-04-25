package io.github.lefpap.mdtr.exception;

/**
 * Exception thrown when no handler is found for a given command or query.
 * <p>
 * This exception is typically used by the mediator when it cannot locate a
 * registered
 * handler for the specified command or query type.
 * </p>
 */
public class CqrsRequestHandlerNotFoundException extends RuntimeException {

    /**
     * Constructs a new HandlerNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public CqrsRequestHandlerNotFoundException(String message) {
        super(message);
    }
}
