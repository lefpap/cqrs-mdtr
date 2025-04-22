package io.github.lefpap.mdtr.registry;

import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;

import java.util.Optional;

/**
 * Registry for command and query handlers.
 */
public interface CqrsHandlerRegistry {

    /**
     * Registers a command handler for a specific command type.
     *
     * @param commandType the command type
     * @param handler     the command handler
     * @param <C>         the command type
     * @param <R>         the result type
     */
    <C extends CqrsCommand<R>, R> void registerCommandHandler(Class<C> commandType, CqrsCommandHandler<C, R> handler);

    /**
     * Registers a query handler for a specific query type.
     *
     * @param queryType the query type
     * @param handler   the query handler
     * @param <Q>       the query type
     * @param <R>       the result type
     */
    <Q extends CqrsQuery<R>, R> void registerQueryHandler(Class<Q> queryType, CqrsQueryHandler<Q, R> handler);

    /**
     * Retrieves a command handler for a specific command type.
     *
     * @param commandType the command type
     * @param <C>         the command type
     * @param <R>         the result type
     * @return an optional containing the command handler if found, otherwise empty
     */
    <C extends CqrsCommand<R>, R> Optional<CqrsCommandHandler<C, R>> getCommandHandler(Class<C> commandType);

    /**
     * Retrieves a query handler for a specific query type.
     *
     * @param queryType the query type
     * @param <Q>       the query type
     * @param <R>       the result type
     * @return an optional containing the query handler if found, otherwise empty
     */
    <Q extends CqrsQuery<R>, R> Optional<CqrsQueryHandler<Q, R>> getQueryHandler(Class<Q> queryType);
}
