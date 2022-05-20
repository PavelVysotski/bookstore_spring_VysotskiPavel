package com.belhard.controller;

import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/byId/{id}")
    public String getBookById(Model model, @PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/byIsbn/{isbn}")
    public String getBookByIsbn(Model model, @PathVariable String isbn) {
        BookDto book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/addBook")
    public String addBookForm() {
        return "create-book";
    }

    @PostMapping("/createBook")
    public String addNewBook(@RequestParam Map<String, Object> params) {
        BookDto newBook = new BookDto();
        newBook.setIsbn(String.valueOf(params.get("isbn")));
        newBook.setTitle(String.valueOf(params.get("title")));
        newBook.setAuthor(String.valueOf(params.get("author")));
        newBook.setTypeCover(BookDto.TypeCoverDto.valueOf(String.valueOf(params.get("cover")).toUpperCase(Locale.ROOT)));
        newBook.setPrice(BigDecimal.valueOf(Double.parseDouble(String.valueOf(params.get("price")))));
        bookService.createBook(newBook);
        return "redirect:/books";
    }

    @GetMapping("/count")
    public String countBooks(Model model) {
        model.addAttribute("count", bookService.countAllBooks());
        return "count";
    }

    @GetMapping("/updateBook/{id}")
    public String updateBookForm(Model model, @PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "update-book";
    }

    @PostMapping("/updateBook")
    public String updateBook(@RequestParam Map<String, Object> params) {
        BookDto updateBook = new BookDto();
        updateBook.setId(Long.parseLong(String.valueOf(params.get("id"))));
        updateBook.setIsbn(String.valueOf(params.get("isbn")));
        updateBook.setTitle(String.valueOf(params.get("title")));
        updateBook.setAuthor(String.valueOf(params.get("author")));
        updateBook.setTypeCover(BookDto.TypeCoverDto.valueOf(String.valueOf(params.get("cover")).toUpperCase(Locale.ROOT)));
        updateBook.setPrice(BigDecimal.valueOf(Double.parseDouble(String.valueOf(params.get("price")))));
        bookService.updateBook(updateBook);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }
}
