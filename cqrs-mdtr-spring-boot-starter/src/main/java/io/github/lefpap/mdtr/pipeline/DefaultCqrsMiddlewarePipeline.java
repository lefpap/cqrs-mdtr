package io.github.lefpap.mdtr.pipeline;

import io.github.lefpap.mdtr.handler.CqrsRequestHandler;
import io.github.lefpap.mdtr.middleware.CqrsHandlerMiddleware;
import io.github.lefpap.mdtr.middleware.CqrsMiddlewarePipeline;
import io.github.lefpap.mdtr.request.CqrsRequest;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Default implementation of the CQRS middleware pipeline.
 *
 * <p>
 * This class is responsible for executing a request through a series of middleware behaviors.
 * It allows for the addition of global, command-specific, and query-specific middleware
 * to the pipeline, ensuring extensibility and modularity in request handling.
 * </p>
 */
public class DefaultCqrsMiddlewarePipeline implements CqrsMiddlewarePipeline {

    private final List<CqrsHandlerMiddleware> middlewares;

    /**
     * Constructs a new instance of the middleware pipeline with the specified middlewares.
     *
     * @param middlewares the list of middlewares to be included in the pipeline
     */
    public DefaultCqrsMiddlewarePipeline(List<CqrsHandlerMiddleware> middlewares) {
        this.middlewares = Objects.isNull(middlewares)
                ? List.of()
                : List.copyOf(middlewares);
    }

    /**
     * Executes a CQRS request through the middleware pipeline.
     *
     * <p>
     * The pipeline filters applicable middleware for the given request and chains their execution.
     * Each middleware can process the request and decide whether to pass it to the next middleware
     * or terminate the chain.
     *
     * @param request the request to be executed
     * @param handler the handler responsible for processing the request
     * @param <T>     the type of the request, extending {@link CqrsRequest}
     * @param <R>     the type of the result produced by the request
     * @return the result of executing the request through the pipeline
     */
    @Override
    public <T extends CqrsRequest<R>, R> R execute(T request, CqrsRequestHandler<T, R> handler) {

        Supplier<R> invocation = () -> handler.handle(request);
        if (middlewares.isEmpty()) {
            return invocation.get();
        }

        List<CqrsHandlerMiddleware> chain = middlewares.stream()
                .filter(mw -> mw.applies(request))
                .toList();

        for (int i = chain.size() - 1; i >= 0; i--) {
            CqrsHandlerMiddleware middleware = chain.get(i);
            Supplier<R> next = invocation;
            invocation = () -> middleware.invoke(request, next);
        }

        return invocation.get();
    }
}
