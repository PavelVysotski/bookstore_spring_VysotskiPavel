package com.belhard.service;

import com.belhard.service.dto.book.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto getBookByIsbn(String isbn);

    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    List<BookDto> getBooksByAuthor(String author);

    void deleteBookById(Long id);

    int countAllBooks();
}
