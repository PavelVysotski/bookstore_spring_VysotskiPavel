package com.belhard.repository.book;

import com.belhard.repository.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT COUNT(*) as count FROM books WHERE deleted = false", nativeQuery = true)
    int countAllBooks();

    @Query(value = "SELECT id, isbn, title, author, cover, price FROM books WHERE deleted = false ORDER BY id ASC", nativeQuery = true)
    List<Book> findAllBooks();
}
