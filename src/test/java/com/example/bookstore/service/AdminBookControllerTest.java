package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminBookControllerTest {

    @Test
    void shouldCreateBook() {
        BookService bookService = Mockito.mock(BookService.class);
        AdminBookController controller = new AdminBookController(bookService);

        Book book = new Book();
        book.setTitle("Java");

        Mockito.when(bookService.createBook(book)).thenReturn(book);

        Book result = controller.createBook(book);

        assertEquals("Java", result.getTitle());
    }

    @Test
    void shouldUpdateBook() {
        BookService bookService = Mockito.mock(BookService.class);
        AdminBookController controller = new AdminBookController(bookService);

        Book book = new Book();
        book.setTitle("Updated");

        Mockito.when(bookService.updateBook(1L, book)).thenReturn(book);

        Book result = controller.updateBook(1L, book);

        assertEquals("Updated", result.getTitle());
    }

    @Test
    void shouldDeleteBook() {
        BookService bookService = Mockito.mock(BookService.class);
        AdminBookController controller = new AdminBookController(bookService);

        controller.deleteBook(1L);

        Mockito.verify(bookService).deleteBook(1L);
    }
}