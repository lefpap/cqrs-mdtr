package com.github.lefpap.mdtr.registry;

import com.github.lefpap.mdtr.handler.CommandHandler;
import com.github.lefpap.mdtr.handler.QueryHandler;
import com.github.lefpap.mdtr.message.Command;
import com.github.lefpap.mdtr.message.Query;

import java.util.Optional;


/**
 * A registry for registering and retrieving command and query handlers.
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
     * Registers a query handler for a specific query type.
     *
     * @param <Q>       the type of query handled
     * @param <R>       the type of result produced by the handler
     * @param queryType the class of the query to be handled
     * @param handler   the query handler that will process the query
     */
    <Q extends Query, R>
    void registerQueryHandler(Class<Q> queryType, QueryHandler<Q, R> handler);

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
     * Retrieves a registered query handler for the given query.
     *
     * @param <Q>   the type of query
     * @param <R>   the type of result expected from the handler
     * @param query the query for which a handler is sought
     * @return an {@link Optional} containing the matching query handler if one is registered, or an empty Optional otherwise
     */
    <Q extends Query, R> Optional<QueryHandler<Q, R>> getQueryHandler(Q query);
}

