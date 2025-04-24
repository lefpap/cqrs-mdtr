package io.github.lefpap.mdtr.registry;

import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;
import org.springframework.lang.NonNull;

import java.util.Map;
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
    <C extends CqrsCommand<R>, R> void registerCommandHandler(@NonNull Class<C> commandType, @NonNull CqrsCommandHandler<C, R> handler);

    /**
     * Registers multiple command handlers.
     *
     * @param handlersMap a map of command types to their corresponding handlers
     * @param <C>         the command type
     * @param <R>         the result type
     */
    default <C extends CqrsCommand<R>, R> void registerCommandHandlers(@NonNull Map<Class<C>, CqrsCommandHandler<C, R>> handlersMap) {
        handlersMap.forEach(this::registerCommandHandler);
    }

    /**
     * Registers a query handler for a specific query type.
     *
     * @param queryType the query type
     * @param handler   the query handler
     * @param <Q>       the query type
     * @param <R>       the result type
     */
    <Q extends CqrsQuery<R>, R> void registerQueryHandler(@NonNull Class<Q> queryType, @NonNull CqrsQueryHandler<Q, R> handler);

    /**
     * Registers multiple query handlers.
     *
     * @param handlersMap a map of query types to their corresponding handlers
     * @param <Q>         the query type
     * @param <R>         the result type
     */
    default <Q extends CqrsQuery<R>, R> void registerQueryHandlers(@NonNull Map<Class<Q>, CqrsQueryHandler<Q, R>> handlersMap) {
        handlersMap.forEach(this::registerQueryHandler);
    }

    /**
     * Retrieves a command handler for a specific command type.
     *
     * @param commandType the command type
     * @param <C>         the command type
     * @param <R>         the result type
     * @return an optional containing the command handler if found, otherwise empty
     */
    <C extends CqrsCommand<R>, R> Optional<CqrsCommandHandler<C, R>> getCommandHandler(@NonNull Class<C> commandType);

    /**
     * Retrieves a query handler for a specific query type.
     *
     * @param queryType the query type
     * @param <Q>       the query type
     * @param <R>       the result type
     * @return an optional containing the query handler if found, otherwise empty
     */
    <Q extends CqrsQuery<R>, R> Optional<CqrsQueryHandler<Q, R>> getQueryHandler(@NonNull Class<Q> queryType);
}
