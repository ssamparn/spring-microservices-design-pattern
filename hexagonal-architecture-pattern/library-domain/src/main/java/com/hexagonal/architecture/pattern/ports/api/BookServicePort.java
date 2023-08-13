package com.hexagonal.architecture.pattern.ports.api;

import com.hexagonal.architecture.pattern.dto.BookDto;

import java.util.List;

public interface BookServicePort {
    BookDto addBook(BookDto bookDto);
    List<BookDto> getBooks();
    BookDto getBookById(Long bookId);
    BookDto updateBook(BookDto bookDto);
    void deleteBookById(Long bookId);
}
