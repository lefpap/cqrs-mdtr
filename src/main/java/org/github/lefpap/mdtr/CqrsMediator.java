package org.github.lefpap.mdtr;


import org.github.lefpap.mdtr.message.Command;
import org.github.lefpap.mdtr.message.CommandContext;
import org.github.lefpap.mdtr.message.Query;
import org.github.lefpap.mdtr.message.QueryContext;

/**
 * Defines a mediator for dispatching commands and sending queries within a CQRS architecture.
 * <p>
 * This interface provides overloaded methods to handle both contextual and non-contextual operations.
 * It decouples the invocation of command and query handlers from their implementation, promoting
 * a clean separation of concerns in your application.
 * </p>
 *
 * @see Command
 * @see CommandContext
 * @see Query
 * @see QueryContext
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
    <R, C extends Command> R dispatch(C command);

    /**
     * Dispatches the specified command along with additional contextual information.
     *
     * @param <R>     the type of result expected from handling the command
     * @param <C>     the type of command to be processed
     * @param <CTX>   the type of context associated with the command
     * @param command the command to dispatch
     * @param ctx     the context containing extra data required for processing the command
     * @return the result produced by processing the command with the provided context
     */
    <R, C extends Command, CTX extends CommandContext<C>> R dispatch(C command, CTX ctx);

    /**
     * Sends the specified query.
     *
     * @param <R>   the type of result expected from processing the query
     * @param <Q>   the type of query to be processed
     * @param query the query to send
     * @return the result produced by processing the query
     */
    <R, Q extends Query> R send(Q query);

    /**
     * Sends the specified query along with additional contextual information.
     *
     * @param <R>   the type of result expected from processing the query
     * @param <Q>   the type of query to be processed
     * @param <CTX> the type of context associated with the query
     * @param query the query to send
     * @param ctx   the context containing extra data required for processing the query
     * @return the result produced by processing the query with the provided context
     */
    <R, Q extends Query, CTX extends QueryContext<Q>> R send(Q query, CTX ctx);
}

