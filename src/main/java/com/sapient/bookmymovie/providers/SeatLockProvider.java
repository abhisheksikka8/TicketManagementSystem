package com.sapient.bookmymovie.providers;


import com.sapient.bookmymovie.data.model.request.BookingRequest;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface SeatLockProvider {

    void lockSeats(BookingRequest bookingRequest);
    void unlockSeats(List<Long> seatsId, Long showId, Long userId);
    boolean validateLock(@NonNull final Long show, @NonNull final Long seat, @NonNull final Long user);

    Optional<List<Long>> getLockedSeats(Long show);
}
