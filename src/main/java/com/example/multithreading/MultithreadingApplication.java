package com.example.multithreading;

import com.example.multithreading.model.Booking;
import com.example.multithreading.model.Movie;
import com.example.multithreading.repository.BookingRepository;
import com.example.multithreading.repository.MovieRepository;
import com.example.multithreading.service.BookingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;

@Slf4j
@EnableAsync(proxyTargetClass=true)
@SpringBootApplication
public class MultithreadingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultithreadingApplication.class, args);
	}

	@Autowired private MovieRepository movieRepository;
	@Autowired private BookingRepository bookingRepository;
	@Autowired private BookingServiceImpl bookingService;

	@Override
	public void run(String... args) throws Exception {
		// Create Movie
		movieRepository.deleteAll();
		movieRepository.save(new Movie("Movie 1", 20));
		log.info("Movies count: " + movieRepository.findAll().size());

		bookingRepository.deleteAll();

		// Initializing lots of booking
		// Chances are high that we get a case where synchronization is required
		// -> to avoid having amount of booked seats > movie seats capacity
		CompletableFuture<Booking> b0 = bookingService.bookTicketsAsync("Movie Wrong", 5, "Mr White");
		CompletableFuture<Booking> b1 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White");
		CompletableFuture<Booking> b2 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 2");
		CompletableFuture<Booking> b3 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 3");
		CompletableFuture<Booking> b4 = bookingService.bookTicketsAsync("Movie 1", 3, "Mr White 4");
		CompletableFuture<Booking> b5 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b6 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b7 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b8 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b9 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b10 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b11 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b12 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b13 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");
		CompletableFuture<Booking> b14 = bookingService.bookTicketsAsync("Movie 1", 5, "Mr White 5");

		// Wait for futures to be all completed
		CompletableFuture.allOf(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14);
	}
}
