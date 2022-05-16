package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import com.belhard.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class BooksCommand implements Command {

    private final BookService BOOK_SERVICE = new BookServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books = BOOK_SERVICE.getAllBooks();
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }
}
