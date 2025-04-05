package com.github.lefpap.sample.command;

import com.github.lefpap.mdtr.message.Command;

/** Dummy command implementation for testing. */
public record TestCommand(String value) implements Command<String> {}
