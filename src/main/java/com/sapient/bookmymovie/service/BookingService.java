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
import com.sapient.bookmymovie.exceptions.*;
import com.sapient.bookmymovie.providers.SeatLockProvider;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class BookingService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatLockProvider seatLockProvider;

    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        if(checkAnySeatAlreadyBooked(bookingRequest)) {
            throw new SeatPermanentlyUnavailableException("Selected seats already booked.");
        }

        seatLockProvider.lockSeats(bookingRequest);

        //lock the seats to be booked
        List<Seat> seatsToBeBooked = seatRepository.findAllBySeatIdIn(bookingRequest.getSeats());

        Booking booking = Booking.builder().userId(bookingRequest.getUserId()).bookingStatus(BookingStatus.Created)
                .paymentStatus(PaymentStatus.PAYMENT_INITIATED_AT_GATEWAY).showId(bookingRequest.getShowId())
                        .seatsBooked(seatsToBeBooked) .build();

        log.info("Saving Booking: " + booking);

        Booking finalBooking = bookingRepository.save(booking);

        log.info("Final Booking : \n" + finalBooking );
        // Initiate Payment Here - Connect with Payment Gateways ...
        return BookingResponse.builder().bookingId(finalBooking.getBookingId()).userId(finalBooking.getUserId())
                .bookingStatus(BookingStatus.Created).build();
    }

    @Transactional
    public BookingResponse confirmBooking(PostPaymentBookingRequest bookingRequest) {
        Optional<Booking> booking = bookingRepository.findById(bookingRequest.getBookingId());

        this.validatePostPaymentBooking(booking, bookingRequest);

        Booking initiatedBooking = booking.get();

        initiatedBooking.setPaymentStatus(bookingRequest.getPaymentStatus());
        initiatedBooking.setTransactionId(bookingRequest.getTransactionId());

        if (bookingRequest.getPaymentStatus().equals(PaymentStatus.PAYMENT_FAILED)) {
            initiatedBooking.setBookingStatus(BookingStatus.Cancelled);
        } else {
            initiatedBooking.setBookingStatus(BookingStatus.Confirmed);
        }

        Booking confirmedBooking = bookingRepository.save(initiatedBooking);

        log.info("Confirmed Booking : " + confirmedBooking);

        seatLockProvider.unlockSeats(initiatedBooking.getSeatsBooked().stream().map(Seat::getSeatId).toList(),
                initiatedBooking.getShowId(), bookingRequest.getUserId());

        return BookingResponse.builder().bookingStatus(confirmedBooking.getBookingStatus()).bookingId(confirmedBooking.getBookingId())
                .userId(confirmedBooking.getUserId()).build();
    }

    public Optional<Booking> getBooking(@NonNull final Long bookingId) {
       return bookingRepository.findById(bookingId);
    }

    public Optional<List<Booking>> getBookingsForShow(@NonNull final Long showId) {

        Optional<List<Booking>> bookingsForShow = bookingRepository.findAllByShowId(showId);

        if(bookingsForShow.isEmpty()) {
            log.info("No Bookings found for show");
        }

        return bookingsForShow;
    }


    private boolean checkAnySeatAlreadyBooked(BookingRequest bookingRequest) {
        Optional<List<Long>> bookedSeats = this.getBookedSeatsForShow(bookingRequest.getShowId());

        if(bookedSeats.isEmpty()) {
            log.info( "No Seats Booked for Show: " + bookingRequest.getShowId());
            return false;
        }

        long count = bookingRequest.getSeats().stream().filter(seat -> bookedSeats.get().contains(seat)).count();
        return count >0;
    }

    private Optional<List<Long>> getBookedSeatsForShow(@NonNull final Long showId) {
        Optional<List<Booking>> showBookings = bookingRepository.findAllByShowId(showId);

        return showBookings.map(bookings -> bookings.stream()
                .filter(Booking::isConfirmed)
                .map(Booking::getSeatsBooked)
                .flatMap(Collection::stream).map(Seat::getSeatId)
                .collect(Collectors.toList()));
    }

    private void validatePostPaymentBooking(Optional<Booking> booking, PostPaymentBookingRequest bookingRequest) {

        if(booking.isEmpty()) {
            throw new NoBookingPresentException("No Booking found for Booking Req: " + bookingRequest);
        }

        Booking initiatedBooking = booking.get();

        if(!initiatedBooking.getUserId().equals(bookingRequest.getUserId())) {
            throw new InvalidUserFoundForBookingException("Invalid User Found for Booking Req: " + bookingRequest);
        }

        if(!initiatedBooking.getBookingStatus().equals(BookingStatus.Created)) {
            throw new InvalidBookingException("Invalid Booking Confirmation: " + bookingRequest);
        }

        booking.get().getSeatsBooked().stream().filter(seat -> !seatLockProvider.validateLock(initiatedBooking.getShowId(),
                seat.getSeatId(), initiatedBooking.getUserId())).findAny().ifPresent(seat -> {
                    log.info("Invalid Seat Status for Seat : " + seat);
                    throw new SeatNotLockedException("Seats should be locked.");
                } );
    }
}
