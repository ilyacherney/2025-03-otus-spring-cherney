package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepositoryJpa;
import ru.otus.hw.repositories.BookRepositoryJpa;
import ru.otus.hw.repositories.CommentRepositoryJpa;

import java.util.List;

@DataJpaTest
@Import({CommentServiceImpl.class, CommentRepositoryJpa.class, BookRepositoryJpa.class, AuthorRepositoryJpa.class})
public class CommentServiceTest {

    @Autowired
    private CommentServiceImpl commentService;

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
        bookToSave.setTitle("War and Peace");
        bookToSave.setAuthor(author1);
        bookToSave.setGenre(genre1);
        tem.persistAndFlush(bookToSave);

        Comment commentToSave = new Comment();
        commentToSave.setBook(bookToSave);
        tem.persistAndFlush(commentToSave);

        Comment foundComment = commentService.findById(commentToSave.getId()).orElseThrow();

        String bookTitle = foundComment.getBook().getTitle();
        String bookAuthorFullName = foundComment.getBook().getAuthor().getFullName();
        String bookGenreName = foundComment.getBook().getGenre().getName();

        Assertions.assertThat(bookTitle).isNotBlank();
        Assertions.assertThat(bookAuthorFullName).isNotBlank();
        Assertions.assertThat(bookGenreName).isNotBlank();
    }
}
