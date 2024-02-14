package com.sapient.bookmymovie.service;

import com.sapient.bookmymovie.data.entity.Seat;
import com.sapient.bookmymovie.data.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    /*public List<Seat> getSeatsForSelectedShow(Long screenId, LocalDate selectedDate) {

    }*/
}
