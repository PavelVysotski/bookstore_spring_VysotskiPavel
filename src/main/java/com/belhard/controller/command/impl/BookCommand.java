package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookCommand implements Command {

    private final BookService bookService;

    @Autowired
    public BookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto book = bookService.getBookById(id);
        if (book == null) {
            req.setAttribute("message", "No book with id = " + id);
            return "jsp/error.jsp";
        }
        req.setAttribute("book", book);
        return "jsp/book.jsp";
    }
}
