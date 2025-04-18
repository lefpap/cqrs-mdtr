package com.github.lefpap.mdtr;

import com.github.lefpap.mdtr.command.Command;
import com.github.lefpap.mdtr.exception.HandlerNotFoundException;
import com.github.lefpap.mdtr.query.Query;
import com.github.lefpap.mdtr.registry.HandlerRegistry;

/**
 * Default implementation of the CqrsMediator interface, responsible for
 * dispatching commands, sending queries, and invoking lifecycle hooks during
 * these operations.
 *
 * @see CqrsMediator
 * @see CqrsMediatorHooks
 * @see Command
 * @see Query
 */
public class DefaultCqrsMediator implements CqrsMediator, CqrsMediatorHooks {

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
        if (command == null) {
            throw new IllegalArgumentException("Cannot dispatch a null command");
        }

        try {
            // Call the before dispatch hook.
            onBeforeDispatch(command);

            // Look up the command handler from the registry.
            @SuppressWarnings("unchecked")
            var result = registry.<C, R>getCommandHandler((Class<C>) command.getClass())
                    .map(handler -> handler.handle(command))
                    .orElseThrow(
                            () -> new HandlerNotFoundException(
                                    "No command handler registered for " + command.getClass()));

            // Call the after dispatch hook.
            onAfterDispatch(command, result);

            return result;
        } catch (Exception e) {
            // Call the error dispatch hook.
            onDispatchError(command, e);
            throw e;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, Q extends Query<R>> R send(Q query) {
        if (query == null) {
            throw new IllegalArgumentException("Cannot send a null query");
        }

        try {
            // Call the before send hook.
            onBeforeSend(query);

            // Retrieve the query handler from the registry.
            var result = registry.<Q, R>getQueryHandler((Class<Q>) query.getClass())
                    .map(handler -> handler.handle(query))
                    .orElseThrow(
                            () -> new HandlerNotFoundException("No query handler registered for " + query.getClass()));

            // Call the after send hook.
            onAfterSend(query, result);

            return result;
        } catch (Exception ex) {
            // Call the error send hook.
            onSendError(query, ex);
            throw ex;
        }
    }
}