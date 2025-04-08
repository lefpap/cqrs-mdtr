package com.github.lefpap.sample.command;

import com.github.lefpap.mdtr.command.CommandHandler;

public class TestCommandHandler implements CommandHandler<TestCommand, String> {
    @Override
    public String handle(TestCommand command) {
        return "Command handled: %s".formatted(command.value());
    }
}
