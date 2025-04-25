package io.github.lefpap.mdtr;

import io.github.lefpap.mdtr.exception.CqrsRequestHandlerNotFoundException;
import io.github.lefpap.mdtr.middleware.CqrsMiddlewarePipeline;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;
import io.github.lefpap.mdtr.registry.CqrsHandlerRegistry;

import java.util.Optional;

/**
 * Default implementation of the {@link CqrsMediator} interface.
 *
 * <p>This class is responsible for dispatching commands and sending queries to their respective handlers.
 * It uses a {@link CqrsHandlerRegistry} to look up the appropriate handler for each command or query.
 */
public class DefaultCqrsMediator implements CqrsMediator {

    private final CqrsHandlerRegistry registry;
    private final CqrsMiddlewarePipeline pipeline;

    /**
     * Constructs a new {@code DefaultCqrsMediator} with the specified handler registry.
     *
     * @param registry the registry containing command and query handlers
     */
    public DefaultCqrsMediator(CqrsHandlerRegistry registry, CqrsMiddlewarePipeline pipeline) {
        this.registry = registry;
        this.pipeline = pipeline;
    }

    /**
     * Constructs a new {@code DefaultCqrsMediator} with the specified handler registry.
     *
     * @param registry the registry containing command and query handlers
     */
    public DefaultCqrsMediator(CqrsHandlerRegistry registry) {
        this.registry = registry;
        this.pipeline = null;
    }

    /**
     * @throws CqrsRequestHandlerNotFoundException if no handler is found for the command
     */
    @Override
    public <R, C extends CqrsCommand<R>> R dispatch(C command) {
        //noinspection unchecked
        var handler = registry.getCommandHandler((Class<C>) command.getClass())
                .orElseThrow(() -> new CqrsRequestHandlerNotFoundException("No handler found for command: " + command.getClass().getName()));

        return Optional.ofNullable(pipeline)
                .map(p -> p.execute(command, handler))
                .orElseGet(() -> handler.handle(command));
    }

    /**
     * @throws CqrsRequestHandlerNotFoundException if no handler is found for the query
     */
    @Override
    public <R, Q extends CqrsQuery<R>> R send(Q query) {
        //noinspection unchecked
        var handler = registry.getQueryHandler((Class<Q>) query.getClass())
                .orElseThrow(() -> new CqrsRequestHandlerNotFoundException("No handler found for query: " + query.getClass().getName()));

        return Optional.ofNullable(pipeline)
                .map(p -> p.execute(query, handler))
                .orElseGet(() -> handler.handle(query));
    }
}
