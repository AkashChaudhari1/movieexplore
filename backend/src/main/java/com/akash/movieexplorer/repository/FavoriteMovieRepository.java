package com.akash.movieexplorer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akash.movieexplorer.entity.FavoriteMovie;
import com.akash.movieexplorer.entity.User;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
    List<FavoriteMovie> findByUser(User user);
    Optional<FavoriteMovie> findByUserAndTmdbId(User user, String tmdbId);
}
