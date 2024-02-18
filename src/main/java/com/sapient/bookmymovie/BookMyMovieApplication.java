package com.sapient.bookmymovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class BookMyMovieApplication {
	public static void main(String[] args) {

		SpringApplication.run(BookMyMovieApplication.class, args);
	}
}
