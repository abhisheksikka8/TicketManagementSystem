package com.sapient.bookmymovie.data.repository;


import com.sapient.bookmymovie.data.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    public Screen findByScreenId(long screenId);
    public List<Screen> findByTheatreId(long theatreId);
}
