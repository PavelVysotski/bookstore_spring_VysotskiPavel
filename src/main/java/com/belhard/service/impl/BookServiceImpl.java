package com.belhard.service.impl;

import com.belhard.repository.book.BookRepository;
import com.belhard.repository.entity.Book;
import com.belhard.repository.entity.Book.TypeCover;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import com.belhard.service.dto.book.BookDto.TypeCoverDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookToBookDto(bookRepository.save(bookDtoToBook(bookDto)));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return bookToBookDto(bookRepository.save(bookDtoToBook(bookDto)));
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAllBooks().stream()
                .map(this::bookToBookDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book book = bookOptional.orElseThrow(() -> new RuntimeException("No book with id = " + id));
        return bookToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public int countAllBooks() {
        return bookRepository.countAllBooks();
    }

    private BookDto bookToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTypeCover(TypeCoverDto.valueOf(book.getTypeCover().toString()));
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    private Book bookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setTypeCover(TypeCover.valueOf(bookDto.getTypeCover().toString()));
        book.setPrice(bookDto.getPrice());
        return book;
    }
}
