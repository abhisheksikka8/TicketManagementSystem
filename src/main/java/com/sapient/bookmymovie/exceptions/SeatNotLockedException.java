package com.sapient.bookmymovie.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SeatNotLockedException extends RuntimeException {
    private String msg;
}
