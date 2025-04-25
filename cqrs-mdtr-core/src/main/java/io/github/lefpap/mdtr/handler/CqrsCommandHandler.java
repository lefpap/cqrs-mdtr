package io.github.lefpap.mdtr.handler;

import io.github.lefpap.mdtr.request.CqrsCommand;

/**
 * Handles a command by processing it and returning a result.
 *
 * @param <C> the type of command to be processed
 * @param <R> the type of result produced by handling the command
 */
public interface CqrsCommandHandler<C extends CqrsCommand<R>, R> extends CqrsRequestHandler<C, R> {
}
