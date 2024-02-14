package com.sapient.bookmymovie.data.repository;

import com.sapient.bookmymovie.data.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository  extends JpaRepository<Show, Integer> {

}
