package co.com.poli.movie_service.service;


import co.com.poli.movie_service.persistence.entity.Movie;

import java.util.List;

public interface MovieService {

    void save(Movie movie);
    void delete(Movie movie);
    List<Movie> findAll();
    Movie findById(Long id);
}