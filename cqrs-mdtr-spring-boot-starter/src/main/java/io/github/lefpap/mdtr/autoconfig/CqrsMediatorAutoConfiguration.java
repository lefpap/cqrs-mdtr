package io.github.lefpap.mdtr.autoconfig;

import io.github.lefpap.mdtr.CqrsMediator;
import io.github.lefpap.mdtr.DefaultCqrsMediator;
import io.github.lefpap.mdtr.pipeline.DefaultCqrsMiddlewarePipeline;
import io.github.lefpap.mdtr.pipeline.NoopCqrsMiddlewarePipeline;
import io.github.lefpap.mdtr.annotation.CqrsHandler;
import io.github.lefpap.mdtr.annotation.CqrsMiddleware;
import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.handler.CqrsRequestHandler;
import io.github.lefpap.mdtr.middleware.CqrsHandlerMiddleware;
import io.github.lefpap.mdtr.middleware.CqrsMiddlewarePipeline;
import io.github.lefpap.mdtr.registry.CqrsHandlerRegistry;
import io.github.lefpap.mdtr.registry.InMemoryCqrsHandlerRegistry;
import io.github.lefpap.mdtr.request.CqrsCommand;
import io.github.lefpap.mdtr.request.CqrsQuery;
import io.github.lefpap.mdtr.request.CqrsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Autoconfiguration for the CQRS mediator.
 */
@AutoConfiguration
@ConditionalOnClass(CqrsMediator.class)
public class CqrsMediatorAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CqrsMediatorAutoConfiguration.class);

    private final ApplicationContext ctx;

    public CqrsMediatorAutoConfiguration(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Creates a {@link CqrsMediator} bean if none is already defined.
     *
     * @param registry the CQRS handler registry
     * @return the CQRS mediator
     */
    @Bean
    @ConditionalOnMissingBean
    public CqrsMediator cqrsMediator(CqrsHandlerRegistry registry, CqrsMiddlewarePipeline pipeline) {
        return new DefaultCqrsMediator(registry, pipeline);
    }

    /**
     * Creates a {@link CqrsHandlerRegistry} bean if none is already defined.
     *
     * @return the CQRS handler registry
     */
    @Bean
    @ConditionalOnMissingBean(CqrsHandlerRegistry.class)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public CqrsHandlerRegistry cqrsHandlerRegistry() {
        var registry = new InMemoryCqrsHandlerRegistry();

        ctx.getBeansWithAnnotation(CqrsHandler.class).values().stream()
                .filter(CqrsRequestHandler.class::isInstance)
                .map(CqrsRequestHandler.class::cast)
                .forEach(handler -> {
                    // Get the annotation
                    CqrsHandler ann = AnnotatedElementUtils.findMergedAnnotation(handler.getClass(), CqrsHandler.class);
                    Class<? extends CqrsRequest<?>> reqType = Objects.requireNonNull(ann).value();

                    if (CqrsCommand.class.isAssignableFrom(reqType)) {
                        registry.registerCommandHandler((Class) reqType, (CqrsCommandHandler) handler);
                        log.debug("Registered command handler {} → {}", handler.getClass().getSimpleName(), reqType.getSimpleName());
                    } else if (CqrsQuery.class.isAssignableFrom(reqType)) {
                        registry.registerQueryHandler((Class) reqType, (CqrsQueryHandler) handler);
                        log.debug("Registered query   handler {} → {}", handler.getClass().getSimpleName(), reqType.getSimpleName());
                    } else {
                        throw new IllegalStateException(
                                "@CqrsHandler on " + handler.getClass().getName() +
                                        " points at " + reqType.getName() +
                                        " which is neither a Command nor a Query");
                    }
                });

        return registry;
    }

    /**
     * Creates a {@link CqrsMiddlewarePipeline} bean if none is already defined.
     *
     * @return the CQRS request pipeline
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(CqrsHandlerMiddleware.class)
    public CqrsMiddlewarePipeline cqrsMiddlewarePipeline() {
        // Find all beans annotated @CqrsMiddleware
        var candidates = ctx.getBeansWithAnnotation(CqrsMiddleware.class)
                .values();

        // Filter out the beans that are not CqrsHandlerMiddleware
        List<CqrsHandlerMiddleware> middlewares = candidates.stream()
                .filter(CqrsHandlerMiddleware.class::isInstance)
                .map(CqrsHandlerMiddleware.class::cast)
                .collect(Collectors.toList());

        // sort by @Order on the bean class
        AnnotationAwareOrderComparator.sort(middlewares);
        return new DefaultCqrsMiddlewarePipeline(middlewares);
    }

    /**
     * Creates a no-operation {@link CqrsMiddlewarePipeline} bean if no other pipeline is defined.
     *
     * @return the no-operation CQRS request pipeline
     */
    @Bean
    @ConditionalOnMissingBean
    public CqrsMiddlewarePipeline noopCqrsMiddlewarePipeline() {
        return new NoopCqrsMiddlewarePipeline();
    }
}
