package com.example.multithreading.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Document
@Getter
public class Booking {

    @Id
    private String id;

    @Setter
    @Indexed
    private String movieId;
    @Setter
    private String user_fullName;
    @Setter
    private int seats;

    public Booking(String user_fullName, int seats, String movieId) {
        this.user_fullName = user_fullName;
        this.seats = seats;
        this.movieId = movieId;
    }
}
