package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "SCREEN")
@Getter
@Setter
@ToString
public class Screen {
    @Id
    @Column(name = "SCREEN_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long screenId;
    @Column(name = "SEATS_NUM", nullable = false)
    private int seatsNum;

    @Column(name = "SCREEN_NAME", nullable = false)
    private String screeName;

    @Column(name = "THEATRE_ID", nullable = false)
    private Long theatreId;

    @OneToMany
    @JoinColumn(name = "SCREEN_ID")
    private List<Seat> seats;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "SCREEN_ID")
    private List<Show> shows;


    public List<Show> getShowsByMovieAndSelectedDate(Long movieId, LocalDate selectedDate) {
        return this.getShows().stream().filter(show ->
                show.isShowOfSameDateAndMovie(movieId, selectedDate)).toList();
    }
}
