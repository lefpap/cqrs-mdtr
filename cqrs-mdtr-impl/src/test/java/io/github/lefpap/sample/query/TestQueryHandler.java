package io.github.lefpap.sample.query;

import io.github.lefpap.mdtr.query.QueryHandler;

public class TestQueryHandler implements QueryHandler<TestQuery, String> {
    @Override
    public String handle(TestQuery query) {
        return "Query handled: %s".formatted(query.value());
    }

    @Override
    public Class<TestQuery> supportedQuery() {
        return TestQuery.class;
    }
}
