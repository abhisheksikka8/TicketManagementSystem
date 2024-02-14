package com.sapient.bookmymovie.data.entity;

import com.sapient.bookmymovie.constants.BookingStatus;
import com.sapient.bookmymovie.constants.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "BOOKING")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
    @Id
    @Column(name = "BOOKING_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;

   @Column(name = "SHOW_ID")
    private Long showId;

    @OneToMany
    @Column(name = "SEAT_ID")
    private List<Seat> seatsBooked;

    @Column(name="USER_ID")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "BOOKING_STATUS")
    private BookingStatus bookingStatus;

    @Column(name = "TRANSACTION_ID")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

    public boolean isConfirmed() {
        return this.bookingStatus == BookingStatus.Confirmed;
    }
}
