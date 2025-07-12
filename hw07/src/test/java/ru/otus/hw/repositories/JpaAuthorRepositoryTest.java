package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class JpaAuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldFindAllAuthors() {
        List<Author> foundAuthors = authorRepository.findAll();

        Assertions.assertThat(foundAuthors)
                .extracting(Author::getFullName)
                .containsExactlyInAnyOrder("Leo Tolstoy", "Alexander Pushkin");
    }

    @Test
    void shouldFindAuthorById() {
        Optional<Author> authorOptional = authorRepository.findById(1L);

        Assertions.assertThat(authorOptional)
                .isPresent()
                .get()
                .extracting(Author::getFullName)
                .isEqualTo("Leo Tolstoy");
    }
}
