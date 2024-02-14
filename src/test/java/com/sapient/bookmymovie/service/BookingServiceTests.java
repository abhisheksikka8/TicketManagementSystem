package com.sapient.bookmymovie.service;


import com.sapient.bookmymovie.constants.BookingStatus;
import com.sapient.bookmymovie.data.entity.Booking;
import com.sapient.bookmymovie.data.entity.Seat;
import com.sapient.bookmymovie.data.model.request.BookingRequest;
import com.sapient.bookmymovie.data.repository.BookingRepository;
import com.sapient.bookmymovie.data.repository.SeatRepository;
import com.sapient.bookmymovie.exceptions.SeatPermanentlyUnavailableException;
import com.sapient.bookmymovie.providers.SeatLockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTests {
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private SeatLockProvider seatLockProvider;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testSeatBooking() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId(1l);
        bookingRequest.setShowId(1l);
        bookingRequest.setSeats(Arrays.asList(1l, 2l));

        Seat seat = new Seat();
        seat.setScreenId(1l);
        seat.setRowNumber(1);
        seat.setRowId('A');
        seat.setSeatId(1l);

        Seat seat2 = new Seat();
        seat2.setScreenId(1l);
        seat2.setRowNumber(1);
        seat2.setRowId('B');
        seat2.setSeatId(2l);
        
        Mockito.when(seatRepository.findAllBySeatIdIn(Arrays.asList(1l, 2l))).thenReturn(  Arrays.asList(seat, seat2));
        Mockito.when(bookingRepository.findAllByShowId(1l)).thenReturn(Optional.empty());

        Booking finalBooking = new Booking();
        finalBooking.setBookingStatus(BookingStatus.Confirmed);
        finalBooking.setSeatsBooked(Arrays.asList(seat, seat2));
        finalBooking.setBookingId(1l);
        Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(finalBooking);

        Assertions.assertEquals(1l,  bookingService.createBooking(bookingRequest).getBookingId());
    }
    
    @Test
    void testSeatsAlreadyBooked() {
        Seat seat = new Seat();
        seat.setScreenId(1l);
        seat.setRowNumber(1);
        seat.setRowId('A');
        seat.setSeatId(1l);

        Seat seat2 = new Seat();
        seat2.setScreenId(1l);
        seat2.setRowNumber(1);
        seat2.setRowId('B');
        seat2.setSeatId(2l);

        Booking finalBooking = new Booking();
        finalBooking.setBookingStatus(BookingStatus.Confirmed);
        finalBooking.setSeatsBooked(Arrays.asList(seat, seat2));
        finalBooking.setBookingId(1l);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId(1l);
        bookingRequest.setShowId(1l);
        bookingRequest.setSeats(Arrays.asList(1l, 2l));
        
        Mockito.when(bookingRepository.findAllByShowId(1l)).thenReturn(Optional.of(Arrays.asList(finalBooking)));
    
        Assertions.assertThrows(SeatPermanentlyUnavailableException.class, () -> bookingService.createBooking(bookingRequest));
    }
}
