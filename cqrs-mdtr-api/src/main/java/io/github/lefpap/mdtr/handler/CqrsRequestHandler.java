package io.github.lefpap.mdtr.handler;

import io.github.lefpap.mdtr.request.CqrsRequest;

/**
 * Handles a request by processing it and returning a result.
 *
 * @param <T> the type of request to be processed
 * @param <R> the type of result produced by handling the request
 */
public interface CqrsRequestHandler<T extends CqrsRequest<R>, R> {
    /**
     * Handles a request and returns a result.
     *
     * @param request the request to be handled
     * @return the result of handling the request
     */
    R handle(T request);
}
