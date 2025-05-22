package com.akash.movieexplorer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.akash.movieexplorer.dto.TmdbMovieDto;
import com.akash.movieexplorer.dto.TmdbSearchResponse;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Search Movies
    public TmdbSearchResponse searchMovies(String query, int page) {
        try {
            String url = String.format("%s/search/movie?api_key=%s&query=%s&page=%d", baseUrl, apiKey, query, page);
            return restTemplate.getForObject(url, TmdbSearchResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 429) {
                throw new RuntimeException("API rate limit exceeded.");
            }
            throw new RuntimeException("Error fetching movie search results.");
        }
    }

    // Get Movie Details by ID
    public TmdbMovieDto getMovieDetails(Long movieId) {
        try {
            String url = String.format("%s/movie/%d?api_key=%s", baseUrl, movieId, apiKey);
            return restTemplate.getForObject(url, TmdbMovieDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            throw new RuntimeException("Error fetching movie details.");
        }
    }

    // ✅ Get Trending Movies
    public TmdbSearchResponse getTrendingMovies() {
        try {
            String url = String.format("%s/trending/movie/week?api_key=%s", baseUrl, apiKey);
            return restTemplate.getForObject(url, TmdbSearchResponse.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching trending movies.");
        }
    }

    // ✅ Get Top Rated Movies
    public TmdbSearchResponse getTopRatedMovies() {
        try {
            String url = String.format("%s/movie/top_rated?api_key=%s", baseUrl, apiKey);
            return restTemplate.getForObject(url, TmdbSearchResponse.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching top-rated movies.");
        }
    }

    // ✅ Get Movies by Genre
    public TmdbSearchResponse getMoviesByGenre(int genreId) {
        try {
            String url = String.format("%s/discover/movie?api_key=%s&with_genres=%d", baseUrl, apiKey, genreId);
            return restTemplate.getForObject(url, TmdbSearchResponse.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching genre movies.");
        }
    }
}
