package com.belhard.controller.command;

import com.belhard.controller.command.impl.AddBookFormCommand;
import com.belhard.controller.command.impl.BookCommand;
import com.belhard.controller.command.impl.BooksCommand;
import com.belhard.controller.command.impl.CreateBookCommand;
import com.belhard.controller.command.impl.ErrorCommand;
import com.belhard.controller.command.impl.UserCommand;
import com.belhard.controller.command.impl.UsersCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static class Holder {
        private static final CommandFactory instance = new CommandFactory();
    }

    public static CommandFactory getInstance() {
        return Holder.instance;
    }

    private CommandFactory() {
    }

    private static final Map<String, Command> map = new HashMap<>();

    static {
        map.put("book", new BookCommand());
        map.put("books", new BooksCommand());
        map.put("create-book-form", new AddBookFormCommand());
        map.put("create-book", new CreateBookCommand());
        map.put("user", new UserCommand());
        map.put("users", new UsersCommand());
        map.put("error", new ErrorCommand());
    }

    public Command createCommandRequest(String action) {
        Command command = map.get(action);
        if (command == null) {
            return map.get("error");
        }
        return command;
    }
}
