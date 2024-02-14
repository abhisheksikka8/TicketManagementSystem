package com.sapient.bookmymovie.data.repository;

import com.sapient.bookmymovie.data.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllBySeatIdIn(List<Long> seatId);
}
