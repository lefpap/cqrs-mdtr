package com.github.lefpap.sample.command;

import com.github.lefpap.mdtr.command.Command;

/** Dummy command implementation for testing. */
public record TestCommand(String value) implements Command<String> {
}
