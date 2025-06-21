package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa genreRepository;

    @Autowired
    private TestEntityManager tem;

    @Test
    void shouldFindAllGenres() {
        Genre genre1 = new Genre();
        tem.persistAndFlush(genre1);

        Genre genre2 = new Genre();
        tem.persistAndFlush(genre2);

        List<Genre> genresToSave = List.of(genre1, genre2);

        List<Genre> foundGenres = genreRepository.findAll();

        Assertions.assertThat(foundGenres).containsAll(genresToSave);
    }

    @Test
    void shouldFindById() {
        Genre genreToSave = new Genre();
        tem.persistAndFlush(genreToSave);

        Genre foundGenre = genreRepository.findById(genreToSave.getId()).orElseThrow();

        Assertions.assertThat(foundGenre)
                .isNotNull()
                .isEqualTo(genreToSave);
    }
}
