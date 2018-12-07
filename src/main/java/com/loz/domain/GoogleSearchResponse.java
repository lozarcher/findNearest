package com.loz.domain;

import java.util.List;

public class GoogleSearchResponse {
    private List<SearchResult> results;

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
