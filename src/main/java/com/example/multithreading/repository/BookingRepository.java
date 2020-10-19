package com.example.multithreading.repository;

import com.example.multithreading.model.Booking;
import com.example.multithreading.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByMovieId(String movieId);
}
