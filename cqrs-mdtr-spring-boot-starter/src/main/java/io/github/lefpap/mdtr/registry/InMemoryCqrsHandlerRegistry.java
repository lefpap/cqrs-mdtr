package io.github.lefpap.mdtr.registry;

import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe in-memory implementation of the \`CqrsHandlerRegistry\` interface.
 * Provides efficient registration and retrieval of command and query handlers.
 *
 * @see CqrsCommand
 * @see CqrsQuery
 */
public class InMemoryCqrsHandlerRegistry implements CqrsHandlerRegistry {

    private final Map<Class<?>, CqrsCommandHandler<?, ?>> commandHandlers = new ConcurrentHashMap<>();
    private final Map<Class<?>, CqrsQueryHandler<?, ?>> queryHandlers = new ConcurrentHashMap<>();

    @Override
    public <C extends CqrsCommand<R>, R> void registerCommandHandler(Class<C> commandType, CqrsCommandHandler<C, R> handler) {
        commandHandlers.put(commandType, handler);
    }

    @Override
    public <Q extends CqrsQuery<R>, R> void registerQueryHandler(Class<Q> queryType, CqrsQueryHandler<Q, R> handler) {
        queryHandlers.put(queryType, handler);
    }

    @Override
    public <C extends CqrsCommand<R>, R> Optional<CqrsCommandHandler<C, R>> getCommandHandler(Class<C> commandType) {
        //noinspection unchecked
        return Optional.ofNullable(commandHandlers.get(commandType))
                .map(handler -> (CqrsCommandHandler<C, R>) handler);
    }

    @Override
    public <Q extends CqrsQuery<R>, R> Optional<CqrsQueryHandler<Q, R>> getQueryHandler(Class<Q> queryType) {
        //noinspection unchecked
        return Optional.ofNullable(queryHandlers.get(queryType))
                .map(handler -> (CqrsQueryHandler<Q, R>) handler);
    }
}
