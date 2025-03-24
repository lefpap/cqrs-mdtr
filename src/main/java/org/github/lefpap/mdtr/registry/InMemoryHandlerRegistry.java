package org.github.lefpap.mdtr.registry;

import org.github.lefpap.mdtr.handler.CommandHandler;
import org.github.lefpap.mdtr.handler.ContextualCommandHandler;
import org.github.lefpap.mdtr.handler.ContextualQueryHandler;
import org.github.lefpap.mdtr.handler.QueryHandler;
import org.github.lefpap.mdtr.message.Command;
import org.github.lefpap.mdtr.message.CommandContext;
import org.github.lefpap.mdtr.message.Query;
import org.github.lefpap.mdtr.message.QueryContext;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An in-memory implementation of the {@link HandlerRegistry} interface.
 * <p>
 * This registry stores command and query handlers in concurrent maps, providing
 * thread-safe registration and lookup of both contextual and non-contextual handlers.
 * The handlers are keyed by the runtime class of the command or query.
 * </p>
 *
 * <p>
 * Note: This implementation uses unchecked casts internally. When registering and retrieving handlers,
 * make sure that the types match to avoid {@link ClassCastException}.
 * </p>
 */
public class InMemoryHandlerRegistry implements HandlerRegistry {

    // Maps for non-contextual handlers.
    private final Map<Class<? extends Command>, CommandHandler<?, ?>> commandHandlers = new ConcurrentHashMap<>();
    private final Map<Class<? extends Query>, QueryHandler<?, ?>> queryHandlers = new ConcurrentHashMap<>();

    // Maps for contextual handlers.
    private final Map<Class<? extends Command>, ContextualCommandHandler<?, ?, ?>> contextualCommandHandlers = new ConcurrentHashMap<>();
    private final Map<Class<? extends Query>, ContextualQueryHandler<?, ?, ?>> contextualQueryHandlers = new ConcurrentHashMap<>();

    // Overloaded registration for non-contextual command handlers.
    @Override
    public <C extends Command, R> void registerCommandHandler(Class<C> commandType, CommandHandler<C, R> handler) {
        commandHandlers.put(commandType, handler);
    }

    // Overloaded registration for contextual command handlers.
    @Override
    public <C extends Command, CTX extends CommandContext<C>, R>
    void registerCommandHandler(Class<C> commandType, ContextualCommandHandler<C, CTX, R> handler) {
        contextualCommandHandlers.put(commandType, handler);
    }

    // Overloaded registration for non-contextual query handlers.
    @Override
    public <Q extends Query, R> void registerQueryHandler(Class<Q> queryType, QueryHandler<Q, R> handler) {
        queryHandlers.put(queryType, handler);
    }

    // Overloaded registration for contextual query handlers.
    @Override
    public <Q extends Query, CTX extends QueryContext<Q>, R>
    void registerQueryHandler(Class<Q> queryType, ContextualQueryHandler<Q, CTX, R> handler) {
        contextualQueryHandlers.put(queryType, handler);
    }

    // Lookup for non-contextual command handlers.
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Command, R> Optional<CommandHandler<C, R>> getCommandHandler(C command) {
        return Optional.ofNullable((CommandHandler<C, R>) commandHandlers.get(command.getClass()));
    }

    // Lookup for contextual command handlers.
    @SuppressWarnings("unchecked")
    @Override
    public <C extends Command, CTX extends CommandContext<C>, R>
    Optional<ContextualCommandHandler<C, CTX, R>> getContextualCommandHandler(C command) {
        return Optional.ofNullable((ContextualCommandHandler<C, CTX, R>) contextualCommandHandlers.get(command.getClass()));
    }

    // Lookup for non-contextual query handlers.
    @SuppressWarnings("unchecked")
    @Override
    public <Q extends Query, R> Optional<QueryHandler<Q, R>> getQueryHandler(Q query) {
        return Optional.ofNullable((QueryHandler<Q, R>) queryHandlers.get(query.getClass()));
    }

    // Lookup for contextual query handlers.
    @SuppressWarnings("unchecked")
    @Override
    public <Q extends Query, CTX extends QueryContext<Q>, R>
    Optional<ContextualQueryHandler<Q, CTX, R>> getContextualQueryHandler(Q query) {
        return Optional.ofNullable((ContextualQueryHandler<Q, CTX, R>) contextualQueryHandlers.get(query.getClass()));
    }
}
