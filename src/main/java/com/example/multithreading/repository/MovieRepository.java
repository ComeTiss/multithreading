package com.example.multithreading.repository;

import com.example.multithreading.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    Optional<Movie> findOneByName(String name);
}
