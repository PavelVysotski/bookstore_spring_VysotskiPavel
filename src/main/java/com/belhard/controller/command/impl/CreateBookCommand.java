package com.belhard.controller.command.impl;

import com.belhard.controller.command.Command;
import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import com.belhard.service.impl.BookServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Locale;

public class CreateBookCommand implements Command {

    private final BookService BOOK_SERVICE = new BookServiceImpl();

    @Override
    public String execute(HttpServletRequest req) {
        BookDto newBook = new BookDto();
        newBook.setIsbn(req.getParameter("isbn"));
        newBook.setTitle(req.getParameter("title"));
        newBook.setAuthor(req.getParameter("author"));
        newBook.setTypeCover(BookDto.TypeCoverDto.valueOf(req.getParameter("cover")));
        newBook.setPrice(BigDecimal.valueOf(Double.valueOf(req.getParameter("price").toUpperCase(Locale.ROOT))));

        BookDto bookDto = BOOK_SERVICE.createBook(newBook);
        req.setAttribute("book", bookDto);
        return "jsp/book.jsp";
    }
}
