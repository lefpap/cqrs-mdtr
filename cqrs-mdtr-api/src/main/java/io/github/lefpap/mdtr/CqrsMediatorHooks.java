package io.github.lefpap.mdtr;

import io.github.lefpap.mdtr.command.Command;
import io.github.lefpap.mdtr.query.Query;

/**
 * Interface for defining hooks that can be used to intercept and modify the
 * behavior
 * of the CQRS mediator during command dispatching and query sending.
 *
 * <p>
 * Implementations can override the default methods to provide custom behavior
 * before and after dispatching commands or sending queries, as well as handling
 * errors that may occur during these operations.
 * 
 * @see CqrsMediator
 */
public interface CqrsMediatorHooks {

    /**
     * Called before dispatching a command.
     *
     * @param <R>     the type of result expected from handling the command
     * @param <C>     the type of command to be processed
     * @param command the command to dispatch
     */
    default <R, C extends Command<R>> void onBeforeDispatch(C command) {
        // Default implementation does nothing
    }

    /**
     * Called after dispatching a command.
     *
     * @param <R>     the type of result expected from handling the command
     * @param <C>     the type of command to be processed
     * @param command the command that was dispatched
     * @param result  the result produced by processing the command
     */
    default <R, C extends Command<R>> void onAfterDispatch(C command, R result) {
        // Default implementation does nothing
    }

    /**
     * Called before sending a query.
     *
     * @param <R>   the type of result expected from processing the query
     * @param <Q>   the type of query to be processed
     * @param query the query to send
     */
    default <R, Q extends Query<R>> void onBeforeSend(Q query) {
        // Default implementation does nothing
    }

    /**
     * Called after sending a query.
     *
     * @param <R>    the type of result expected from processing the query
     * @param <Q>    the type of query to be processed
     * @param query  the query that was sent
     * @param result the result produced by processing the query
     */
    default <R, Q extends Query<R>> void onAfterSend(Q query, R result) {
        // Default implementation does nothing
    }

    /**
     * Called when an error occurs during command dispatching.
     *
     * @param <R>     the type of result expected from handling the command
     * @param <C>     the type of command to be processed
     * @param command the command that caused the error
     * @param ex      the exception that occurred
     */
    default <R, C extends Command<R>> void onDispatchError(C command, Throwable ex) {
        // Default implementation does nothing
    }

    /**
     * Called when an error occurs during query sending.
     *
     * @param <R>   the type of result expected from processing the query
     * @param <Q>   the type of query to be processed
     * @param query the query that caused the error
     * @param ex    the exception that occurred
     */
    default <R, Q extends Query<R>> void onSendError(Q query, Throwable ex) {
        // Default implementation does nothing
    }
}
