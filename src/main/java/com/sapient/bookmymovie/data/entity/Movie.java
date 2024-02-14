package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "MOVIE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {

    @Id
    @Column(name = "MOVIE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long movieId;

    @Column(name = "MOVIE_NAME")
    private String movieName;

    @Column(name = "MOVIE_POSTER_URL")
    private String moviePosterUrl;

    @Column(name = "MOVIE_TAGS")
    private String movieTags;

}
