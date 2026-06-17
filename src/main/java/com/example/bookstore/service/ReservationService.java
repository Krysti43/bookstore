package com.example.bookstore.service;

import com.example.bookstore.dto.CreateReservationRequest;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.Reservation;
import com.example.bookstore.entity.User;
import com.example.bookstore.enums.ReservationStatus;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.ReservationRepository;
import com.example.bookstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDateTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              BookRepository bookRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Reservation createReservation(CreateReservationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book is not available");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        return reservationRepository.save(reservation);
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    public Reservation updateReservationStatus(Long reservationId,
                                               ReservationStatus status) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(status);

        return reservationRepository.save(reservation);
    }
}