package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidBookingException extends RuntimeException {
    private String msg;
}
