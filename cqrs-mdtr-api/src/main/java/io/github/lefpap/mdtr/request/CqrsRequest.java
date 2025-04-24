package io.github.lefpap.mdtr.request;

/**
 * Represents a request in the CQRS pattern.
 *
 * <p>
 * This interface serves as a marker for all request types in the CQRS system.
 * It is used to define the structure of requests that can be handled by the system.
 *
 * @param <R> the type of result produced by handling the request
 */
public interface CqrsRequest<R> {
}
