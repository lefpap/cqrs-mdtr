package org.github.lefpap.mdtr.handler;


import org.github.lefpap.mdtr.message.Query;
import org.github.lefpap.mdtr.message.QueryContext;

/**
 * Handles a query that requires additional contextual information.
 *
 * @param <Q>   the type of query to be processed
 * @param <CTX> the type of context required to process the query
 * @param <R>   the type of result produced by handling the query
 */
public interface ContextualQueryHandler<Q extends Query, CTX extends QueryContext<Q>, R> {

    /**
     * Handles the given query using the provided context and returns a result.
     *
     * @param query the query to handle
     * @param ctx   the context containing additional information required for processing
     * @return the result of processing the query
     */
    R handle(Q query, CTX ctx);
}
