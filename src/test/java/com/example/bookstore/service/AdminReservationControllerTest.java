package com.example.bookstore.controller;

import com.example.bookstore.entity.Reservation;
import com.example.bookstore.enums.ReservationStatus;
import com.example.bookstore.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminReservationControllerTest {

    @Test
    void shouldReturnAllReservations() {
        ReservationService reservationService =
                Mockito.mock(ReservationService.class);

        AdminReservationController controller =
                new AdminReservationController(reservationService);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        Mockito.when(reservationService.getAllReservations())
                .thenReturn(List.of(reservation));

        List<Reservation> result = controller.getAllReservations();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void shouldUpdateReservationStatus() {
        ReservationService reservationService =
                Mockito.mock(ReservationService.class);

        AdminReservationController controller =
                new AdminReservationController(reservationService);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(ReservationStatus.ACCEPTED);

        Mockito.when(
                reservationService.updateReservationStatus(
                        1L,
                        ReservationStatus.ACCEPTED
                )
        ).thenReturn(reservation);

        Reservation result =
                controller.updateStatus(
                        1L,
                        ReservationStatus.ACCEPTED
                );

        assertEquals(
                ReservationStatus.ACCEPTED,
                result.getStatus()
        );
    }
}