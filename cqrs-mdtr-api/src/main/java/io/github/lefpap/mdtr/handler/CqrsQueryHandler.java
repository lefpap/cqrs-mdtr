package io.github.lefpap.mdtr.handler;

import io.github.lefpap.mdtr.request.CqrsQuery;

/**
 * Handles a query by processing it and returning a result.
 *
 * @param <Q> the type of query to be processed
 * @param <R> the type of result produced by handling the query
 */
public interface CqrsQueryHandler<Q extends CqrsQuery<R>, R> extends CqrsRequestHandler<Q, R> {
}
