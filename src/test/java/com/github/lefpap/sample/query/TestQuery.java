package com.github.lefpap.sample.query;

import com.github.lefpap.mdtr.query.Query;

/** Dummy query implementation for testing. */
public record TestQuery(String value) implements Query<String> {}
