package com.sapient.bookmymovie.data.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BaseExceptionResponse {
    private String status;
    private Integer statusCode;
    private String message;
}
