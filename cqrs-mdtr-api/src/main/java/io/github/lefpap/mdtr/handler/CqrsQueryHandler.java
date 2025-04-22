package io.github.lefpap.mdtr.handler;

import io.github.lefpap.mdtr.request.CqrsQuery;

/**
 * Handles a query by processing it and returning a result.
 *
 * @param <Q> the type of query to be processed
 * @param <R> the type of result produced by handling the query
 */
public interface CqrsQueryHandler<Q extends CqrsQuery<R>, R> {

    /**
     * Handles the given query and returns a result.
     *
     * @param query the query to handle
     * @return the result of processing the query
     */
    R handle(Q query);

    /**
     * Returns the class type of the query handled by this handler.
     *
     * @return the class type of the query
     */
    Class<Q> getQueryType();

    /**
     * Checks if this handler supports the given query type.
     *
     * @param queryType the class type of the query
     * @return true if this handler supports the query type, false otherwise
     */
    default boolean supports(Class<? extends CqrsQuery<?>> queryType) {
        return getQueryType().isAssignableFrom(queryType);
    }
}
