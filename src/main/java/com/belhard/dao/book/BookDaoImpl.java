package com.belhard.dao.book;

import com.belhard.dao.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger(BookDaoImpl.class);

    private static final String SELECT_ALL = "SELECT id, isbn, title, author, cover, price FROM books WHERE deleted = false ORDER BY id ASC";
    private static final String SELECT_BY_ID = "SELECT id, isbn, title, author, cover, price FROM books WHERE id = :id AND deleted = false";
    private static final String SELECT_BY_AUTHOR = "SELECT id, isbn, title, author, cover, price FROM books WHERE author = :author AND deleted = false";
    private static final String SELECT_BY_ISBN = "SELECT id, isbn, title, author, cover, price FROM books WHERE isbn = :isbn AND deleted = false";
    private static final String ADD = "INSERT INTO books (isbn, title, author, cover, price) VALUES (:isbn, :title, :author, CAST(:cover AS type_cover), :price)";
    private static final String UPDATE = "UPDATE books SET isbn = :isbn, title = :title, author = :author, cover = CAST(:cover AS type_cover), price = :price WHERE id = :id";
    private static final String DELETE_BY_ID = "UPDATE books SET deleted = true WHERE id = :id AND deleted = false";
    private static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) as count FROM books WHERE deleted = false";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    @Autowired
    public BookDaoImpl(NamedParameterJdbcTemplate jdbcTemplate, BookRowMapper bookRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = bookRowMapper;
    }

    @Override
    public List<Book> getAllBooks() {
        logger.debug("Getting all books from DB.");
        return jdbcTemplate.query(SELECT_ALL, bookRowMapper);
    }

    @Override
    public Book getBookById(Long id) {
        logger.debug("Getting a book from the database by ID={}", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, params, bookRowMapper);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        logger.debug("Getting book from the database by ISBN={}", isbn);
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", isbn);
        return jdbcTemplate.queryForObject(SELECT_BY_ISBN, params, bookRowMapper);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        logger.debug("Getting book from the database by AUTHOR={}", author);
        Map<String, Object> params = new HashMap<>();
        params.put("author", author);
        return jdbcTemplate.query(SELECT_BY_AUTHOR, params, bookRowMapper);
    }

    @Override
    public Book createBook(Book book) {
        logger.debug("Creating new book and adding to database.");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new MapSqlParameterSource(addParams(book));
        int rowsUpdate = jdbcTemplate.update(ADD, source, keyHolder, new String[]{"id"});
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't create the book" + book);
        }
        Long id = Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElseThrow(() -> new RuntimeException("Can't create the book" + book));
        return getBookById(id);
    }

    @Override
    public Book updateBook(Book book) {
        logger.debug("Updating existing book in the database.");
        Map<String, Object> params = addParams(book);
        params.put("id", book.getId());
        SqlParameterSource source = new MapSqlParameterSource(params);
        int rowsUpdate = jdbcTemplate.update(UPDATE, source);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't update a book" + book);
        }
        return getBookById(book.getId());
    }

    @Override
    public boolean deleteBookById(Long id) {
        logger.debug("Deleting existing book by ID={}.", id);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int rowsUpdate = jdbcTemplate.update(DELETE_BY_ID, params);
        if (rowsUpdate != 1) {
            throw new RuntimeException("Can't delete the book with id = " + id);
        }
        return rowsUpdate == 1;
    }

    @Override
    public int countAllBooks() {
        logger.debug("Getting the total number of books.");
        return jdbcTemplate.query(COUNT_ALL_BOOKS, rs -> {
            rs.next();
            return rs.getInt("count");
        });
    }

    private Map<String, Object> addParams(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("isbn", book.getIsbn());
        params.put("title", book.getTitle());
        params.put("author", book.getAuthor());
        params.put("cover", book.getTypeCover().toString());
        params.put("price", book.getPrice());
        return params;
    }
}
