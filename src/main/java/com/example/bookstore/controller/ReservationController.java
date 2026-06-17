package com.example.bookstore.controller;

import com.example.bookstore.dto.CreateReservationRequest;
import com.example.bookstore.entity.Reservation;
import com.example.bookstore.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.bookstore.enums.ReservationStatus;

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
    @PutMapping("/{id}/status")
    public Reservation updateStatus(@PathVariable Long id,
                                    @RequestParam ReservationStatus status) {

        return reservationService.updateReservationStatus(id, status);
    }
}