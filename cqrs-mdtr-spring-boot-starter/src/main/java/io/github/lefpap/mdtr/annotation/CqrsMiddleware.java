package io.github.lefpap.mdtr.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Annotation to mark a class as a CQRS middleware.
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Order
public @interface CqrsMiddleware {

    /**
     * The order in which the middleware should be executed.
     *
     * <p> The default order is {@link Ordered#LOWEST_PRECEDENCE}.
     *
     * @return the order of execution
     */
    @AliasFor(annotation = Order.class, attribute = "value")
    int order() default Ordered.LOWEST_PRECEDENCE;
}
