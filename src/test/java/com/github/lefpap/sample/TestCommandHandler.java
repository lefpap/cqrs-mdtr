package com.github.lefpap.sample;

import com.github.lefpap.mdtr.handler.CommandHandler;

public class TestCommandHandler implements CommandHandler<TestCommand, String> {

    @Override
    public String handle(TestCommand command) {
        return "Handles command %s".formatted(TestCommand.class);
    }
}
