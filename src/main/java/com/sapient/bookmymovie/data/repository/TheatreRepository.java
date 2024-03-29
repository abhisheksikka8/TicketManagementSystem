package com.sapient.bookmymovie.data.repository;

import com.sapient.bookmymovie.data.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Theatre findByTheatreId(Long theatreId);

    List<Theatre> findAllByTheatreCity(Integer theaterId);
}
