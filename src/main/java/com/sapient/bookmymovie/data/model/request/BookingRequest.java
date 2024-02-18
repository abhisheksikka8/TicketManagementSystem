package com.sapient.bookmymovie.data.model.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class BookingRequest {

    @NonNull
    private Long userId;
    @NonNull
    private Long showId;
    @NonNull 
    private List<Long> seats;
    @NonNull
    private LocalDate bookingDate;
}
