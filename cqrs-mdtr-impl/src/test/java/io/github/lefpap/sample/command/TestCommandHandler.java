package io.github.lefpap.sample.command;

import io.github.lefpap.mdtr.command.CommandHandler;

public class TestCommandHandler implements CommandHandler<TestCommand, String> {
    @Override
    public String handle(TestCommand command) {
        return "Command handled: %s".formatted(command.value());
    }

    @Override
    public Class<TestCommand> supportedCommand() {
        return TestCommand.class;
    }
}
