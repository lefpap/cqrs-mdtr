package org.github.lefpap.sample;

import org.github.lefpap.mdtr.message.Command;

public record TestCommand(
    String value
) implements Command {}
