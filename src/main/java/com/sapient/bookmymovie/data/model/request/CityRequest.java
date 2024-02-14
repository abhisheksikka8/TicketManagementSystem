package com.sapient.bookmymovie.data.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CityRequest {
    private String cityName;

    private String cityCode;

    private Double longitude;

    private Double latitude;
}
