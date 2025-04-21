package io.github.lefpap.mdtr.registry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import io.github.lefpap.mdtr.command.Command;
import io.github.lefpap.mdtr.command.CommandHandler;
import io.github.lefpap.mdtr.query.Query;
import io.github.lefpap.mdtr.query.QueryHandler;

/**
 * An in-memory implementation of the {@link HandlerRegistry} interface.
 * 
 * <p>
 * This registry stores command and query handlers in concurrent maps, providing
 * thread-safe registration and lookup of both contextual and non-contextual
 * handlers.
 * The handlers are keyed by the runtime class of the command or query.
 * </p>
 *
 * <p>
 * Note: This implementation uses unchecked casts internally. When registering
 * and retrieving handlers,
 * make sure that the types match to avoid {@link ClassCastException}.
 * </p>
 */
public class InMemoryHandlerRegistry implements HandlerRegistry {

    // Maps for handlers.
    private final Map<Class<? extends Command<?>>, CommandHandler<?, ?>> commandHandlers = new ConcurrentHashMap<>();
    private final Map<Class<? extends Query<?>>, QueryHandler<?, ?>> queryHandlers = new ConcurrentHashMap<>();

    // Overloaded registration for non-contextual command handlers.
    @Override
    public <C extends Command<R>, R> void registerCommandHandler(CommandHandler<C, R> handler) {
        commandHandlers.put(handler.supportedCommand(), handler);
    }

    // Overloaded registration for non-contextual query handlers.
    @Override
    public <Q extends Query<R>, R> void registerQueryHandler(QueryHandler<Q, R> handler) {
        queryHandlers.put(handler.supportedQuery(), handler);
    }

    @Override
    public <C extends Command<R>, R> void unregisterCommandHandler(Class<C> commandType) {
        commandHandlers.remove(commandType);
    }

    @Override
    public <Q extends Query<R>, R> void unregisterQueryHandler(Class<Q> queryType) {
        queryHandlers.remove(queryType);
    }

    // Lookup for non-contextual command handlers.
    @Override
    public <C extends Command<R>, R> Optional<CommandHandler<C, R>> getCommandHandler(Class<C> commandType) {
        @SuppressWarnings("unchecked")
        var commandHandler = (CommandHandler<C, R>) commandHandlers.get(commandType);
        return Optional.ofNullable(commandHandler);
    }

    // Lookup for non-contextual query handlers.
    @Override
    public <Q extends Query<R>, R> Optional<QueryHandler<Q, R>> getQueryHandler(Class<Q> queryType) {
        @SuppressWarnings("unchecked")
        var queryHandler = (QueryHandler<Q, R>) queryHandlers.get(queryType);
        return Optional.ofNullable(queryHandler);
    }
}
