package com.example.multithreading.service;

import com.example.multithreading.model.Booking;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface BookingService {

    CompletableFuture<Booking> bookTicketsAsync(String movieName, int seats, String user_fullName);
}
