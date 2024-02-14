package com.sapient.bookmymovie.service;

import com.sapient.bookmymovie.data.entity.City;
import com.sapient.bookmymovie.data.entity.Movie;
import com.sapient.bookmymovie.data.entity.Theatre;
import com.sapient.bookmymovie.data.model.request.CityRequest;
import com.sapient.bookmymovie.data.repository.CityRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    public City addCity(CityRequest cityRequest) {
        City city = City.builder().cityCode(cityRequest.getCityCode()).cityName(cityRequest.getCityName())
                .latitude(cityRequest.getLatitude()).longitude(cityRequest.getLongitude()).build();

        log.info(String.format("Adding New City: {}", city));
        return cityRepository.save(city);
    }

    public List<Movie> getMoviesInCity(String cityName) {
        List<Theatre> theatres = cityRepository.findTheatersByCityName(cityName);

        //To Be Completed !!

        return null;
       // theatres.forEach(theatre -> theatre.getScreens().forEach(screen -> screen.getShows().stream().map(screen -> screen.)));
    }
}
