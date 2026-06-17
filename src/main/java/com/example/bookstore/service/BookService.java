package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;
import com.example.bookstore.repository.ReservationRepository;


import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;

    public BookService(BookRepository bookRepository,
                       ReservationRepository reservationRepository) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
    public Book updateBook(Long id, Book updatedBook) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setIsbn(updatedBook.getIsbn());
        book.setQuantity(updatedBook.getQuantity());

        return bookRepository.save(book);
    }
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (reservationRepository.existsByBookId(id)) {
            throw new RuntimeException("Cannot delete book with reservations");
        }

        bookRepository.delete(book);
    }
    public List<Book> searchBooks(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}