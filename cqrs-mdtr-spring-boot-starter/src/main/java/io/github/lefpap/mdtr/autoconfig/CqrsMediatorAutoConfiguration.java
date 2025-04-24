package io.github.lefpap.mdtr.autoconfig;

import io.github.lefpap.mdtr.CqrsMediator;
import io.github.lefpap.mdtr.DefaultCqrsMediator;
import io.github.lefpap.mdtr.annotation.CqrsHandler;
import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.handler.CqrsRequestHandler;
import io.github.lefpap.mdtr.pipeline.CqrsRequestPipeline;
import io.github.lefpap.mdtr.pipeline.DefaultCqrsRequestPipeline;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsCommandPipelineBehavior;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsGlobalPipelineBehavior;
import io.github.lefpap.mdtr.pipeline.behavior.CqrsQueryPipelineBehavior;
import io.github.lefpap.mdtr.registry.CqrsHandlerRegistry;
import io.github.lefpap.mdtr.registry.InMemoryCqrsHandlerRegistry;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Autoconfiguration for the CQRS mediator.
 */
@AutoConfiguration
@ConditionalOnClass(CqrsMediator.class)
public class CqrsMediatorAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CqrsMediatorAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(CqrsHandlerRegistry.class)
    public CqrsHandlerRegistry cqrsHandlerRegistry(
            List<CqrsRequestHandler<?, ?>> handlers
    ) {
        var registry = new InMemoryCqrsHandlerRegistry();
        for (var h : handlers) {
            var meta = h.getClass().getAnnotation(CqrsHandler.class);
            Assert.state(meta != null, "Handler must have @CqrsHandler annotation");
            Class<? extends CqrsRequest<?>> type = meta.value();
            if (CqrsCommand.class.isAssignableFrom(type)) {
                //noinspection unchecked,rawtypes
                registry.registerCommandHandler((Class) type, (CqrsCommandHandler) h);
                log.debug("Registered command handler for type: {}", type.getName());
            } else {
                //noinspection unchecked,rawtypes
                registry.registerQueryHandler((Class) type, (CqrsQueryHandler) h);
                log.debug("Registered query handler for type: {}", type.getName());
            }
        }

        return registry;
    }

    /**
     * Creates a {@link CqrsMediator} bean if none is already defined.
     *
     * @param registry the CQRS handler registry
     * @return the CQRS mediator
     */
    @Bean
    @ConditionalOnMissingBean(CqrsMediator.class)
    public CqrsMediator cqrsMediator(CqrsHandlerRegistry registry, CqrsRequestPipeline pipeline) {
        return new DefaultCqrsMediator(registry, pipeline);
    }

    @Bean
    @ConditionalOnMissingBean
    public CqrsRequestPipeline pipeline(
            List<CqrsGlobalPipelineBehavior> globals,
            List<CqrsCommandPipelineBehavior> commands,
            List<CqrsQueryPipelineBehavior> queries
    ) {
        return new DefaultCqrsRequestPipeline(globals, commands, queries);
    }
}
