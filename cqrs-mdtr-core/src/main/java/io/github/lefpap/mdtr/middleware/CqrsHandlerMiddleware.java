package io.github.lefpap.mdtr.middleware;

import io.github.lefpap.mdtr.request.CqrsRequest;

import java.util.function.Supplier;

/**
 * Defines a middleware for a request pipeline.
 *
 * <p>
 * A middleware is a component that can intercept and modify the request and response in the pipeline.
 * </p>
 */
public interface CqrsHandlerMiddleware {

    /**
     * Invokes the next middleware in the pipeline.
     *
     * @param request the request to be processed
     * @param next    the next middleware in the pipeline
     * @param <R>     the type of the result
     * @return the result of processing the request
     */
    <T extends CqrsRequest<R>, R> R invoke(T request, Supplier<R> next);

    /**
     * Determines if the middleware applies to the given request.
     *
     * @param request the request to check
     * @return true if the middleware applies, false otherwise
     */
    default boolean applies(CqrsRequest<?> request) {
        return true;
    }
}
