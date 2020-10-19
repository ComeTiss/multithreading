package com.example.multithreading.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Override
    public String bookTicket(String showName) {
        return "Ticket booked for: " + showName;
    }

    @Async
    @Override
    public CompletableFuture<String> bookTicketAsync(String showName) throws ExecutionException, InterruptedException {
        log.info("bookTicketAsync/showName=" + showName);
        log.info("bookTicketAsync/currentThreadName=" + Thread.currentThread().getName());
        log.info("bookTicketAsync/START sleep");
        Thread.sleep(1000L);
        log.info("bookTicketAsync/END sleep");
        return CompletableFuture.supplyAsync(() -> "|Async| Ticket booked for: " + showName);
    }
}
