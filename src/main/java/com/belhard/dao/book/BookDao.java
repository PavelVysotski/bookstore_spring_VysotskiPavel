package com.belhard.dao.book;

import com.belhard.dao.entity.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book getBookByIsbn(String isbn);

    Book createBook(Book book);

    Book updateBook(Book book);

    List<Book> getBooksByAuthor(String author);

    boolean deleteBookById(Long id);

    int countAllBooks();
}
