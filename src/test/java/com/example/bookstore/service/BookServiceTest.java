package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    @Test
    void shouldReturnAllBooks() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book book = new Book();
        book.setTitle("Wiedzmin");

        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> books = bookService.getAllBooks();

        assertEquals(1, books.size());
        assertEquals("Wiedzmin", books.get(0).getTitle());
    }

    @Test
    void shouldCreateBook() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book book = new Book();
        book.setTitle("Java");

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.createBook(book);

        assertEquals("Java", saved.getTitle());
    }

    @Test
    void shouldGetBookById() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Wiedzmin");

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Wiedzmin", result.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFoundById() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bookService.getBookById(1L)
        );

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void shouldUpdateBook() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book oldBook = new Book();
        oldBook.setId(1L);
        oldBook.setTitle("Old title");
        oldBook.setAuthor("Old author");
        oldBook.setIsbn("111");
        oldBook.setQuantity(1);

        Book updatedBook = new Book();
        updatedBook.setTitle("New title");
        updatedBook.setAuthor("New author");
        updatedBook.setIsbn("222");
        updatedBook.setQuantity(5);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(oldBook));
        Mockito.when(bookRepository.save(oldBook)).thenReturn(oldBook);

        Book result = bookService.updateBook(1L, updatedBook);

        assertEquals("New title", result.getTitle());
        assertEquals("New author", result.getAuthor());
        assertEquals("222", result.getIsbn());
        assertEquals(5, result.getQuantity());
    }

    @Test
    void shouldDeleteBookWithoutReservations() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book book = new Book();
        book.setId(1L);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(reservationRepository.existsByBookId(1L)).thenReturn(false);

        bookService.deleteBook(1L);

        Mockito.verify(bookRepository).delete(book);
    }

    @Test
    void shouldThrowExceptionWhenDeletingBookWithReservations() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book book = new Book();
        book.setId(1L);

        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(reservationRepository.existsByBookId(1L)).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bookService.deleteBook(1L)
        );

        assertEquals("Cannot delete book with reservations", exception.getMessage());
    }

    @Test
    void shouldSearchBooksByTitle() {
        BookRepository bookRepository = Mockito.mock(BookRepository.class);
        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);

        BookService bookService = new BookService(bookRepository, reservationRepository);

        Book book = new Book();
        book.setTitle("Wiedzmin");

        Mockito.when(bookRepository.findByTitleContainingIgnoreCase("Wiedz"))
                .thenReturn(List.of(book));

        List<Book> result = bookService.searchBooks("Wiedz");

        assertEquals(1, result.size());
        assertEquals("Wiedzmin", result.get(0).getTitle());
    }
}