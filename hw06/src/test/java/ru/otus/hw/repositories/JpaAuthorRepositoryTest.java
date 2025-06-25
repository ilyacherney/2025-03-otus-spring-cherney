package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;

@Import(JpaAuthorRepository.class)
@DataJpaTest
public class JpaAuthorRepositoryTest {

    @Autowired
    private TestEntityManager tem;

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @Test
    void shouldFindAllAuthors() {
        Author author1 = new Author();
        tem.persistAndFlush(author1);

        Author author2 = new Author();
        tem.persistAndFlush(author2);

        List<Author> savedAuthors = List.of(author1, author2);

        List<Author> foundAuthors = jpaAuthorRepository.findAll();

        Assertions.assertThat(foundAuthors).containsAll(savedAuthors);
    }

    @Test
    void shouldFindAuthorById() {
        Author author = new Author();
        tem.persistAndFlush(author);

        Author foundAuthor = jpaAuthorRepository.findById(author.getId()).orElseThrow();

        Assertions.assertThat(foundAuthor).isEqualTo(author);
    }
}
