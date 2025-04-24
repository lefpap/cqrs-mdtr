package io.github.lefpap.mdtr.pipeline.behavior;

import io.github.lefpap.mdtr.request.CqrsRequest;

import java.util.function.Supplier;

/**
 * Defines a behavior for a request pipeline.
 */
public interface CqrsPipelineBehavior {

    /**
     * Invokes the next behavior in the pipeline.
     *
     * @param request the request to be processed
     * @param next    the next behavior in the pipeline
     * @param <R>     the type of the result
     * @return the result of processing the request
     */
    <T extends CqrsRequest<R>, R> R invoke(T request, Supplier<R> next);
}
