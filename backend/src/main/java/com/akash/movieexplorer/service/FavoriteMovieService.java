package com.akash.movieexplorer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.movieexplorer.dto.TmdbMovieDto;
import com.akash.movieexplorer.entity.FavoriteMovie;
import com.akash.movieexplorer.entity.User;
import com.akash.movieexplorer.repository.FavoriteMovieRepository;

@Service
public class FavoriteMovieService {

    @Autowired
    private FavoriteMovieRepository favoriteMovieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TmdbService tmdbService;

    public void addFavorite(String tmdbId, String username) {
        User user = userService.getUserByUsername(username);

        boolean alreadyExists = favoriteMovieRepository.findByUserAndTmdbId(user, tmdbId).isPresent();
        if (!alreadyExists) {
            TmdbMovieDto movie = tmdbService.getMovieDetails(Long.parseLong(tmdbId));
            FavoriteMovie favorite = new FavoriteMovie();
            favorite.setTmdbId(tmdbId);
            favorite.setTitle(movie.getTitle());
            favorite.setPosterPath(movie.getPoster_path());
            favorite.setUser(user);
            favoriteMovieRepository.save(favorite);
        }
    }

    public void removeFavorite(String tmdbId, String username) {
        User user = userService.getUserByUsername(username);
        favoriteMovieRepository.findByUserAndTmdbId(user, tmdbId)
                .ifPresent(favoriteMovieRepository::delete);
    }

    public List<FavoriteMovie> getFavorites(String username) {
        User user = userService.getUserByUsername(username);
        return favoriteMovieRepository.findByUser(user);
    }
}
