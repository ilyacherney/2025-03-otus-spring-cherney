package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@Import(JpaAuthorRepository.class)
@DataJpaTest
public class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository jpaAuthorRepository;

    @Test
    void shouldFindAllAuthors() {
        List<Author> foundAuthors = jpaAuthorRepository.findAll();

        Assertions.assertThat(foundAuthors)
                .extracting(Author::getFullName)
                .containsExactlyInAnyOrder("Leo Tolstoy", "Alexander Pushkin");
    }

    @Test
    void shouldFindAuthorById() {
        Optional<Author> authorOptional = jpaAuthorRepository.findById(1L);

        Assertions.assertThat(authorOptional)
                .isPresent()
                .get()
                .extracting(Author::getFullName)
                .isEqualTo("Leo Tolstoy");
    }
}
