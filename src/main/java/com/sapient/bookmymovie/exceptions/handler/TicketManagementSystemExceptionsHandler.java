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
        return this.getResponse(ex.getMsg(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidUserFoundForBookingException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            InvalidUserFoundForBookingException invalidUserFoundForBookingException) {
        logger.error("Invalid User ex:{}", invalidUserFoundForBookingException);
        return this.getResponse(invalidUserFoundForBookingException.getMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidBookingException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            InvalidBookingException invalidBookingException) {
        logger.error("Invalid Booking ex:{}", invalidBookingException);
        return this.getResponse(invalidBookingException.getMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SeatPermanentlyUnavailableException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidUserException(
            SeatPermanentlyUnavailableException seatPermanentlyUnavailableException) {
        logger.error("Seat Are Unavailable For Booking ex:{}", seatPermanentlyUnavailableException);
        return this.getResponse(seatPermanentlyUnavailableException.getMsg(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({SeatTemporaryUnavailableException.class})
    protected ResponseEntity<BaseExceptionResponse> handleSeatTemporaryUnavailableException(
            SeatTemporaryUnavailableException seatTemporaryUnavailableException) {
        logger.error("Some Seats are locked For Booking ex:{}", seatTemporaryUnavailableException);
        return this.getResponse(seatTemporaryUnavailableException.getMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDateException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidDateException(
            InvalidDateException invalidDateException) {
        logger.error("Invalid Date Selected. Past Dates are not supported. Ex:{}", invalidDateException);
        return this.getResponse(invalidDateException.getMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SeatNotLockedException.class})
    protected ResponseEntity<BaseExceptionResponse> handleInvalidDateException(
            SeatNotLockedException seatNotLockedException) {
        logger.error("Invalid Seat Selected. Seat should be locked while confirmation. Ex:{}", seatNotLockedException);
        return this.getResponse(seatNotLockedException.getMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PastDateTicketBookingNotAllowedException.class})
    protected ResponseEntity<BaseExceptionResponse> handlePastDateException(
            PastDateTicketBookingNotAllowedException pastDateTicketBookingNotAllowedException) {
        logger.error("Invalid Date Selected.  Ex:{}", pastDateTicketBookingNotAllowedException);
        return this.getResponse(pastDateTicketBookingNotAllowedException.getMsg(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoTheatreFoundException.class})
    protected ResponseEntity<BaseExceptionResponse> handleNoTheatreFoundException(
            NoTheatreFoundException noTheatreFoundException) {
        logger.error("NO Theatre Found. Ex:{}", noTheatreFoundException);
        return this.getResponse(noTheatreFoundException.getMsg(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoShowsFoundForCityAndDateException.class})
    protected ResponseEntity<BaseExceptionResponse> handleNoShowsFoundException(
            NoShowsFoundForCityAndDateException noShowsFoundForCityAndDateException) {
        logger.error("No Shows Found. Ex:{}", noShowsFoundForCityAndDateException);
        return this.getResponse(noShowsFoundForCityAndDateException.getMsg(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<BaseExceptionResponse> getResponse(String message, HttpStatus httpStatus) {
        BaseExceptionResponse baseExceptionResponse = BaseExceptionResponse.builder().status(httpStatus.toString())
                .message(message).statusCode(httpStatus.value()) .build();
        return new ResponseEntity<>(baseExceptionResponse, httpStatus);
    }
}
