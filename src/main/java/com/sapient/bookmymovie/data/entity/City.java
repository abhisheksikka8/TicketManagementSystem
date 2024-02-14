package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "City")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class City {

    @Id
    @Column(name = "CITY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cityId;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "CITY_CODE")
    private String cityCode;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "LATITUDE")
    private Double latitude;

    @OneToMany
    @JoinColumn(name = "THEATRE_CITY_ID")
    List<Theatre> theatres;
}
