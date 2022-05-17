package com.belhard;

import com.belhard.controller.command.impl.BookCommand;
import com.belhard.controller.command.impl.BooksCommand;
import com.belhard.controller.command.impl.CreateBookCommand;
import com.belhard.controller.command.impl.UserCommand;
import com.belhard.controller.command.impl.UsersCommand;
import com.belhard.dao.book.BookDao;
import com.belhard.dao.book.BookDaoImpl;
import com.belhard.dao.user.UserDao;
import com.belhard.dao.user.UserDaoImpl;
import com.belhard.service.BookService;
import com.belhard.service.UserService;
import com.belhard.service.impl.BookServiceImpl;
import com.belhard.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean
    public BookDao bookDao() {
        return new BookDaoImpl();
    }

    @Bean
    public BookService bookService() {
        return new BookServiceImpl(bookDao());
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userDao());
    }

    @Bean
    public BookCommand bookCommand() {
        return new BookCommand(bookService());
    }

    @Bean
    public BooksCommand booksCommand() {
        return new BooksCommand(bookService());
    }

    @Bean
    public CreateBookCommand createBookCommand() {
        return new CreateBookCommand(bookService());
    }

    @Bean
    public UserCommand userCommand() {
        return new UserCommand(userService());
    }

    @Bean
    public UsersCommand usersCommand() {
        return new UsersCommand(userService());
    }
}
