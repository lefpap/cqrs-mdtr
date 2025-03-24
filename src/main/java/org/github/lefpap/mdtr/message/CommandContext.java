package org.github.lefpap.mdtr.message;


/**
 * Provides contextual information for a command.
 * <p>
 * The context can include additional metadata needed to process a command,
 * such as authentication details, request-specific data, or correlation IDs.
 * </p>
 *
 * @param <C> the type of Command associated with this context
 */
public interface CommandContext<C extends Command> {}
