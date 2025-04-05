package com.github.lefpap.mdtr;


import com.github.lefpap.mdtr.exception.HandlerNotFoundException;
import com.github.lefpap.mdtr.handler.CommandHandler;
import com.github.lefpap.mdtr.handler.QueryHandler;
import com.github.lefpap.mdtr.message.Command;
import com.github.lefpap.mdtr.message.Query;
import com.github.lefpap.mdtr.registry.HandlerRegistry;

/**
 * Default implementation of the {@link CqrsMediator} interface.
 * <p>
 * This mediator is responsible for dispatching commands and sending queries
 * to their respective handlers using a {@link HandlerRegistry}.
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
    public <R, C extends Command<R>> R dispatch(C command) {
        // Look up the command handler.
        CommandHandler<C, R> handler = registry.<C, R>getCommandHandler(command)
            .orElseThrow(() -> new HandlerNotFoundException("No command handler registered for " + command.getClass()));

        return handler.handle(command);
    }


    @Override
    public <R, Q extends Query<R>> R send(Q query) {
        // Look up the query handler.
        QueryHandler<Q, R> handler = registry.<Q, R>getQueryHandler(query)
            .orElseThrow(() -> new HandlerNotFoundException("No query handler registered for " + query.getClass()));
        return handler.handle(query);
    }

}
