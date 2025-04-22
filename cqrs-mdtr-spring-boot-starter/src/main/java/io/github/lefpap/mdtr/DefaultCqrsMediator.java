package io.github.lefpap.mdtr;

import io.github.lefpap.mdtr.exception.CqrsRequestHandlerNotFoundException;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;
import io.github.lefpap.mdtr.registry.CqrsHandlerRegistry;

/**
 * Default implementation of the {@link CqrsMediator} interface.
 *
 * <p>This class is responsible for dispatching commands and sending queries to their respective handlers.
 * It uses a {@link CqrsHandlerRegistry} to look up the appropriate handler for each command or query.
 */
public class DefaultCqrsMediator implements CqrsMediator {

    private final CqrsHandlerRegistry registry;

    /**
     * Constructs a new {@code DefaultCqrsMediator} with the specified handler registry.
     *
     * @param registry the registry containing command and query handlers
     */
    public DefaultCqrsMediator(CqrsHandlerRegistry registry) {
        this.registry = registry;
    }

    /**
     * @throws CqrsRequestHandlerNotFoundException if no handler is found for the command
     */
    @Override
    public <R, C extends CqrsCommand<R>> R dispatch(C command) {
        //noinspection unchecked
        return registry.getCommandHandler((Class<C>) command.getClass())
                .map(handler -> handler.handle(command))
                .orElseThrow(() -> new CqrsRequestHandlerNotFoundException("No handler found for command: " + command.getClass().getName()));
    }

    /**
     * @throws CqrsRequestHandlerNotFoundException if no handler is found for the query
     */
    @Override
    public <R, Q extends CqrsQuery<R>> R send(Q query) {
        //noinspection unchecked
        return registry.getQueryHandler((Class<Q>) query.getClass())
                .map(handler -> handler.handle(query))
                .orElseThrow(() -> new CqrsRequestHandlerNotFoundException("No handler found for query: " + query.getClass().getName()));
    }
}
