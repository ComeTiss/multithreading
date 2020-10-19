package com.example.multithreading.service;

import com.example.multithreading.model.Booking;
import com.example.multithreading.model.Movie;
import com.example.multithreading.repository.BookingRepository;
import com.example.multithreading.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private MovieRepository movieRepository;

    private final static Object lock = new Object();

    @Async
    @Override
    public CompletableFuture<Booking> bookTicketsAsync(String movieName, int seats, String user_fullName) {
        log.info("bookTicketsAsync/currentThread=" + Thread.currentThread().getName());
        log.info("bookTicketsAsync/movieName=" + movieName);
        log.info("bookTicketsAsync/seats=" + seats);
        log.info("bookTicketsAsync/user_fullName=" + user_fullName);

        Optional<Movie> movieOpt = movieRepository.findOneByName(movieName);

        if (movieOpt.isPresent()) {
            synchronized (lock) {
                String movieId = movieOpt.get().getId();
                List<Booking> bookings = bookingRepository.findByMovieId(movieId);
                int seatsAlreadyBooked = calculateSeatsTotalFromBookings(bookings);
                int seatsRemaining = movieOpt.get().getCapacity() - seatsAlreadyBooked;

                log.info("bookTicketsAsync/seats remaining=" + seatsRemaining);

                if (seatsRemaining >= seats) {
                    return CompletableFuture.supplyAsync(() -> {
                        log.info("bookTicketsAsync/BOOKING DONE");
                        return bookingRepository.save(new Booking(user_fullName, seats, movieId));
                    });
                }
                log.info("bookTicketsAsync/BOOKING ABORTED");
            }
        } else {
            log.info("bookTicketsAsync/MOVIE NOT FOUND");
        }
        return null;
    }

    private int calculateSeatsTotalFromBookings(List<Booking> bookings) {
        if (bookings.size() == 0) {
            return 0;
        }

        int count = 0;
        for (Booking b : bookings) {
            count += b.getSeats();
        }
        return count;
    }
}
