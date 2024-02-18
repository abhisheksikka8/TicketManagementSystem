package com.sapient.bookmymovie.controller;

import com.sapient.bookmymovie.data.entity.Booking;
import com.sapient.bookmymovie.data.model.request.BookingRequest;
import com.sapient.bookmymovie.data.model.request.PostPaymentBookingRequest;
import com.sapient.bookmymovie.data.model.response.BookingResponse;
import com.sapient.bookmymovie.service.BookingService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest) {
        log.info("Creating Booking for : " + bookingRequest);
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }

    @PutMapping
    public ResponseEntity<BookingResponse> confirmBooking(@RequestBody PostPaymentBookingRequest bookingRequest) {
        log.info("Booking Confirmation Request: " + bookingRequest);

        return ResponseEntity.ok(bookingService.confirmBooking(bookingRequest));
    }

    @GetMapping(path = "/{bookingId}")
    public ResponseEntity<Booking> findByBookingId(@PathVariable  Long bookingId) {
        Optional<Booking> booking = bookingService.getBooking(bookingId);

        if(booking.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.of(bookingService.getBooking(bookingId));
    }

    @GetMapping(path = "/shows/{showId}")
    public ResponseEntity<List<Booking>> findBookingsByShowId(@PathVariable  Long showId) {
        Optional<List<Booking>> bookingsForShow = bookingService.getBookingsForShow(showId);

        return bookingsForShow.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
