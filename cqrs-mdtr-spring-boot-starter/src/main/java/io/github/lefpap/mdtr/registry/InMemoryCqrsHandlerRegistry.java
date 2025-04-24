package io.github.lefpap.mdtr.registry;

import io.github.lefpap.mdtr.exception.RequestHandlerAlreadyRegisteredException;
import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Objects;
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

    /**
     * @throws NullPointerException                     if the command type or handler is null
     * @throws RequestHandlerAlreadyRegisteredException if a handler is already registered for the command type
     */
    @Override
    public <C extends CqrsCommand<R>, R> void registerCommandHandler(@NonNull Class<C> commandType, @NonNull CqrsCommandHandler<C, R> handler) {
        Objects.requireNonNull(commandType, "Command type cannot be null");
        Objects.requireNonNull(handler, "Command handler cannot be null");

        if (commandHandlers.containsKey(commandType)) {
            throw new RequestHandlerAlreadyRegisteredException("Command handler already registered for type: " + commandType.getName());
        }

        commandHandlers.put(commandType, handler);
    }

    /**
     * @throws NullPointerException                     if the query type or handler is {@code null}
     * @throws RequestHandlerAlreadyRegisteredException if a handler is already registered for the query type
     */
    @Override
    public <Q extends CqrsQuery<R>, R> void registerQueryHandler(@NonNull Class<Q> queryType, @NonNull CqrsQueryHandler<Q, R> handler) {
        Objects.requireNonNull(queryType, "Query type cannot be null");
        Objects.requireNonNull(handler, "Query handler cannot be null");

        if (queryHandlers.containsKey(queryType)) {
            throw new RequestHandlerAlreadyRegisteredException("Query handler already registered for type: " + queryType.getName());
        }

        queryHandlers.put(queryType, handler);
    }

    /**
     * @throws NullPointerException if the command type is null
     */
    @Override
    public <C extends CqrsCommand<R>, R> Optional<CqrsCommandHandler<C, R>> getCommandHandler(@NonNull Class<C> commandType) {
        Objects.requireNonNull(commandType, "Command type cannot be null");

        var handler = commandHandlers.get(commandType);
        //noinspection unchecked
        return Optional.ofNullable(handler)
                .map(h -> (CqrsCommandHandler<C, R>) h);
    }

    /**
     * @throws NullPointerException if the query type is null
     */
    @Override
    public <Q extends CqrsQuery<R>, R> Optional<CqrsQueryHandler<Q, R>> getQueryHandler(@NonNull Class<Q> queryType) {
        Objects.requireNonNull(queryType, "Query type cannot be null");

        var handler = queryHandlers.get(queryType);
        //noinspection unchecked
        return Optional.ofNullable(handler)
                .map(h -> (CqrsQueryHandler<Q, R>) h);
    }
}
