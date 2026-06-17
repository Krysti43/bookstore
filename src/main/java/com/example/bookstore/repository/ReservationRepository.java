package com.example.bookstore.repository;

import com.example.bookstore.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByBookId(Long bookId);
}
