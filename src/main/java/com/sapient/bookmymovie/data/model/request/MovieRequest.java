package com.sapient.bookmymovie.data.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MovieRequest {
    private String movieName;
    private String moviePosterUrl;
    private String movieTags;
}
