package com.sapient.bookmymovie.data.repository;

import com.sapient.bookmymovie.data.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    Movie findByMovieName(String movieName);
    Movie findByMovieId(long movieId);
    Integer deleteByMovieName(String movieName);

}
