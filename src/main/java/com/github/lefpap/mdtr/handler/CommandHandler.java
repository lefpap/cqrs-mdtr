package com.github.lefpap.mdtr.handler;


import com.github.lefpap.mdtr.message.Command;

/**
 * Handles a command by processing it and returning a result.
 *
 * @param <C> the type of command to be processed
 * @param <R> the type of result produced by handling the command
 */
public interface CommandHandler<C extends Command, R> {

    /**
     * Handles the given command and returns a result.
     *
     * @param command the command to handle
     * @return the result of processing the command
     */
    R handle(C command);
}
