package com.sapient.bookmymovie.data.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
public class BookingResponse {
    private Long bookingId;
    private Long userId;
}
