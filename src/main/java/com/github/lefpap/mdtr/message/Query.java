package com.github.lefpap.mdtr.message;

/**
 * Marker interface representing a query in the CQRS pattern.
 * <p>
 * A query encapsulates a request for data without modifying the state of the system.
 * </p>
 *
 * @param <R> the type of result produced by handling the query
 */
public interface Query<R> {}
