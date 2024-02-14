package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidBookingException extends RuntimeException {
    private String msg;
}
