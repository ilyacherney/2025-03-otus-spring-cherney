package ru.otus.hw.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepositoryJpa;
import ru.otus.hw.repositories.BookRepositoryJpa;
import ru.otus.hw.repositories.CommentRepositoryJpa;
import ru.otus.hw.repositories.GenreRepositoryJpa;

@DataJpaTest
@Import({BookServiceImpl.class, BookRepositoryJpa.class, CommentRepositoryJpa.class, AuthorRepositoryJpa.class,
        GenreRepositoryJpa.class})
public class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private TestEntityManager tem;

    @Test
    void shouldNotThrowLIE() {
        Author author1 = new Author();
        author1.setFullName("Leo Tolstoy");
        tem.persistAndFlush(author1);

        Genre genre1 = new Genre();
        genre1.setName("Novel");
        tem.persistAndFlush(genre1);

        Book bookToSave = new Book();
        bookToSave.setAuthor(author1);
        bookToSave.setGenre(genre1);
        tem.persistAndFlush(bookToSave);

        Comment commentToSave = new Comment();
        commentToSave.setBook(bookToSave);
        commentToSave.setText("Good book!");
        tem.persistAndFlush(commentToSave);

        Book foundBook = bookService.findById(bookToSave.getId()).orElseThrow();

        Assertions.assertThatCode(() -> {
            String bookGenreName = foundBook.getGenre().getName();
            String bookAuthorFullName = foundBook.getAuthor().getFullName();
        }).doesNotThrowAnyException();

    }
}
