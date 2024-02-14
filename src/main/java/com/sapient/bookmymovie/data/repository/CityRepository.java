package com.sapient.bookmymovie.data.repository;

import com.sapient.bookmymovie.data.entity.City;
import com.sapient.bookmymovie.data.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<Theatre> findTheatersByCityName (String cityName);

    List<Theatre> findTheatersByCityId(Integer cityId);

}
