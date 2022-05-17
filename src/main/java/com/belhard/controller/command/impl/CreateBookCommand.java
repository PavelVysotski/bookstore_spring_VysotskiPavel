package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;

@Component
public class CreateBookCommand implements Command {

    private final BookService bookService;

    @Autowired
    public CreateBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String execute(HttpServletRequest req) {
        BookDto newBook = new BookDto();
        newBook.setIsbn(req.getParameter("isbn"));
        newBook.setTitle(req.getParameter("title"));
        newBook.setAuthor(req.getParameter("author"));
        newBook.setTypeCover(BookDto.TypeCoverDto.valueOf(req.getParameter("cover")));
        newBook.setPrice(BigDecimal.valueOf(Double.valueOf(req.getParameter("price").toUpperCase(Locale.ROOT))));

        BookDto bookDto = bookService.createBook(newBook);
        req.setAttribute("book", bookDto);
        return "jsp/book.jsp";
    }
}
