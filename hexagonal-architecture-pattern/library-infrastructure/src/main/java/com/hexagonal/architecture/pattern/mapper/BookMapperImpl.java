package com.hexagonal.architecture.pattern.mapper;

import com.hexagonal.architecture.pattern.dto.BookDto;
import com.hexagonal.architecture.pattern.entity.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BookMapperImpl implements BookMapper {

    public BookMapperImpl() {
    }

    public BookDto bookToBookDto(Book book) {
        if (book == null) {
            return null;
        } else {
            BookDto.BookDtoBuilder bookDto = BookDto
                    .builder();
            return bookDto
                    .id(book.getId())
                    .title(book.getTitle())
                    .description(book.getDescription())
                    .price(book.getPrice())
                    .build();
        }
    }

    public Book bookDtoToBook(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        } else {
            Book book = new Book();
            book.setId(bookDto.getId());
            book.setTitle(bookDto.getTitle());
            book.setDescription(bookDto.getDescription());
            book.setPrice(bookDto.getPrice());
            return book;
        }
    }

    public List<BookDto> bookListToBookDtoList(List<Book> bookList) {
        if (bookList == null) {
            return null;
        } else {
            List<BookDto> list = new ArrayList(bookList.size());
            Iterator var3 = bookList.iterator();

            while(var3.hasNext()) {
                Book book = (Book)var3.next();
                list.add(this.bookToBookDto(book));
            }

            return list;
        }
    }

    public List<Book> BookDtoListTobookList(List<BookDto> BookDtoList) {
        if (BookDtoList == null) {
            return null;
        } else {
            List<Book> list = new ArrayList(BookDtoList.size());
            Iterator var3 = BookDtoList.iterator();

            while(var3.hasNext()) {
                BookDto bookDto = (BookDto)var3.next();
                list.add(this.bookDtoToBook(bookDto));
            }

            return list;
        }
    }
}
