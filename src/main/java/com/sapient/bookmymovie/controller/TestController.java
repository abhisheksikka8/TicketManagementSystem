package com.sapient.bookmymovie.controller;

import com.sapient.bookmymovie.data.repository.CityRepository;
import com.sapient.bookmymovie.data.repository.MovieRepository;
import com.sapient.bookmymovie.data.repository.ShowRepository;
import com.sapient.bookmymovie.data.repository.TheatreRepository;
import com.sapient.bookmymovie.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class TestController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheatreService theatreService;
    @RequestMapping(method = RequestMethod.GET, path = "/test")
    public String test() {
        System.out.printf("Cities: \n" + cityRepository.findAll());
        System.out.printf("\n Theatres: " + theatreRepository.findAll() + "\nTheat from City: \n");
        //cityRepository.findAll().forEach(city -> city.getTheatres().forEach(System.out :: println));

        //movieRepository.findAll().forEach(System.out::println);

        showRepository.findAll().forEach(System.out::println);
        //System.out.printf("Movies: \n" + movieRepository.findAll());
        theatreService.getShowsForMovie(1, 3l, LocalDate.of(2024, Month.JANUARY, 3));

        List<Integer> test = Arrays.asList(1,2,3,4,5);

        List<Integer> test2 = Arrays.asList(3,2,1);

        test.stream().filter(test2::contains).toList();

        test.stream().collect(Collectors.groupingBy(Function.identity(),
                Collectors.counting())).entrySet().stream().filter(entry ->entry.getValue() > 1).toList();
        return "Test";
    }
}
