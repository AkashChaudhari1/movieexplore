package com.akash.movieexplorer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.movieexplorer.dto.FavoriteRequest;
import com.akash.movieexplorer.entity.FavoriteMovie;
import com.akash.movieexplorer.security.JwtUtil;
import com.akash.movieexplorer.service.FavoriteMovieService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteMovieController {

    @Autowired
    private FavoriteMovieService favoriteMovieService;

    @Autowired
    private JwtUtil jwtUtil;

    private String extractUsername(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return jwtUtil.extractUsername(authHeader.substring(7));
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(@RequestBody FavoriteRequest favoriteRequest, HttpServletRequest request) {
        String username = extractUsername(request);
        favoriteMovieService.addFavorite(favoriteRequest.getTmdbId(), username);
        return ResponseEntity.ok("Movie added to favorites");
    }

    @DeleteMapping("/{tmdbId}")
    public ResponseEntity<String> removeFavorite(@PathVariable String tmdbId, HttpServletRequest request) {
        String username = extractUsername(request);
        favoriteMovieService.removeFavorite(tmdbId, username);
        return ResponseEntity.ok("Movie removed from favorites");
    }

    @GetMapping
    public ResponseEntity<List<FavoriteMovie>> getFavorites(HttpServletRequest request) {
        String username = extractUsername(request);
        List<FavoriteMovie> favorites = favoriteMovieService.getFavorites(username);
        return ResponseEntity.ok(favorites);
    }
}
