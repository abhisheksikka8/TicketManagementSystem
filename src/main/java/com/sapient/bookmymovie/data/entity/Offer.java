package com.sapient.bookmymovie.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Offers")
@Getter
@Setter
public class Offer {

    @Id
    @Column(name = "OFFER_ID")
    private Integer offerId;

    @Column
    private Double timeBasedDiscountPercentage;

    private Double moreSeatsBookedDiscountPercentage;

    @OneToOne
    private Theatre theatre;

    private String city;
}
