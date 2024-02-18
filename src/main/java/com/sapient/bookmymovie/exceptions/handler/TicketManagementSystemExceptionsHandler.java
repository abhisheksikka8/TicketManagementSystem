package com.sapient.bookmymovie.exceptions.handler;

import com.sapient.bookmymovie.data.model.response.BaseExceptionResponse;
import com.sapient.bookmymovie.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TicketManagementSystemExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoBookingPresentException.class})
    protected ResponseEntity<BaseExceptionResponse> handleNoBookingPresentException(
            NoBookingPresentException ex) {
        logger.error("No Booking Found ex:{}", ex);
        return this.getResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidUserFoundForBookingException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            InvalidUserFoundForBookingException invalidUserFoundForBookingException) {
        logger.error("Invalid User ex:{}", invalidUserFoundForBookingException);
        return this.getResponse(invalidUserFoundForBookingException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidBookingException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            InvalidBookingException invalidBookingException) {
        logger.error("Invalid Booking ex:{}", invalidBookingException);
        return this.getResponse(invalidBookingException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SeatPermanentlyUnavailableException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            SeatPermanentlyUnavailableException seatPermanentlyUnavailableException) {
        logger.error("Seat Are Unavailable For Booking ex:{}", seatPermanentlyUnavailableException);
        return this.getResponse(seatPermanentlyUnavailableException, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({SeatTemporaryUnavailableException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            SeatTemporaryUnavailableException seatTemporaryUnavailableException) {
        logger.error("Some Seats are locked For Booking ex:{}", seatTemporaryUnavailableException);
        return this.getResponse(seatTemporaryUnavailableException, HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler({InvalidDateException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidDateException(
            InvalidDateException invalidDateException) {
        logger.error("Invalid Date Selected. Past Dates are not supported. Ex:{}", invalidDateException);
        return this.getResponse(invalidDateException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoTheatreFoundException.class})
    protected ResponseEntity<BaseExceptionResponse> handleNoTheatreFoundException(
            NoTheatreFoundException noTheatreFoundException) {
        logger.error("NO Theatre Found. Ex:{}", noTheatreFoundException);
        return this.getResponse(noTheatreFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoShowsFoundForCityAndDateException.class})
    protected ResponseEntity<BaseExceptionResponse> handleNoShowsFoundException(
            NoShowsFoundForCityAndDateException noShowsFoundForCityAndDateException) {
        logger.error("No Shows Found. Ex:{}", noShowsFoundForCityAndDateException);
        return this.getResponse(noShowsFoundForCityAndDateException, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<BaseExceptionResponse> getResponse(RuntimeException ex, HttpStatus httpStatus) {
        BaseExceptionResponse baseExceptionResponse = BaseExceptionResponse.builder().status(httpStatus.toString())
                .message(ex.getMessage()).statusCode(httpStatus.value()) .build();
        return new ResponseEntity<>(baseExceptionResponse, httpStatus);
    }
}
