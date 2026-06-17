package com.example.bookstore.service;

import com.example.bookstore.dto.CreateReservationRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Reservation;
import com.example.bookstore.entity.User;
import com.example.bookstore.enums.ReservationStatus;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.ReservationRepository;
import com.example.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    @Test
    void shouldCreateReservation() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(1L);
        request.setBookId(1L);

        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setQuantity(5);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Reservation result = reservationService.createReservation(request);

        assertEquals(user, result.getUser());
        assertEquals(book, result.getBook());
        assertEquals(ReservationStatus.PENDING, result.getStatus());
        assertEquals(4, book.getQuantity());

        Mockito.verify(bookRepository).save(book);
        Mockito.verify(reservationRepository).save(Mockito.any(Reservation.class));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(1L);
        request.setBookId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationService.createReservation(request)
        );

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(1L);
        request.setBookId(1L);

        User user = new User();
        user.setId(1L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationService.createReservation(request)
        );

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBookIsNotAvailable() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        CreateReservationRequest request = new CreateReservationRequest();
        request.setUserId(1L);
        request.setBookId(1L);

        User user = new User();
        user.setId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setQuantity(0);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationService.createReservation(request)
        );

        assertEquals("Book is not available", exception.getMessage());
    }

    @Test
    void shouldReturnAllReservations() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        Mockito.when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<Reservation> result = reservationService.getAllReservations();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void shouldUpdateReservationStatus() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setStatus(ReservationStatus.PENDING);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result =
                reservationService.updateReservationStatus(1L, ReservationStatus.ACCEPTED);

        assertEquals(ReservationStatus.ACCEPTED, result.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenReservationNotFoundDuringStatusUpdate() {
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BookRepository bookRepository = Mockito.mock(BookRepository.class);

        ReservationService reservationService =
                new ReservationService(reservationRepository, userRepository, bookRepository);

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reservationService.updateReservationStatus(1L, ReservationStatus.ACCEPTED)
        );

        assertEquals("Reservation not found", exception.getMessage());
    }
}