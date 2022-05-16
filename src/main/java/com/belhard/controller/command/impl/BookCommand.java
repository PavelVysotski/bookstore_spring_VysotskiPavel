package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import com.belhard.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class BookCommand implements Command {

    private final BookService BOOK_SERVICE = new BookServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto book = BOOK_SERVICE.getBookById(id);
        if (book == null) {
            req.setAttribute("message", "No book with id = " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("book", book);
        return "jsp/book.jsp";
    }
}
