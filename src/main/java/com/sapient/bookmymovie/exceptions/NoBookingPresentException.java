package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoBookingPresentException extends RuntimeException {
    private String msg;
}
