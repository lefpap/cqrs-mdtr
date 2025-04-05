package com.github.lefpap.mdtr;

import com.github.lefpap.mdtr.exception.HandlerNotFoundException;
import com.github.lefpap.mdtr.registry.HandlerRegistry;
import com.github.lefpap.mdtr.registry.InMemoryHandlerRegistry;
import com.github.lefpap.sample.command.TestCommand;
import com.github.lefpap.sample.command.TestCommandHandler;
import com.github.lefpap.sample.query.TestQuery;
import com.github.lefpap.sample.query.TestQueryHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CqrsMediatorTest {

    private CqrsMediator mediator;
    private HandlerRegistry handlerRegistry;

    @BeforeEach
    void setup() {
        handlerRegistry = new InMemoryHandlerRegistry();
        handlerRegistry.registerCommandHandler(TestCommand.class, new TestCommandHandler());
        handlerRegistry.registerQueryHandler(TestQuery.class, new TestQueryHandler());
        mediator = new DefaultCqrsMediator(handlerRegistry);
    }

    @Test
    void testCommandHandling() {
        String result = mediator.dispatch(new TestCommand("test"));
        assertEquals("Command handled: test", result);
    }

    @Test
    void testQueryHandling() {
        String result = mediator.send(new TestQuery("test"));
        assertEquals("Query handled: test", result);
    }

    @Test
    void testMissingCommandHandler_throwsException() {
        handlerRegistry.unregisterCommandHandler(TestCommand.class);
        TestCommand command = new TestCommand("test");
        HandlerNotFoundException exception = assertThrows(HandlerNotFoundException.class, () -> {
            mediator.dispatch(command);
        });

        assertTrue(exception.getMessage().contains("No command handler registered for"));
    }

    @Test
    void testMissingQueryHandler_throwsException() {
        handlerRegistry.unregisterQueryHandler(TestQuery.class);
        TestQuery query = new TestQuery("test");
        HandlerNotFoundException exception = assertThrows(HandlerNotFoundException.class, () -> {
            mediator.send(query);
        });

        assertTrue(exception.getMessage().contains("No query handler registered for"));
    }
}
