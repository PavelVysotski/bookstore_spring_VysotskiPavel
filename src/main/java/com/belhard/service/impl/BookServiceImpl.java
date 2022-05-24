package com.belhard.service.impl;

import com.belhard.dao.book.BookDao;
import com.belhard.dao.entity.Book;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    public final BookDao bookDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookToBookDto(bookDao.createBook(bookDtoToBook(bookDto)));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return bookToBookDto(bookDao.updateBook(bookDtoToBook(bookDto)));
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookDao.getAllBooks().stream()
                .map(this::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookToBookDto(bookDao.getBookById(id));
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        return bookToBookDto(bookDao.getBookByIsbn(isbn));
    }

    @Override
    public List<BookDto> getBooksByAuthor(String author) {
        return bookDao.getBooksByAuthor(author).stream()
                .map(this::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(Long id) {
        if (bookDao.deleteBookById(id)) {
            System.out.println("The book has been successfully removed from sale.");
        } else {
            System.out.println("This book has been removed or does not exist in the list.");
        }
    }

    @Override
    public int countAllBooks() {
        return bookDao.countAllBooks();
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
