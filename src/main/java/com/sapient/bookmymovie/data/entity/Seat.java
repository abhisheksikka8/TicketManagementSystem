package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SEAT")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Seat {
    @Id
    @Column(name = "SEAT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long seatId;
    @Column(name = "ROW_ID")
    private char rowId;
    @Column(name = "SCREEN_ID")
    private long screenId;
    @Column(name = "ROW_NUMBER")
    private int rowNumber;
}
