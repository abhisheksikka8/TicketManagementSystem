package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoTheatreFoundException extends RuntimeException {

    private String msg;
}
