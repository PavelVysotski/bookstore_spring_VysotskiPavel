package com.belhard.service.impl;

import com.belhard.dao.book.BookDaoImpl;
import com.belhard.dao.entity.Book;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    public static final BookDaoImpl BOOK_DAO = new BookDaoImpl();

    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookToBookDto(BOOK_DAO.createBook(bookDtoToBook(bookDto)));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return bookToBookDto(BOOK_DAO.updateBook(bookDtoToBook(bookDto)));
    }

    @Override
    public List<BookDto> getAllBooks() {
        return BOOK_DAO.getAllBooks().stream()
                .map(this::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookToBookDto(BOOK_DAO.getBookById(id));
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        return bookToBookDto(BOOK_DAO.getBookByIsbn(isbn));
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        return BOOK_DAO.getBooksByAuthor(author).stream()
                .map(this::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(Long id) {
        if (BOOK_DAO.deleteBookById(id)) {
            System.out.println("The book has been successfully removed from sale.");
        } else {
            System.out.println("This book has been removed or does not exist in the list.");
        }
    }

    @Override
    public void countAllBooks() {
        System.out.println("The number of books on sale is = " + BOOK_DAO.countAllBooks());
    }

    private BookDto bookToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTypeCover(BookDto.TypeCoverDto.valueOf(book.getTypeCover().toString()));
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    private Book bookDtoToBook(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setTypeCover(Book.TypeCover.valueOf(bookDto.getTypeCover().toString()));
        book.setPrice(bookDto.getPrice());
        return book;
    }
}
