package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoShowsFoundForCityAndDateException extends RuntimeException {
    private String msg;
}
