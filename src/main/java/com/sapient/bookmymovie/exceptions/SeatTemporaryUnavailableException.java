package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SeatTemporaryUnavailableException extends RuntimeException {
    private String msg;
}
