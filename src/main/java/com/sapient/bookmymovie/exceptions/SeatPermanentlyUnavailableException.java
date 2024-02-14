package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SeatPermanentlyUnavailableException extends RuntimeException {
    private String msg;
}
