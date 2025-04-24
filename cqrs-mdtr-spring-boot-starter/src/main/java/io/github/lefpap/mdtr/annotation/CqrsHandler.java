package io.github.lefpap.mdtr.annotation;

import io.github.lefpap.mdtr.request.CqrsRequest;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CqrsHandler {
    Class<? extends CqrsRequest<?>> value();
}
