package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksCommand implements Command {

    private final BookService bookService;

    @Autowired
    public BooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books = bookService.getAllBooks();
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }
}
