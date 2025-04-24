package io.github.lefpap.mdtr.pipeline;

import io.github.lefpap.mdtr.handler.CqrsRequestHandler;
import io.github.lefpap.mdtr.request.CqrsRequest;

/**
 * Defines a pipeline for executing requests.
 * This interface allows for the execution of requests using a specified handler.
 */
public interface CqrsRequestPipeline {

    /**
     * Executes a request using the provided handler.
     *
     * @param request the request to be executed
     * @param handler the handler to process the request
     * @param <R>     the type of the result
     * @return the result of executing the request
     */
    <T extends CqrsRequest<R>, R> R execute(T request, CqrsRequestHandler<T, R> handler);
}
