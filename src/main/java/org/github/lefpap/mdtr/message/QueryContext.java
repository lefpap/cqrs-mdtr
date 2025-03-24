package org.github.lefpap.mdtr.message;

/**
 * Provides contextual information for a query.
 * <p>
 * The context can include additional metadata needed to process a query,
 * such as authentication details, request-specific data, or correlation IDs.
 * </p>
 *
 * @param <Q> the type of Query associated with this context
 */
public interface QueryContext<Q extends Query> {}
