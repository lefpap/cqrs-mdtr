package io.github.lefpap.mdtr;

import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;

/**
 * Defines a mediator for dispatching commands and sending queries within a CQRS
 * architecture.
 *
 * @see CqrsCommand
 * @see CqrsQuery
 */
public interface CqrsMediator {
    /**
     * Dispatches the specified command.
     *
     * @param <R>     the type of result expected from handling the command
     * @param <C>     the type of command to be processed
     * @param command the command to dispatch
     * @return the result produced by processing the command
     */
    <R, C extends CqrsCommand<R>> R dispatch(C command);

    /**
     * Sends the specified query.
     *
     * @param <R>   the type of result expected from processing the query
     * @param <Q>   the type of query to be processed
     * @param query the query to send
     * @return the result produced by processing the query
     */
    <R, Q extends CqrsQuery<R>> R send(Q query);
}
