package com.example.multithreading.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class Movie {

    @Id
    private String id;
    @Setter
    private String name;
    @Setter
    private int capacity;

    public Movie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
}
