package org.github.lefpap.sample;

import org.github.lefpap.sample.ContextualTestCommand.ContextualTestCommandCtx;
import org.github.lefpap.mdtr.handler.ContextualCommandHandler;

public class ContextualTestCommandHandler implements ContextualCommandHandler<ContextualTestCommand, ContextualTestCommandCtx, String> {

    @Override
    public String handle(ContextualTestCommand command, ContextualTestCommandCtx ctx) {
        return "Handles command %s with context data: %s".formatted(ContextualTestCommand.class.getSimpleName(), ctx);
    }
}
