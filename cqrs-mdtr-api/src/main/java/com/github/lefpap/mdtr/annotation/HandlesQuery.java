package com.github.lefpap.mdtr.annotation;



import com.github.lefpap.mdtr.query.Query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to mark a class as a handler for a specific {@link Query}.
 * <p>
 * This annotation is used to indicate that the annotated class is responsible for processing
 * a particular type of {@link Query}. The query type is specified as the annotation's value.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlesQuery {
    /**
     * The Query class this handler is responsible for.
     */
    @SuppressWarnings("rawtypes") Class<? extends Query> value();
}
