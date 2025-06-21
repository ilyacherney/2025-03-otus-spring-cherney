package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;

@Import(AuthorRepositoryJpa.class)
@DataJpaTest
public class AuthorRepositoryJpaTest {

    @Autowired
    private TestEntityManager tem;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    void shouldFindAllAuthors() {
        Author author1 = new Author();
        author1.setFullName("Ernest Hemingway");
        tem.persistAndFlush(author1);

        Author author2 = new Author();
        author2.setFullName("Edgar Allan Poe");
        tem.persistAndFlush(author2);

        List<Author> savedAuthors = List.of(author1, author2);

        List<Author> foundAuthors = authorRepositoryJpa.findAll();

        Assertions.assertThat(foundAuthors).containsAll(savedAuthors);
    }

    @Test
    void shouldFindAuthorById() {
        Author author = new Author();
        tem.persistAndFlush(author);

        Author foundAuthor = authorRepositoryJpa.findById(author.getId()).orElseThrow();

        Assertions.assertThat(foundAuthor).isEqualTo(author);
    }
}
