package com.example.multithreading.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface BookingService {

    String bookTicket(String showName);

    CompletableFuture<String> bookTicketAsync(String showName) throws ExecutionException, InterruptedException;
}
