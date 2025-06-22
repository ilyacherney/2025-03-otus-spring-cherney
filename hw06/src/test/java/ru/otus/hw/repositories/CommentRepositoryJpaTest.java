package ru.otus.hw.repositories;

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

import java.util.List;

@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa commentRepository;

    @Autowired
    private TestEntityManager tem;

    @Test
    void shouldFindById() {
        Author author1 = new Author();
        tem.persistAndFlush(author1);

        Genre genre1 = new Genre();
        tem.persistAndFlush(genre1);

        Book book = new Book();
        book.setGenre(genre1);
        book.setAuthor(author1);
        tem.persistAndFlush(book);

        Comment commentToSave = new Comment();
        commentToSave.setBook(book);
        tem.persistAndFlush(commentToSave);

        Comment foundComment = commentRepository.findById(commentToSave.getId()).orElseThrow();

        Assertions.assertThat(foundComment)
                .isNotNull()
                .isEqualTo(commentToSave);
    }

    @Test
    void shouldFindByBookId() {
        Author author1 = new Author();
        tem.persistAndFlush(author1);

        Genre genre1 = new Genre();
        tem.persistAndFlush(genre1);

        Book bookToSave = new Book();
        bookToSave.setAuthor(author1);
        bookToSave.setGenre(genre1);
        tem.persistAndFlush(bookToSave);

        Comment commentToSave = new Comment();
        commentToSave.setBook(bookToSave);
        tem.persistAndFlush(commentToSave);

        List<Comment> foundComments = commentRepository
                .findAllByBookId(bookToSave.getId());

        Assertions.assertThat(foundComments).contains(commentToSave);
    }

    @Test
    void shouldSaveComment() {
        Comment commentToSave = new Comment();

        commentRepository.saveComment(commentToSave);

        Comment foundComment = tem.find(Comment.class, commentToSave.getId());
        Assertions.assertThat(foundComment)
                .isNotNull()
                .isEqualTo(commentToSave);
    }

    @Test
    void shouldDeleteComment() {
        Comment commentToDelete = new Comment();
        tem.persistAndFlush(commentToDelete);

        commentRepository.deleteComment(commentToDelete.getId());

        Comment foundComment = tem.find(Comment.class, commentToDelete.getId());
        Assertions.assertThat(foundComment).isNull();
    }
}
