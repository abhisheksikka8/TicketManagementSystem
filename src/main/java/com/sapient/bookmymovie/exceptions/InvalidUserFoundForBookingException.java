package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidUserFoundForBookingException extends RuntimeException {
    private String msg;
}
