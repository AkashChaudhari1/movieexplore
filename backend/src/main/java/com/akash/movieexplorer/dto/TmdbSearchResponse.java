package com.akash.movieexplorer.dto;

import java.util.List;

public class TmdbSearchResponse {
    private int page;
    private List<TmdbMovieDto> results;
    private int total_pages;
    private int total_results;

    // Getters & setters

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public List<TmdbMovieDto> getResults() {
        return results;
    }
    public void setResults(List<TmdbMovieDto> results) {
        this.results = results;
    }
    public int getTotal_pages() {
        return total_pages;
    }
    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
    public int getTotal_results() {
        return total_results;
    }
    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
    
}