package com.example.bookstore.controller;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookControllerTest {

    @Test
    void shouldReturnBooks() {
        BookService bookService = Mockito.mock(BookService.class);
        BookController controller = new BookController(bookService);

        Book book = new Book();
        book.setTitle("Wiedzmin");

        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(book));

        List<Book> result = controller.getBooks();

        assertEquals(1, result.size());
        assertEquals("Wiedzmin", result.get(0).getTitle());
    }

    @Test
    void shouldReturnBookById() {
        BookService bookService = Mockito.mock(BookService.class);
        BookController controller = new BookController(bookService);

        Book book = new Book();
        book.setId(1L);

        Mockito.when(bookService.getBookById(1L)).thenReturn(book);

        Book result = controller.getBookById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldSearchBooks() {
        BookService bookService = Mockito.mock(BookService.class);
        BookController controller = new BookController(bookService);

        Book book = new Book();
        book.setTitle("Java");

        Mockito.when(bookService.searchBooks("Ja")).thenReturn(List.of(book));

        List<Book> result = controller.searchBooks("Ja");

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getTitle());
    }
}