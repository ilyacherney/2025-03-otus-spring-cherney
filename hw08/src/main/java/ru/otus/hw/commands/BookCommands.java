package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.services.BookService;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookRepository bookRepository;

    private final BookConverter bookConverter;

    private final BookService bookService;

    // bins newBook 1 1
    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(String title, long authorId, long genreId) {
        var savedBook = bookService.insert(title, authorId, genreId);
        return bookConverter.bookToString(savedBook);
    }
}
