package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Entity
@Table(name = "THEATRE")
@Getter
@Setter
@ToString
public class Theatre {
    @Id
    @Column(name = "THEATRE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long theatreId;
    @Column(name = "THEATRE_NAME")
    private String theatreName;

    @Column(name = "THEATRE_CITY_ID")
    private Integer theatreCity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "THEATRE_ID")
    private List<Screen> screens;
}
