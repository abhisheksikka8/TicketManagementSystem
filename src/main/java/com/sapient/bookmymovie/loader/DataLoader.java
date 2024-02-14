package com.sapient.bookmymovie.loader;

import com.sapient.bookmymovie.data.entity.*;
import com.sapient.bookmymovie.data.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.Month;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TaskExecutor taskExecutor;

    private class ProcessMovie implements Runnable {
        private String movieLine;

        ProcessMovie(String movieLine) {
            this.movieLine = movieLine;
        }

        @Override
        public void run() {
            String[] movieInfo = movieLine.split(",");

            Movie movie = new Movie();
            movie.setMovieId(Long.parseLong(movieInfo[0]));
            movie.setMovieName(movieInfo[1]);
            movie.setMovieTags(movieInfo[2]);
            movie.setMoviePosterUrl(movieInfo[3]);

            movieRepository.save(movie);
        }
    }

    private void populateMovieTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("movies.small.csv").getInputStream()));
              ) {
            String movieLine;
            while ((movieLine = brMovies.readLine()) != null) {
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessMovie(movieLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ProcessCity implements Runnable {
        private String cityLine;

        ProcessCity(String cityLine) {
            this.cityLine = cityLine;
        }

        @Override
        public void run() {
            String[] cityInfo = cityLine.split(",");

            City city = City.builder().cityId(Integer.valueOf(cityInfo[0])).cityName(cityInfo[1]).cityCode(cityInfo[2])
                    .latitude(Double.valueOf(cityInfo[3])).longitude(Double.valueOf(cityInfo[4])).build();

            cityRepository.save(city);
        }
    }

    private void populateCityTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("cities.small.csv").getInputStream()));
        ) {
            String movieLine;
            while ((movieLine = brMovies.readLine()) != null) {
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessCity(movieLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ProcessTheatre implements Runnable {
        private String theatreLine;

        ProcessTheatre(String theatreLine) {
            this.theatreLine = theatreLine;
        }

        @Override
        public void run() {
            String[] theatreInfo = theatreLine.split(",");

            Theatre theatre = new Theatre();
            theatre.setTheatreId(Long.parseLong(theatreInfo[0]));
            theatre.setTheatreName(theatreInfo[1]);
            theatre.setTheatreCity(Integer.parseInt(theatreInfo[2]));

            theatreRepository.save(theatre);
        }
    }

    private void populateTheatreTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("theatre.small.csv").getInputStream()));
        ) {
            String theatreLine;
            while ((theatreLine = brMovies.readLine()) != null) {
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessTheatre(theatreLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ProcessScreen implements Runnable {
        private String screenLine;

        ProcessScreen(String screenLine) {
            this.screenLine = screenLine;
        }

        @Override
        public void run() {
            String[] screenInfo = screenLine.split(",");

            Screen screen = new Screen();
            screen.setScreenId(Long.parseLong(screenInfo[0]));
            screen.setSeatsNum(Integer.parseInt(screenInfo[1]));
            screen.setScreeName(screenInfo[2]);
            screen.setTheatreId(Long.parseLong(screenInfo[3]));

            screenRepository.save(screen);
        }
    }

    private void populateScreenTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("screens.small.csv").getInputStream()));
        ) {
            String screenLine;
            while ((screenLine = brMovies.readLine()) != null) {
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessScreen(screenLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ProcessShow implements Runnable {
        private String showLine;
        private LocalDateTime localDateTime;

        ProcessShow(String showLine, LocalDateTime localDateTime) {
            this.showLine = showLine;
            this.localDateTime = localDateTime;
        }

        @Override
        public void run() {
            String[] showInfo = showLine.split(",");

            Show show = new Show();
            show.setShowId(Long.parseLong(showInfo[0]));
            show.setScreenId(Long.parseLong(showInfo[1]));
            show.setMovie(Long.parseLong(showInfo[2]));
            show.setScreeningDate(localDateTime);
            show.setBookedTickets(0);

            showRepository.save(show);
        }
    }

    private void populateShowTable() {
        int dateCount = 1;
        int hourCount = 12;
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader
                (new ClassPathResource("shows.small.csv").getInputStream()));
        ) {
            String showLine;
            while ((showLine = brMovies.readLine()) != null) {
                new ProcessShow(showLine, LocalDateTime.of(2024, Month.JANUARY,
                        getNumber(dateCount++), getNumber(hourCount++), 00, 00 )).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer getNumber(int count) {
        return count%2 == 0 ? 3: 6;
    }

    private class ProcessSeat implements Runnable {
        private String seatLine;

        ProcessSeat(String seatLine) {
            this.seatLine = seatLine;
        }

        @Override
        public void run() {
            String[] seatInfo = seatLine.split(",");

            Seat seat = new Seat();
            seat.setSeatId(Long.parseLong(seatInfo[0]));
            seat.setRowId(seatInfo[1].toCharArray()[0]);
            seat.setRowNumber(Integer.parseInt(seatInfo[2]));
            seat.setScreenId(Long.parseLong(seatInfo[3]));

            seatRepository.save(seat);
        }
    }

    private void populateSeatTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("seats.small.csv").getInputStream()));
        ) {
            String seatLine;
            while ((seatLine = brMovies.readLine()) != null) {
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessSeat(seatLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ProcessUser implements Runnable {
        private String userLine;

        ProcessUser(String userLine) {
            this.userLine = userLine;
        }

        @Override
        public void run() {
            String[] userInfo = userLine.split(",");

            User user = new User();
            user.setId(Long.parseLong(userInfo[0]));
            user.setUsername(userInfo[1]);
            user.setPassword(userInfo[2]);

            userRepository.save(user);
        }
    }

    private void populateUserTable() {
        try (BufferedReader brMovies = new BufferedReader(new InputStreamReader(new ClassPathResource("users.small.csv").getInputStream()));
        ) {
            String seatLine;
            while ((seatLine = brMovies.readLine()) != null) {
                //taskExecutor.execute(new ProcessMovie(movieLine, linkLine));
                new ProcessUser(seatLine).run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        populateMovieTable();
        populateCityTable();
        populateTheatreTable();
        populateScreenTable();
        populateShowTable();
        populateSeatTable();
        populateUserTable();
    }
}
