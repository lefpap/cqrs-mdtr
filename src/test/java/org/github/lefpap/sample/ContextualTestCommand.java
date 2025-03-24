package org.github.lefpap.sample;

import org.github.lefpap.mdtr.message.Command;
import org.github.lefpap.mdtr.message.CommandContext;

public record ContextualTestCommand() implements Command {
    public record ContextualTestCommandCtx() implements CommandContext<ContextualTestCommand> {}
}
