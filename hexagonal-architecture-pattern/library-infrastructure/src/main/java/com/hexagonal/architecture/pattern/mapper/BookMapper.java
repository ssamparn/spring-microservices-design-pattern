package com.hexagonal.architecture.pattern.mapper;

import com.hexagonal.architecture.pattern.dto.BookDto;
import com.hexagonal.architecture.pattern.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookToBookDto(Book book);

    Book bookDtoToBook(BookDto bookDto);

    List<BookDto> bookListToBookDtoList(List<Book> bookList);

    List<Book> BookDtoListTobookList(List<BookDto> BookDtoList);
}
