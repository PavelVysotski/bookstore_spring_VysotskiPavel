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

import static com.belhard.controller.Controller.context;

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
        map.put("book", context.getBean(BookCommand.class));
        map.put("books", context.getBean(BooksCommand.class));
        map.put("create-book-form", new AddBookFormCommand());
        map.put("create-book", context.getBean(CreateBookCommand.class));
        map.put("user", context.getBean(UserCommand.class));
        map.put("users", context.getBean(UsersCommand.class));
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
