package io.github.lefpap.mdtr.pipeline;

import io.github.lefpap.mdtr.handler.CqrsRequestHandler;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsCommandPipelineBehavior;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsGlobalPipelineBehavior;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsPipelineBehavior;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsQueryPipelineBehavior;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;
import io.github.lefpap.mdtr.request.CqrsRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Default implementation of the CQRS request pipeline.
 *
 * <p>
 * This class is responsible for executing a request through a series of behaviors.
 * It allows for the addition of global, command-specific, and query-specific behaviors
 * to the pipeline.
 */
public class DefaultCqrsRequestPipeline implements CqrsRequestPipeline {

    private final List<CqrsGlobalPipelineBehavior> globalBehaviors;
    private final List<CqrsCommandPipelineBehavior> commandBehaviors;
    private final List<CqrsQueryPipelineBehavior> queryBehaviors;

    /**
     * Constructs a new {@code DefaultCqrsRequestPipeline} with the specified behaviors.
     *
     * @param globalBehaviors  the global behaviors to be applied to all requests
     * @param commandBehaviors the command-specific behaviors to be applied to commands
     * @param queryBehaviors   the query-specific behaviors to be applied to queries
     */
    public DefaultCqrsRequestPipeline(List<CqrsGlobalPipelineBehavior> globalBehaviors, List<CqrsCommandPipelineBehavior> commandBehaviors, List<CqrsQueryPipelineBehavior> queryBehaviors) {
        this.globalBehaviors = globalBehaviors;
        this.commandBehaviors = commandBehaviors;
        this.queryBehaviors = queryBehaviors;
    }

    /**
     * Executes a request through the pipeline by applying a series of behaviors and invoking the handler.
     *
     * <p>The method constructs a pipeline of behaviors, starting with global behaviors, and then
     * adding command-specific or query-specific behaviors based on the type of the request.
     * The behaviors are applied in reverse order, ensuring that the first behavior in the list
     * is the last to be executed, wrapping the handler invocation.
     *
     * @param request the request to be executed
     * @param handler the handler responsible for processing the request
     * @param <T>     the type of the request
     * @param <R>     the type of the result
     * @return the result of executing the request through the pipeline
     */
    @Override
    public <T extends CqrsRequest<R>, R> R execute(T request, CqrsRequestHandler<T, R> handler) {
        List<CqrsPipelineBehavior> pipeline = new ArrayList<>(globalBehaviors);
        if (request instanceof CqrsCommand) pipeline.addAll(commandBehaviors);
        if (request instanceof CqrsQuery) pipeline.addAll(queryBehaviors);

        Supplier<R> invocation = () -> handler.handle(request);
        for (int i = pipeline.size() - 1; i >= 0; i--) {
            CqrsPipelineBehavior behavior = pipeline.get(i);
            Supplier<R> next = invocation;
            invocation = () -> behavior.invoke(request, next);
        }

        return invocation.get();
    }
}
