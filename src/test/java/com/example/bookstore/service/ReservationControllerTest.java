package com.example.bookstore.controller;

import com.example.bookstore.dto.CreateReservationRequest;
import com.example.bookstore.entity.Reservation;
import com.example.bookstore.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationControllerTest {

    @Test
    void shouldCreateReservation() {
        ReservationService reservationService = Mockito.mock(ReservationService.class);
        ReservationController controller = new ReservationController(reservationService);

        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(1L);
        request.setBookId(1L);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        Mockito.when(reservationService.createReservation(request))
                .thenReturn(reservation);

        Reservation result = controller.createReservation(request);

        assertEquals(1L, result.getId());
    }
}