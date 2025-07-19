package ru.otus.hw.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;

@DataMongoTest
@Import(CommentServiceImpl.class)
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Book book;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("author");
        mongoTemplate.dropCollection("genre");
        mongoTemplate.dropCollection("book");
        mongoTemplate.dropCollection("comment");

        Author author = new Author("1", "Leo Tolstoy");
        Genre genre = new Genre("1", "Novel");

        mongoTemplate.save(author);
        mongoTemplate.save(genre);

        book = new Book("1", "War and Peace", author, genre);
        mongoTemplate.save(book);
    }

    @Test
    void shouldInsertComment() {
        Comment inserted = commentService.insertComment("Great book!", book.getId());

        Assertions.assertThat(inserted.getId()).isNotNull();
        Assertions.assertThat(inserted.getText()).isEqualTo("Great book!");
        Assertions.assertThat(inserted.getBook().getId()).isEqualTo(book.getId());
    }

    @Test
    void shouldFindCommentById() {
        Comment inserted = commentService.insertComment("Wow!", book.getId());
        Comment found = commentService.findById(inserted.getId()).orElseThrow();

        Assertions.assertThat(found.getText()).isEqualTo("Wow!");
    }

    @Test
    void shouldFindAllByBookId() {
        commentService.insertComment("Comment 1", book.getId());
        commentService.insertComment("Comment 2", book.getId());

        List<Comment> comments = commentService.findAllByBookId(book.getId());

        Assertions.assertThat(comments).hasSize(2);
        Assertions.assertThat(comments).extracting(Comment::getText)
                .containsExactlyInAnyOrder("Comment 1", "Comment 2");
    }

    @Test
    void shouldUpdateComment() {
        Comment inserted = commentService.insertComment("Old comment", book.getId());

        Comment updated = commentService.updateComment(inserted.getId(), "Updated comment");

        Assertions.assertThat(updated.getText()).isEqualTo("Updated comment");
    }

    @Test
    void shouldDeleteComment() {
        Comment inserted = commentService.insertComment("To be deleted", book.getId());

        commentService.deleteComment(inserted.getId());

        Assertions.assertThat(commentService.findById(inserted.getId())).isEmpty();
    }

    @Test
    void shouldThrowIfBookNotFoundWhenInserting() {
        Assertions.assertThatThrownBy(() -> commentService.insertComment("Fail", "nonexistent"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book with id nonexistent not found");
    }

    @Test
    void shouldThrowIfCommentNotFoundWhenDeleting() {
        Assertions.assertThatThrownBy(() -> commentService.deleteComment("nonexistent"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Comment with id nonexistent not found");
    }

    @Test
    void shouldThrowIfCommentNotFoundWhenUpdating() {
        Assertions.assertThatThrownBy(() -> commentService.updateComment("nonexistent", "New"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Comment with id nonexistent not found");
    }
}
