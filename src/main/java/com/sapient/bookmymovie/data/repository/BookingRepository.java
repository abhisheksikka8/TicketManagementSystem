package com.sapient.bookmymovie.data.repository;

import com.sapient.bookmymovie.data.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
        Optional<List<Booking>> findAllByShowId(Long showId);
}
