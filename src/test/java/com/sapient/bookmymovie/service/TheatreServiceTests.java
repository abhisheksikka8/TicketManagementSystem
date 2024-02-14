package com.sapient.bookmymovie.service;

import com.sapient.bookmymovie.data.entity.Screen;
import com.sapient.bookmymovie.data.entity.Show;
import com.sapient.bookmymovie.data.entity.Theatre;
import com.sapient.bookmymovie.data.repository.TheatreRepository;
import com.sapient.bookmymovie.exceptions.NoShowsFoundForCityAndDateException;
import com.sapient.bookmymovie.exceptions.NoTheatreFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TheatreServiceTests {
    @Mock
    private TheatreRepository theatreRepository;

    @InjectMocks
    private TheatreService theatreService;

    @Test
     void testGetTheaterAndShowsForMovieForNoTheatresPresent() {
        Mockito.when(theatreRepository.findAllByTheatreCity(1))
                .thenReturn(Collections.emptyList());

        Assertions.assertThrows(NoTheatreFoundException.class , () ->
                theatreService.getShowsForMovie(1, 1l, LocalDate.now()));
    }

    @Test
     void testGetTheaterAndShowsForMovieForValidShows() {
        Show show = new Show();
        show.setShowId(1l);
        show.setScreeningDate(LocalDateTime.now());
        show.setScreenId(1l);
        show.setMovie(1l);

        Show show1 = new Show();
        show1.setShowId(2l);
        show1.setScreeningDate(LocalDateTime.now().plusDays(2));
        show1.setScreenId(1l);
        show1.setMovie(1l);

        List<Show> shows = new ArrayList<>();
        shows.add(show1);
        shows.add(show);

        List<Screen> screens  = new ArrayList<>();
        Screen screen = new Screen();
        screen.setTheatreId(1l);
        screen.setShows(shows);
        screen.setSeatsNum(10);
        screen.setScreenId(1l);
        screen.setScreeName("AUDI-01");

        screens.add(screen);

        Theatre theatre = new Theatre();
        theatre.setTheatreCity(1);
        theatre.setTheatreName("PVR");
        theatre.setScreens(screens);
        theatre.setTheatreId(1l);

        Mockito.when(theatreRepository.findAllByTheatreCity(1)).thenReturn(Arrays.asList(theatre));

        Assertions.assertEquals(show,
             theatreService.getShowsForMovie(1, 1l, LocalDate.now()).get(0)
        );
    }

    @Test
    void testGetTheaterAndShowsForMovieForNoShows() {
        Show show = new Show();
        show.setShowId(1l);
        show.setScreeningDate(LocalDateTime.now());
        show.setScreenId(1l);
        show.setMovie(1l);

        Show show1 = new Show();
        show1.setShowId(2l);
        show1.setScreeningDate(LocalDateTime.now().plusDays(2));
        show1.setScreenId(1l);
        show1.setMovie(1l);

        List<Show> shows = new ArrayList<>();
        shows.add(show1);
        shows.add(show);

        List<Screen> screens  = new ArrayList<>();
        Screen screen = new Screen();
        screen.setTheatreId(1l);
        screen.setShows(shows);
        screen.setSeatsNum(10);
        screen.setScreenId(1l);
        screen.setScreeName("AUDI-01");

        screens.add(screen);

        Theatre theatre = new Theatre();
        theatre.setTheatreCity(1);
        theatre.setTheatreName("PVR");
        theatre.setScreens(screens);
        theatre.setTheatreId(1l);

        Mockito.when(theatreRepository.findAllByTheatreCity(1)).thenReturn(Arrays.asList(theatre));

        Assertions.assertThrows(NoShowsFoundForCityAndDateException.class, () ->
                theatreService.getShowsForMovie(1, 1l, LocalDate.now().plusDays(3))
        );
    }
}
