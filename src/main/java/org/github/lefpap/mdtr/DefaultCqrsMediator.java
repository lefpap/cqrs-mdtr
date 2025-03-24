package org.github.lefpap.mdtr;


import org.github.lefpap.mdtr.exception.HandlerNotFoundException;
import org.github.lefpap.mdtr.handler.CommandHandler;
import org.github.lefpap.mdtr.handler.QueryHandler;
import org.github.lefpap.mdtr.message.Command;
import org.github.lefpap.mdtr.message.CommandContext;
import org.github.lefpap.mdtr.message.Query;
import org.github.lefpap.mdtr.message.QueryContext;
import org.github.lefpap.mdtr.registry.HandlerRegistry;

/**
 * Default implementation of the {@link CqrsMediator} interface.
 * <p>
 * This mediator is responsible for dispatching commands and sending queries
 * to their respective handlers using a {@link HandlerRegistry}.
 * It supports both contextual and non-contextual handling. When a contextual
 * handler is not available for a given command or query, it falls back to the
 * non-contextual handler.
 * </p>
 *
 * @see CqrsMediator
 * @see HandlerRegistry
 * @see HandlerNotFoundException
 */
public class DefaultCqrsMediator implements CqrsMediator {
    private final HandlerRegistry registry;

    /**
     * Constructs a new DefaultCqrsMediator with the specified handler registry.
     *
     * @param registry the registry to use for looking up command and query handlers
     */
    public DefaultCqrsMediator(HandlerRegistry registry) {
        this.registry = registry;
    }

    @Override
    public <R, C extends Command> R dispatch(C command) {
        // Look up the non-contextual command handler.
        CommandHandler<C, R> handler = registry.<C, R>getCommandHandler(command)
            .orElseThrow(() -> new HandlerNotFoundException("No non-contextual command handler registered for " + command.getClass()));

        return handler.handle(command);
    }

    @Override
    public <R, C extends Command, CTX extends CommandContext<C>> R dispatch(C command, CTX ctx) {
        // Prefer a contextual handler if registered.
        return registry.<C, CTX, R>getContextualCommandHandler(command)
            .map(handler -> handler.handle(command, ctx))
            // Otherwise, fall back to the non-contextual handler.
            .orElseGet(() -> registry.<C, R>getCommandHandler(command)
                .orElseThrow(() -> new HandlerNotFoundException("No command handler registered for " + command.getClass()))
                .handle(command));
    }

    @Override
    public <R, Q extends Query> R send(Q query) {
        // Look up the non-contextual query handler.
        QueryHandler<Q, R> handler = registry.<Q, R>getQueryHandler(query)
            .orElseThrow(() -> new HandlerNotFoundException("No non-contextual query handler registered for " + query.getClass()));
        return handler.handle(query);
    }

    @Override
    public <R, Q extends Query, CTX extends QueryContext<Q>> R send(Q query, CTX ctx) {
        // Prefer a contextual query handler if registered.
        return registry.<Q, CTX, R>getContextualQueryHandler(query)
            .map(handler -> handler.handle(query, ctx))
            // Otherwise, fall back to the non-contextual handler.
            .orElseGet(() -> registry.<Q, R>getQueryHandler(query)
                .orElseThrow(() -> new HandlerNotFoundException("No query handler registered for " + query.getClass()))
                .handle(query));
    }
}
