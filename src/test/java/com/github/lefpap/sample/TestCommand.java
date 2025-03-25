package com.github.lefpap.sample;

import com.github.lefpap.mdtr.message.Command;

public record TestCommand(
    String value
) implements Command {}
