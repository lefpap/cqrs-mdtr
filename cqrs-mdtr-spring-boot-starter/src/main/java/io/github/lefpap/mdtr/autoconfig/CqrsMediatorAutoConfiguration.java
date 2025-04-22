package io.github.lefpap.mdtr.autoconfig;

import io.github.lefpap.mdtr.DefaultCqrsMediator;
import io.github.lefpap.mdtr.CqrsMediator;
import io.github.lefpap.mdtr.handler.CqrsCommandHandler;
import io.github.lefpap.mdtr.handler.CqrsQueryHandler;
import io.github.lefpap.mdtr.registry.CqrsHandlerRegistry;
import io.github.lefpap.mdtr.registry.InMemoryCqrsHandlerRegistry;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration for the CQRS mediator.
 */
@AutoConfiguration
@ConditionalOnClass(CqrsMediator.class)
public class CqrsMediatorAutoConfiguration {

    /**
     * Creates a {@link CqrsHandlerRegistry} bean if none is already defined.
     * <p>
     * This registry will be populated with all beans of type {@link CqrsCommandHandler} and {@link CqrsQueryHandler}
     * found in the application context.
     *
     * @param beanFactory the bean factory
     * @return the CQRS handler registry
     */
    @Bean
    @ConditionalOnMissingBean(CqrsHandlerRegistry.class)
    public CqrsHandlerRegistry cqrsHandlerRegistry(ListableBeanFactory beanFactory) {
        var registry = new InMemoryCqrsHandlerRegistry();

        //noinspection unchecked
        beanFactory.getBeansOfType(CqrsCommandHandler.class)
                .values()
                .forEach(handler -> registry.registerCommandHandler(handler.getCommandType(), handler));

        //noinspection unchecked
        beanFactory.getBeansOfType(CqrsQueryHandler.class)
                .values()
                .forEach(handler -> registry.registerQueryHandler(handler.getQueryType(), handler));

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
    public CqrsMediator cqrsMediator(CqrsHandlerRegistry registry) {
        return new DefaultCqrsMediator(registry);
    }
}
