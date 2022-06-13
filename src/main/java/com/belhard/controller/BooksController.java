package com.belhard.controller;

import com.belhard.service.BookService;
import com.belhard.service.dto.book.BookDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
@Api(tags = "Book operation management.")
public class BooksController {

    private final BookService bookService;

    @GetMapping
    @ApiOperation(value = "Get list of all books from DB.")
    public ModelAndView getAllBooks(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return new ModelAndView("book/books-list");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get book vy id from DB.")
    public ModelAndView getBookById(Model model, @PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return new ModelAndView("book/book");
    }

    @GetMapping("/create")
    @ApiIgnore
    public ModelAndView addBookForm() {
        return new ModelAndView("book/create-book");
    }

    @PostMapping("/createBook")
    @ApiOperation(value = "Add new book in DB.")
    public ModelAndView addNewBook(Model model, @RequestParam Map<String, Object> params) {
        BookDto newBook = new BookDto();
        settingNewData(params, newBook);
        BookDto book = bookService.createBook(newBook);
        ;
        model.addAttribute("book", book);
        return new ModelAndView("book/book");
    }

    @GetMapping("/count")
    @ApiOperation(value = "Count number of all available books.")
    public ModelAndView countBooks(Model model) {
        model.addAttribute("count", bookService.countAllBooks());
        return new ModelAndView("book/count");
    }

    @GetMapping("/update/{id}")
    @ApiIgnore
    public ModelAndView updateBookForm(Model model, @PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return new ModelAndView("book/update-book");
    }

    @PostMapping("/updateBook")
    @ApiOperation(value = "Update existing book in DB.")
    public ModelAndView updateBook(@RequestParam Map<String, Object> params) {
        BookDto updateBook = new BookDto();
        updateBook.setId(Long.parseLong(String.valueOf(params.get("id"))));
        settingNewData(params, updateBook);
        bookService.updateBook(updateBook);
        return new ModelAndView("redirect:/api/books");
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "Delete existing book from DB.")
    public ModelAndView deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ModelAndView("redirect:/api/books");
    }

    private void settingNewData(Map<String, Object> params, BookDto newBook) {
        newBook.setIsbn(String.valueOf(params.get("isbn")));
        newBook.setTitle(String.valueOf(params.get("title")));
        newBook.setAuthor(String.valueOf(params.get("author")));
        newBook.setTypeCover(BookDto.TypeCoverDto.valueOf(String.valueOf(params.get("cover")).toUpperCase(Locale.ROOT)));
        newBook.setPrice(BigDecimal.valueOf(Double.parseDouble(String.valueOf(params.get("price")))));
    }
}
