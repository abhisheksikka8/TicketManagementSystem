package com.sapient.bookmymovie.service;

import com.sapient.bookmymovie.data.entity.Show;
import com.sapient.bookmymovie.data.entity.Theatre;
import com.sapient.bookmymovie.data.repository.TheatreRepository;
import com.sapient.bookmymovie.exceptions.NoShowsFoundForCityAndDateException;
import com.sapient.bookmymovie.exceptions.NoTheatreFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Log
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    public List<Show> getShowsForMovie(Integer cityId, Long movieId, LocalDate selectedDate) {

       List<Theatre> theatres = theatreRepository.findAllByTheatreCity(cityId);

       if(theatres.isEmpty()) {
           log.info("No Theaters found for City : " + cityId);
           throw new NoTheatreFoundException(String.format("No Theaters found for City :"  + cityId));
       }

       List<Show> shows = theatres.stream().flatMap(theatre -> theatre.getScreens().stream().map(screen ->
                        screen.getShowsByMovieAndSelectedDate(movieId, selectedDate)))
               .flatMap(Collection::stream).toList();

        if(shows.isEmpty()) {
           throw new NoShowsFoundForCityAndDateException(String
                   .format("No Shows found for Movie Id: %d, Date : %s", movieId, selectedDate));
       }

        return shows;
    }



}
