package ru.otus.hw.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.JpaAuthorRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaGenreRepository;

@DataJpaTest
@Import({BookServiceImpl.class, JpaBookRepository.class, JpaAuthorRepository.class,
        JpaGenreRepository.class})
public class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void shouldNotThrowLIE() {
        Book foundBook = bookService.findById(1).orElseThrow();

        Assertions.assertThatCode(() -> {
            String bookGenreName = foundBook.getGenre().getName();
            String bookAuthorFullName = foundBook.getAuthor().getFullName();
        }).doesNotThrowAnyException();
    }
}
