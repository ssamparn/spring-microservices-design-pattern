package com.hexagonal.architecture.pattern.service;

import com.hexagonal.architecture.pattern.dto.BookDto;
import com.hexagonal.architecture.pattern.ports.api.BookServicePort;
import com.hexagonal.architecture.pattern.ports.spi.BookPersistencePort;

import java.util.List;

public class BookServiceImpl implements BookServicePort {

    private BookPersistencePort bookPersistencePort;

    public BookServiceImpl(BookPersistencePort bookPersistencePort) {
        this.bookPersistencePort = bookPersistencePort;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        return this.bookPersistencePort.addBook(bookDto);
    }

    @Override
    public List<BookDto> getBooks() {
        return this.bookPersistencePort.getBooks();
    }

    @Override
    public BookDto getBookById(Long bookId) {
        return this.bookPersistencePort.getBookById(bookId);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return this.bookPersistencePort.updateBook(bookDto);
    }

    @Override
    public void deleteBookById(Long bookId) {
        this.bookPersistencePort.deleteBookById(bookId);
    }
}
