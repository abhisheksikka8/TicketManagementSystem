package com.sapient.bookmymovie.controller;

import com.sapient.bookmymovie.data.entity.Show;
import com.sapient.bookmymovie.exceptions.InvalidDateException;
import com.sapient.bookmymovie.service.TheatreService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Log
public class TheatreController {
    @Autowired
    private TheatreService theatreService;

    public ResponseEntity<List<Show>> findShowsByCityAndDate(Integer cityId, Long movieId, LocalDate selectedDate ) {
        log.info(String.format("Finding Shows for City: %s, Movie ID: %d, Date: %s", cityId, movieId, selectedDate));

        if(LocalDate.now().isAfter(selectedDate)) {
            log.info("Invalid Date : " + selectedDate);
            throw new InvalidDateException("Invalid Date" + selectedDate);
        }

        List<Show> shows = theatreService.getShowsForMovie(cityId, movieId, selectedDate);
        log.info("Shows Found: \n" + shows);

        return ResponseEntity.ok(shows);
    }
}
