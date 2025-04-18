package com.github.lefpap.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.github.lefpap.mdtr.registry.HandlerRegistry;
import com.github.lefpap.mdtr.registry.InMemoryHandlerRegistry;
import com.github.lefpap.sample.command.TestCommandHandler;
import com.github.lefpap.sample.query.TestQueryHandler;

public class InMemoryHandlerRegistryTest {

    private final HandlerRegistry handlerRegistry = new InMemoryHandlerRegistry();

    @Test
    void testRegistryCommandActions() {
        var handler = new TestCommandHandler();

        handlerRegistry.registerCommandHandler(handler);
        var existingHandler = handlerRegistry.getCommandHandler(handler.supportedCommand()).orElse(null);
        assertEquals(handler, existingHandler);

        handlerRegistry.unregisterCommandHandler(handler.supportedCommand());
        var nonExistingHandler = handlerRegistry.getCommandHandler(handler.supportedCommand()).orElse(null);
        assertNull(nonExistingHandler);
    }

    @Test
    void testRegistryQueryActions() {
        var handler = new TestQueryHandler();

        handlerRegistry.registerQueryHandler(handler);
        var existingHandler = handlerRegistry.getQueryHandler(handler.supportedQuery()).orElse(null);
        assertEquals(handler, existingHandler);

        handlerRegistry.unregisterQueryHandler(handler.supportedQuery());
        var nonExistingHandler = handlerRegistry.getQueryHandler(handler.supportedQuery()).orElse(null);
        assertNull(nonExistingHandler);
    }

}
