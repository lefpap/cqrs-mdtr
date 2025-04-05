package com.github.lefpap.mdtr.message;

/**
 * Marker interface representing a command in the CQRS pattern.
 * <p>
 * A command encapsulates a request to change the state of the system.
 * </p>
 *
 * @param <R> the type of result produced by handling the command
 */
public interface Command<R> {}
