package com.sapient.bookmymovie.providers;

import com.sapient.bookmymovie.data.model.SeatLock;
import com.sapient.bookmymovie.data.model.request.BookingRequest;
import com.sapient.bookmymovie.exceptions.SeatTemporaryUnavailableException;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Log
@Component
public class InMemorySeatLockProvider implements SeatLockProvider {
    @Value("${lock.timeout}")
    private Integer lockTimeout;
    private Map<Long, Map<Long, SeatLock>> locks = new ConcurrentHashMap<>();

    @Override
     public void lockSeats(BookingRequest bookingRequest) {
        bookingRequest.getSeats().stream().filter(seat -> this.isSeatLocked(bookingRequest.getShowId(), seat)).findAny().ifPresent(seat -> {
            log.info("Seat Is Locked: " + seat);
            throw new SeatTemporaryUnavailableException("Seat Temporarily Unavailable");
        });

        log.info(String.format("Locking Seats for Booking Request: %s, ", bookingRequest));
        bookingRequest.getSeats().forEach(seat -> this.lockSeat(bookingRequest.getShowId(), seat, bookingRequest.getUserId(), lockTimeout));
    }

    @Override
    public void unlockSeats(List<Long> seatsIds, Long showId, Long userId) {
        seatsIds.forEach(seat -> {
            if(validateLock(showId, seat, userId)) {
                this.unlockSeat(showId, seat);
            }
        });
    }

    /**
     * Validates if the seat is locked by the current user
     * @param show Show Details
     * @param seat Seat Selected
     * @param user User Trying to lock the seat
     * @return true/false depending on if the seat is locked
     */
    @Override
    public boolean validateLock(@NonNull final Long show, @NonNull final Long seat, @NonNull final Long user) {
        return isSeatLocked(show, seat) && locks.get(show).get(seat).getLockedBy().equals(user);
    }

    /**
     * Fetches Locked Seats for the Show
     * It checks the Map for the Locked seats per show and also checks if the lock is still intact by checking lock timeouts !!
     * @param show Show for which the locked seats are required
     * @return List of Locked Seats for the Show
     */
    @Override
    public Optional<List<Long>> getLockedSeats(@NonNull final Long show) {
        if (!locks.containsKey(show)) {
            log.info("No Seats locked for Show");
            return Optional.empty();
        }

        Map<Long, SeatLock> lockedSeatsMap = locks.get(show);
        return Optional.of(lockedSeatsMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isLockExpired()).map(Map.Entry::getKey).toList());
    }

    /**
     * Unlocks the Seat for the specific Show
     * @param show Show for which the seat is supposed to be unlocked
     * @param seat Seat which is to be unlocked
     */
    private void unlockSeat(final Long show, final Long seat) {
        if (locks.containsKey(show)) {
            log.info("Unlocking Seat: " + seat);
            locks.get(show).remove(seat);
        }

        log.info("Seat Selected is Not Locked: " + seat);
    }

    /**
     * Method for locking the seat for the specific show by the requested user
     * This also map the timeout in Seconds
     * @param showId Show for which the seat has to be locked
     * @param seatId Seat which is to be locked
     * @param user User requesting the seat lock
     * @param timeoutInSeconds Timeout for the lock
     */
    private void lockSeat(final Long showId, final Long seatId, final Long user, final Integer timeoutInSeconds) {
        log.info(String.format("Locking Seat for Show: %d, Seat : %d, Requested by User: %d", showId, seatId, user));
        final SeatLock lock = new SeatLock(timeoutInSeconds, new Date(), user);

        if (!locks.containsKey(showId)) {
            log.info("No Seats Locked for Show: " + showId);
            locks.put(showId, new ConcurrentHashMap<>());
        }

        locks.get(showId).put(seatId, lock);
    }

    private Boolean isSeatLocked(final Long show, final Long seat) {
        log.info(String.format("Checking Lock for Seat: %d for Show :%d", seat, show));
        return locks.containsKey(show) && locks.get(show).containsKey(seat) && !locks.get(show).get(seat).isLockExpired();
    }
}
