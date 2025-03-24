package org.github.lefpap.mdtr.registry;

import org.github.lefpap.mdtr.handler.CommandHandler;
import org.github.lefpap.mdtr.handler.ContextualCommandHandler;
import org.github.lefpap.mdtr.handler.ContextualQueryHandler;
import org.github.lefpap.mdtr.handler.QueryHandler;
import org.github.lefpap.mdtr.message.Command;
import org.github.lefpap.mdtr.message.CommandContext;
import org.github.lefpap.mdtr.message.Query;
import org.github.lefpap.mdtr.message.QueryContext;

import java.util.Optional;


/**
 * A registry for registering and retrieving command and query handlers.
 * <p>
 * This interface provides overloaded methods for registering both contextual and non-contextual
 * handlers, as well as lookup methods to retrieve the appropriate handler for a given command or query.
 * </p>
 */
public interface HandlerRegistry {

    // ---------------------- Registration Methods ----------------------

    /**
     * Registers a command handler for a specific command type.
     *
     * @param <C>         the type of command handled
     * @param <R>         the type of result produced by the handler
     * @param commandType the class of the command to be handled
     * @param handler     the command handler that will process the command
     */
    <C extends Command, R>
    void registerCommandHandler(Class<C> commandType, CommandHandler<C, R> handler);

    /**
     * Registers a contextual command handler for a specific command type.
     *
     * @param <C>         the type of command handled
     * @param <CTX>       the type of context required by the command handler
     * @param <R>         the type of result produced by the handler
     * @param commandType the class of the command to be handled
     * @param handler     the contextual command handler that will process the command using additional context
     */
    <C extends Command, CTX extends CommandContext<C>, R>
    void registerCommandHandler(Class<C> commandType, ContextualCommandHandler<C, CTX, R> handler);

    /**
     * Registers a query handler for a specific query type.
     *
     * @param <Q>       the type of query handled
     * @param <R>       the type of result produced by the handler
     * @param queryType the class of the query to be handled
     * @param handler   the query handler that will process the query
     */
    <Q extends Query, R>
    void registerQueryHandler(Class<Q> queryType, QueryHandler<Q, R> handler);

    /**
     * Registers a contextual query handler for a specific query type.
     *
     * @param <Q>       the type of query handled
     * @param <CTX>     the type of context required by the query handler
     * @param <R>       the type of result produced by the handler
     * @param queryType the class of the query to be handled
     * @param handler   the contextual query handler that will process the query using additional context
     */
    <Q extends Query, CTX extends QueryContext<Q>, R>
    void registerQueryHandler(Class<Q> queryType, ContextualQueryHandler<Q, CTX, R> handler);

    // ---------------------- Lookup Methods ----------------------

    /**
     * Retrieves a registered command handler for the given command.
     *
     * @param <C>     the type of command
     * @param <R>     the type of result expected from the handler
     * @param command the command for which a handler is sought
     * @return an {@link Optional} containing the matching command handler if one is registered, or an empty Optional otherwise
     */
    <C extends Command, R> Optional<CommandHandler<C, R>> getCommandHandler(C command);

    /**
     * Retrieves a registered contextual command handler for the given command.
     *
     * @param <C>     the type of command
     * @param <CTX>   the type of context required by the handler
     * @param <R>     the type of result expected from the handler
     * @param command the command for which a contextual handler is sought
     * @return an {@link Optional} containing the matching contextual command handler if one is registered, or an empty Optional otherwise
     */
    <C extends Command, CTX extends CommandContext<C>, R> Optional<ContextualCommandHandler<C, CTX, R>> getContextualCommandHandler(C command);

    /**
     * Retrieves a registered query handler for the given query.
     *
     * @param <Q>   the type of query
     * @param <R>   the type of result expected from the handler
     * @param query the query for which a handler is sought
     * @return an {@link Optional} containing the matching query handler if one is registered, or an empty Optional otherwise
     */
    <Q extends Query, R> Optional<QueryHandler<Q, R>> getQueryHandler(Q query);

    /**
     * Retrieves a registered contextual query handler for the given query.
     *
     * @param <Q>   the type of query
     * @param <CTX> the type of context required by the handler
     * @param <R>   the type of result expected from the handler
     * @param query the query for which a contextual handler is sought
     * @return an {@link Optional} containing the matching contextual query handler if one is registered, or an empty Optional otherwise
     */
    <Q extends Query, CTX extends QueryContext<Q>, R> Optional<ContextualQueryHandler<Q, CTX, R>> getContextualQueryHandler(Q query);

}

