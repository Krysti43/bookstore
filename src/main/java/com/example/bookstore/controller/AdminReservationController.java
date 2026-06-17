package com.example.bookstore.controller;

import com.example.bookstore.entity.Reservation;
import com.example.bookstore.enums.ReservationStatus;
import com.example.bookstore.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
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