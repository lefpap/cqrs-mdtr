package com.github.lefpap.mdtr.registry;

import java.util.Optional;
import java.util.ServiceLoader;

import com.github.lefpap.mdtr.command.Command;
import com.github.lefpap.mdtr.command.CommandHandler;
import com.github.lefpap.mdtr.query.Query;
import com.github.lefpap.mdtr.query.QueryHandler;

public class ServiceLoaderHandlerRegistry implements HandlerRegistry {

    @SuppressWarnings("unchecked")
    public ServiceLoaderHandlerRegistry() {
        var commandLoader = ServiceLoader.load(CommandHandler.class);
        commandLoader.forEach(handler -> registerCommandHandler(handler.supportedCommand(), handler));

        var queryLoader = ServiceLoader.load(QueryHandler.class);
        queryLoader.forEach(handler -> registerQueryHandler(handler.supportedQuery(), handler));
    }

    @Override
    public <C extends Command<R>, R> Optional<CommandHandler<C, R>> getCommandHandler(C arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommandHandler'");
    }

    @Override
    public <Q extends Query<R>, R> Optional<QueryHandler<Q, R>> getQueryHandler(Q arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQueryHandler'");
    }

    @Override
    public <C extends Command<R>, R> void registerCommandHandler(Class<C> arg0, CommandHandler<C, R> arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerCommandHandler'");
    }

    @Override
    public <Q extends Query<R>, R> void registerQueryHandler(Class<Q> arg0, QueryHandler<Q, R> arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerQueryHandler'");
    }

    @Override
    public <C extends Command<R>, R> void unregisterCommandHandler(Class<C> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unregisterCommandHandler'");
    }

    @Override
    public <Q extends Query<R>, R> void unregisterQueryHandler(Class<Q> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unregisterQueryHandler'");
    }

}
