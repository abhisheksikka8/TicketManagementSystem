package com.sapient.bookmymovie.data.model.request;

import com.sapient.bookmymovie.constants.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostPaymentBookingRequest {
    private Long bookingId;
    private Long userId;
    private String transactionId;
    private PaymentStatus paymentStatus;
}
