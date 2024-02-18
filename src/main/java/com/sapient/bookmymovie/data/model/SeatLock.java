package com.sapient.bookmymovie.data.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@Getter
public class SeatLock {
    private Integer timeoutInMilliSeconds;
    private Date lockTime;
    private Long lockedBy;

    public boolean isLockExpired() {
        final Instant lockInstant = lockTime.toInstant().plusMillis(timeoutInMilliSeconds);
        final Instant currentInstant = new Date().toInstant();
        return lockInstant.isBefore(currentInstant);
    }
}
