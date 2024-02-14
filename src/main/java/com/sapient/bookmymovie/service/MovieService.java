package com.sapient.bookmymovie.service;

import com.sapient.bookmymovie.data.entity.Movie;
import com.sapient.bookmymovie.data.model.request.MovieRequest;
import com.sapient.bookmymovie.data.repository.MovieRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieRequest movieRequest) {

        Movie movie = Movie.builder().movieTags(movieRequest.getMovieTags())
                .movieName(movieRequest.getMovieName())
                .moviePosterUrl(movieRequest.getMoviePosterUrl()).build();

        log.info(String.format("Adding New Movie: %s", movie));
        return movieRepository.save(movie);
    }

    public Integer deleteMovieByName(String name) {
        log.info(String.format("Deletion Request for Movie with Name: %s", name));

        return movieRepository.deleteByMovieName(name);
    }

    public Movie getMovieDetailsByName(String name) {
        log.info(String.format("Find Request for Movie with Name: %s", name));

        return movieRepository.findByMovieName(name);
    }
}
