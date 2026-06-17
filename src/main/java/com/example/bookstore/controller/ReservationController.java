package com.example.bookstore.controller;

import com.example.bookstore.dto.CreateReservationRequest;
import com.example.bookstore.entity.Reservation;
import com.example.bookstore.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody CreateReservationRequest request) {
        return reservationService.createReservation(request);
    }
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
}