package com.akash.movieexplorer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.movieexplorer.dto.TmdbMovieDto;
import com.akash.movieexplorer.dto.TmdbSearchResponse;
import com.akash.movieexplorer.service.TmdbService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private TmdbService tmdbService;

    // Search Movies with Filters and Pagination
    @GetMapping("/search")
    public ResponseEntity<?> searchMovies(
            @RequestParam String query,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "1") int page) {

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Search query cannot be blank.");
        }

        try {
            // You may later add logic to pass genre to the TMDb call
            TmdbSearchResponse searchResponse = tmdbService.searchMovies(query, page);
            
            if (searchResponse.getResults().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body("No movies found matching your criteria.");
            }

            return ResponseEntity.ok(searchResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred while fetching movie data.");
        }
    }


    // Get Movie Details by ID
    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieDetails(@PathVariable Long movieId) {
        try {
            TmdbMovieDto movieDetails = tmdbService.getMovieDetails(movieId);
            if (movieDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found.");
            }
            return ResponseEntity.ok(movieDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching movie details.");
        }
    }

    // Trending Movies
    @GetMapping("/trending")
    public ResponseEntity<?> getTrendingMovies() {
        try {
            return ResponseEntity.ok(tmdbService.getTrendingMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch trending movies.");
        }
    }

    // Top Rated Movies
    @GetMapping("/top-rated")
    public ResponseEntity<?> getTopRatedMovies() {
        try {
            return ResponseEntity.ok(tmdbService.getTopRatedMovies());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch top-rated movies.");
        }
    }

    // Movies by Genre ID
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<?> getMoviesByGenre(@PathVariable int genreId) {
        try {
            return ResponseEntity.ok(tmdbService.getMoviesByGenre(genreId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch movies by genre.");
        }
    }
}
