package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Comment;

//@DataJpaTest
@SpringBootTest
//@Import(CommentServiceImpl.class)
public class CommentServiceTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private TestEntityManager tem;

    @Test
    void shouldNotThrowLIE() {
        Comment comment = new Comment();
        tem.persistAndFlush(comment);

        Comment foundComment = commentService.findById(comment.getId()).orElseThrow();
        foundComment.getBook();

    }
}
