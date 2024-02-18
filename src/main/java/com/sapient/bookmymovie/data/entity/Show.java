package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "SHOWS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Show {
    @Id
    @Column(name = "SHOW_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long showId;

    @Column(name = "SCREEN_ID")
    private Long screenId;

    @Column(name = "MOVIE_ID")
    private Long movie;

    @Column(name = "SCREENING_DATE")
    private LocalDateTime screeningDate;

    @Column(name = "BOOKED_TICKETS")
    private int bookedTickets;

    public boolean isShowOfSameDateAndMovie(Long movieId, LocalDate showDate) {
        return this.getMovie().equals(movieId) && this.getScreeningDate().toLocalDate().equals(showDate);
    }
}
