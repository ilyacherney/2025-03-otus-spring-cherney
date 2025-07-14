package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Comment;

import java.util.List;

@DataJpaTest
public class JpaCommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager tem;

    @Test
    void shouldFindById() {
        Comment foundComment = commentRepository.findById(1L).orElseThrow();

        Assertions.assertThat(foundComment)
                .isNotNull();
        Assertions.assertThat(foundComment.getText())
                .isEqualTo("Good book!");
    }

    @Test
    void shouldFindByBookId() {
        List<Comment> foundComments = commentRepository
                .findAllByBookId(1L);

        Assertions.assertThat(foundComments)
                .extracting(Comment::getId)
                .containsExactlyInAnyOrder(1L);
    }

    @Test
    void shouldSaveComment() {
        Comment commentToSave = new Comment();

        commentRepository.save(commentToSave);

        Comment foundComment = tem.find(Comment.class, commentToSave.getId());
        Assertions.assertThat(foundComment)
                .isNotNull()
                .isEqualTo(commentToSave);
    }

    @Test
    void shouldDeleteComment() {
        Comment commentToDelete = new Comment();
        tem.persistAndFlush(commentToDelete);

        commentRepository.delete(commentToDelete);

        Comment foundComment = tem.find(Comment.class, commentToDelete.getId());
        Assertions.assertThat(foundComment).isNull();
    }
}
