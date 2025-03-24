package org.github.lefpap.mdtr.handler;


import org.github.lefpap.mdtr.message.Query;


/**
 * Handles a query by processing it and returning a result.
 *
 * @param <Q> the type of query to be processed
 * @param <R> the type of result produced by handling the query
 */
public interface QueryHandler<Q extends Query, R> {

    /**
     * Handles the given query and returns a result.
     *
     * @param query the query to handle
     * @return the result of processing the query
     */
    R handle(Q query);
}
