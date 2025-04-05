package com.github.lefpap.sample.query;

import com.github.lefpap.mdtr.message.Query;

/** Dummy query implementation for testing. */
public record TestQuery(String value) implements Query<String> {}
