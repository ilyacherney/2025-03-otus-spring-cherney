package ru.otus.hw.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

@DataJpaTest
@Import({CommentServiceImpl.class})
public class CommentServiceTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    void shouldNotThrowLIE() {
        Comment foundComment = commentService.findById(1L).orElseThrow();

        Assertions.assertThatCode(() -> {
                    String bookTitle = foundComment.getBook().getTitle();
                    String bookAuthorFullName = foundComment.getBook().getAuthor().getFullName();
                    String bookGenreName = foundComment.getBook().getGenre().getName();
                }).doesNotThrowAnyException();
    }
}
