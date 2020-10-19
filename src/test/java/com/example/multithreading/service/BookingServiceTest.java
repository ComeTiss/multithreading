package com.example.multithreading.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
public class BookingServiceTest {

    @Autowired private BookingServiceImpl bookingService;

    @Test
    public void testBooking() {
        try {

            log.info(">>>>>>>>>>>>>>>> TEST START <<<<<<<<<<<<<<<<<");

            // Fire N requests to the service
            final int N = 10;
            int waitingCount = N;
            ArrayList<CompletableFuture<String>> cf_list = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                cf_list.add(bookingService.bookTicketAsync("Movie : " + i));
            }

            // Waiting for all requests to be done executing
            while (waitingCount != 0) {
                for (int i=0; i<cf_list.size(); i++) {
                    CompletableFuture<String> cf = cf_list.get(i);
                    if (cf.isDone()) {
                        cf_list.remove(cf);
                        waitingCount--;
                    }
                }
            }

            log.info(">>>>>>>>>>>>>>>> TEST END <<<<<<<<<<<<<<<<<");

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
