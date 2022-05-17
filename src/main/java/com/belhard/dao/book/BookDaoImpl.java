package com.belhard.dao.book;

import com.belhard.dao.connection.DbConnection;
import com.belhard.dao.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Component
public class BookDaoImpl implements BookDao {

    private static final Logger logger = LogManager.getLogger(BookDaoImpl.class);

    private static final String SELECT_ALL = "SELECT id, isbn, title, author, cover, price FROM books WHERE deleted = false ORDER BY id ASC";
    private static final String SELECT_BY_ID = "SELECT id, isbn, title, author, cover, price FROM books WHERE id = ? AND deleted = false";
    private static final String SELECT_BY_AUTHOR = "SELECT id, isbn, title, author, cover, price FROM books WHERE author = ? AND deleted = false";
    private static final String SELECT_BY_ISBN = "SELECT id, isbn, title, author, cover, price FROM books WHERE isbn = ? AND deleted = false";
    private static final String ADD = "INSERT INTO books (isbn, title, author, cover, price) VALUES (?, ?, ?, CAST(? AS type_cover), ?)";
    private static final String UPDATE = "UPDATE books SET isbn = ?, title = ?, author = ?, cover = CAST(? AS type_cover), price = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "UPDATE books SET deleted = true WHERE id = ? AND deleted = false";
    private static final String COUNT_ALL_BOOKS = "SELECT COUNT(*) as count FROM books WHERE deleted = false";

    private static final Connection CONNECTION = DbConnection.getConnection();

    @Override
    public List<Book> getAllBooks() {
        logger.debug("Getting all books from DB.");
        List<Book> result = new ArrayList<>();
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book books = bookToObj(resultSet);
                result.add(books);
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return result;
    }

    @Override
    public Book getBookById(Long id) {
        logger.debug("Getting a book from the database by ID={}", id);
        Book book = null;
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(SELECT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            book = bookToObj(resultSet);
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return book;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        logger.debug("Getting book from the database by ISBN={}", isbn);
        Book book = null;
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(SELECT_BY_ISBN);
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            book = bookToObj(resultSet);
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return book;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        logger.debug("Getting book from the database by AUTHOR={}", author);
        List<Book> result = new ArrayList<>();
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(SELECT_BY_AUTHOR);
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book books = bookToObj(resultSet);
                result.add(books);
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return result;
    }

    @Override
    public Book createBook(Book book) {
        logger.debug("Creating new book and adding to database.");
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(ADD, RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getTypeCover().toString());
            statement.setBigDecimal(5, book.getPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return bookToObj(generatedKeys);
            } else {
                throw new RuntimeException("Failed to create book.");
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        throw new RuntimeException("Book not created.");
    }

    @Override
    public Book updateBook(Book book) {
        logger.debug("Updating existing book in the database.");
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(UPDATE, RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getTypeCover().toString());
            statement.setBigDecimal(5, book.getPrice());
            statement.setLong(6, book.getId());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return bookToObj(generatedKeys);
            } else {
                throw new RuntimeException("Failed to update book.");
            }
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        throw new RuntimeException("Book not updated.");
    }

    @Override
    public boolean deleteBookById(Long id) {
        logger.debug("Deleting existing book by ID={}.", id);
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(DELETE_BY_ID);
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return false;
    }

    @Override
    public int countAllBooks() {
        logger.debug("Getting the total number of books.");
        try {
            PreparedStatement statement = CONNECTION.prepareStatement(COUNT_ALL_BOOKS);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("Check the SQL request.", e);
        }
        return 0;
    }

    private Book bookToObj(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setTypeCover(Book.TypeCover.valueOf(resultSet.getString("cover")));
        book.setPrice(resultSet.getBigDecimal("price"));

        return book;
    }
}
