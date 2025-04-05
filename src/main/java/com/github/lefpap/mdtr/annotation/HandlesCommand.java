package com.github.lefpap.mdtr.annotation;


import com.github.lefpap.mdtr.message.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a handler for a specific {@link Command}.
 * <p>
 * This annotation is used to indicate that the annotated class is responsible for processing
 * a particular type of {@link Command}. The command type is specified as the annotation's value.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlesCommand {
    /**
     * The Command class this handler is responsible for.
     */
    @SuppressWarnings("rawtypes") Class<? extends Command> value();
}
