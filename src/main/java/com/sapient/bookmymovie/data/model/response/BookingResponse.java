package com.sapient.bookmymovie.data.model.response;

import com.sapient.bookmymovie.constants.BookingStatus;
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
    private BookingStatus bookingStatus;
}
