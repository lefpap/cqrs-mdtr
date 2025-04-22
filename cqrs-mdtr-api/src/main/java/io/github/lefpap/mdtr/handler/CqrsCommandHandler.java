package io.github.lefpap.mdtr.handler;

import io.github.lefpap.mdtr.request.CqrsCommand;

/**
 * Handles a command by processing it and returning a result.
 *
 * @param <C> the type of command to be processed
 * @param <R> the type of result produced by handling the command
 */
public interface CqrsCommandHandler<C extends CqrsCommand<R>, R> {

    /**
     * Handles the given command and returns a result.
     *
     * @param command the command to handle
     * @return the result of processing the command
     */
    R handle(C command);

    /**
     * Returns the class type of the command handled by this handler.
     *
     * @return the class type of the command
     */
    Class<C> getCommandType();

    /**
     * Checks if this handler supports the given command type.
     *
     * @param commandType the class type of the command
     * @return true if this handler supports the command type, false otherwise
     */
    default boolean supports(Class<? extends CqrsCommand<?>> commandType) {
        return getCommandType().isAssignableFrom(commandType);
    }
}
