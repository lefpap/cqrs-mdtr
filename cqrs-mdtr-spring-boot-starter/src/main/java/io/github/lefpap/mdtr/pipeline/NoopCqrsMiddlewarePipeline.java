package io.github.lefpap.mdtr.pipeline;

import io.github.lefpap.mdtr.handler.CqrsRequestHandler;
import io.github.lefpap.mdtr.middleware.CqrsMiddlewarePipeline;
import io.github.lefpap.mdtr.request.CqrsRequest;

/**
 * A no-operation implementation of the {@link CqrsMiddlewarePipeline} interface.
 * This class is used when no middleware is needed in the pipeline.
 * It simply executes the request using the provided handler without any additional processing.
 */
public class NoopCqrsMiddlewarePipeline implements CqrsMiddlewarePipeline {

    /**
     * No operation implementation of the execute method.
     */
    @Override
    public <T extends CqrsRequest<R>, R> R execute(T request, CqrsRequestHandler<T, R> handler) {
        return handler.handle(request);
    }
}
