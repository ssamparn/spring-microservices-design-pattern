package com.hexagonal.architecture.pattern.adapter;

import com.hexagonal.architecture.pattern.dto.BookDto;
import com.hexagonal.architecture.pattern.entity.Book;
import com.hexagonal.architecture.pattern.mapper.BookMapper;
import com.hexagonal.architecture.pattern.ports.spi.BookPersistencePort;
import com.hexagonal.architecture.pattern.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookJpaAdapter implements BookPersistencePort {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);

        Book savedBook = this.bookRepository.save(book);

        return bookMapper.bookToBookDto(savedBook);
    }

    @Override
    public List<BookDto> getBooks() {
        List<Book> bookList = this.bookRepository.findAll();

        return bookMapper.bookListToBookDtoList(bookList);
    }

    @Override
    public BookDto getBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        return book.map(bookMapper::bookToBookDto).orElse(null);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return this.addBook(bookDto);
    }

    @Override
    public void deleteBookById(Long bookId) {
        this.bookRepository.deleteById(bookId);
    }
}
