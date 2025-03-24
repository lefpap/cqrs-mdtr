package org.github.lefpap.mdtr.handler;


import org.github.lefpap.mdtr.message.Command;
import org.github.lefpap.mdtr.message.CommandContext;


/**
 * Handles a command that requires additional contextual information.
 *
 * @param <C>   the type of command to be processed
 * @param <CTX> the type of context required to process the command
 * @param <R>   the type of result produced by handling the command
 */
public interface ContextualCommandHandler<C extends Command, CTX extends CommandContext<C>, R> {

    /**
     * Handles the given command using the provided context and returns a result.
     *
     * @param command the command to handle
     * @param ctx     the context containing additional information required for processing
     * @return the result of processing the command
     */
    R handle(C command, CTX ctx);
}
