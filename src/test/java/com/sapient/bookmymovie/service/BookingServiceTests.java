package com.sapient.bookmymovie.service;


import com.sapient.bookmymovie.constants.BookingStatus;
import com.sapient.bookmymovie.constants.PaymentStatus;
import com.sapient.bookmymovie.data.entity.Booking;
import com.sapient.bookmymovie.data.entity.Seat;
import com.sapient.bookmymovie.data.model.request.BookingRequest;
import com.sapient.bookmymovie.data.model.request.PostPaymentBookingRequest;
import com.sapient.bookmymovie.data.model.response.BookingResponse;
import com.sapient.bookmymovie.data.repository.BookingRepository;
import com.sapient.bookmymovie.data.repository.SeatRepository;
import com.sapient.bookmymovie.exceptions.InvalidUserFoundForBookingException;
import com.sapient.bookmymovie.exceptions.SeatPermanentlyUnavailableException;
import com.sapient.bookmymovie.providers.SeatLockProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    void testSeatBookingForConfirmation() {
            BookingRequest bookingRequest = getBookingRequest();

    }

    @Test
    void testSeatBookingForCreationSuccess() {
        List<Seat> seats = this.getSeats();

        Booking intiatedBooking = new Booking();
        intiatedBooking.setBookingStatus(BookingStatus.Created);
        intiatedBooking.setSeatsBooked(seats);
        intiatedBooking.setBookingId(1l);
        intiatedBooking.setShowId(1l);
        intiatedBooking.setUserId(1l);

        PostPaymentBookingRequest confirmBookingReq = new PostPaymentBookingRequest();
        confirmBookingReq.setBookingId(1l);
        confirmBookingReq.setTransactionId("testTrans1");
        confirmBookingReq.setUserId(1l);
        confirmBookingReq.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);

        Mockito.when(bookingRepository.findById(confirmBookingReq.getBookingId())).thenReturn(Optional.of(intiatedBooking));

        Mockito.when(seatLockProvider.validateLock(1l, 1l,1l)).thenReturn(true);
        Mockito.when(seatLockProvider.validateLock(1l, 2l,1l)).thenReturn(true);

        Booking confirmedBooking = new Booking();
        confirmedBooking.setBookingId(intiatedBooking.getBookingId());
        confirmedBooking.setUserId(intiatedBooking.getUserId());
        confirmedBooking.setShowId(intiatedBooking.getShowId());
        confirmedBooking.setSeatsBooked(intiatedBooking.getSeatsBooked());
        confirmedBooking.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
        confirmedBooking.setBookingStatus(BookingStatus.Confirmed);

        Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(confirmedBooking);

        BookingResponse bookingResponse = bookingService.confirmBooking(confirmBookingReq);

        Assertions.assertEquals(BookingStatus.Confirmed, bookingResponse.getBookingStatus());
        Assertions.assertEquals(1L, bookingResponse.getBookingId());
        Assertions.assertEquals(1L, bookingResponse.getUserId());
    }
    
    @Test
    void testSeatsAlreadyBooked() {
        Booking finalBooking = getBooking();
        finalBooking.setBookingStatus(BookingStatus.Confirmed);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId(1l);
        bookingRequest.setShowId(1l);
        bookingRequest.setSeats(Arrays.asList(1l, 2l));
        
        Mockito.when(bookingRepository.findAllByShowId(1l)).thenReturn(Optional.of(Arrays.asList(finalBooking)));

        Assertions.assertThrows(SeatPermanentlyUnavailableException.class, () -> bookingService.createBooking(bookingRequest));
    }

    private static Booking getBooking() {
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

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.Created);
        booking.setSeatsBooked(Arrays.asList(seat, seat2));
        booking.setBookingId(1l);
        return booking;
    }

    @Test
    void testSeatBookingConfirmationForInvalidUser() {
        List<Seat> seats = this.getSeats();

        Booking intiatedBooking = new Booking();
        intiatedBooking.setBookingStatus(BookingStatus.Created);
        intiatedBooking.setSeatsBooked(seats);
        intiatedBooking.setBookingId(1l);
        intiatedBooking.setShowId(1l);
        intiatedBooking.setUserId(1l);

        PostPaymentBookingRequest confirmBookingReq = new PostPaymentBookingRequest();
        confirmBookingReq.setBookingId(1l);
        confirmBookingReq.setTransactionId("testTrans1");
        confirmBookingReq.setUserId(2l);
        confirmBookingReq.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);

        Mockito.when(bookingRepository.findById(confirmBookingReq.getBookingId())).thenReturn(Optional.of(intiatedBooking));

        Assertions.assertThrows(InvalidUserFoundForBookingException.class, () -> bookingService.confirmBooking(confirmBookingReq));
    }

    private BookingRequest getBookingRequest() {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId(1l);
        bookingRequest.setShowId(1l);
        bookingRequest.setSeats(Arrays.asList(1l, 2l));

        return bookingRequest;
    }

    private List<Seat> getSeats() {
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

        return Arrays.asList(seat, seat2);
    }
}
