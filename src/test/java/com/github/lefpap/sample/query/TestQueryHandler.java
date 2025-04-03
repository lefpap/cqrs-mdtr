package com.github.lefpap.sample.query;

import com.github.lefpap.mdtr.handler.QueryHandler;

public class TestQueryHandler implements QueryHandler<TestQuery, String> {
    @Override
    public String handle(TestQuery query) {
        return "Query handled: %s".formatted(query.value());
    }
}
