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

		//SpringApplication.run(BookMyMovieApplication.class, args);

		List<Integer> test = Arrays.asList(2,4,5,6,4,2);

		test.stream().collect(Collectors.groupingBy(Function.identity(),
				Collectors.counting())).entrySet().stream().filter(entry ->entry.getValue() > 1).toList().forEach(System.out::println);

		String input = "Aava articles are Awesome";

		System.out.println("Char: " + input.chars().mapToObj(s -> Character.toLowerCase(Character.valueOf((char)(s)))).collect(Collectors.groupingBy(Function.identity(),
						LinkedHashMap::new, Collectors.counting())).entrySet().stream().filter(characterLongEntry ->
				characterLongEntry.getValue() == 1).map(Map.Entry::getKey).findFirst().get());

		System.out.printf("Test ... ");

		List<String> names = Arrays.asList("AA", "BB", "AA", "CC");


		names.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().filter(stringLongEntry -> stringLongEntry.getValue()>1).collect(Collectors.toList()).forEach(System.out::println);
	}
}
