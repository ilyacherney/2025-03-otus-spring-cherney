package ru.otus.hw.repositories;


import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с жанрами")
@JdbcTest
@Import({JdbcGenreRepository.class})
@ExtendWith(SpringExtension.class)
public class JdbcGenreRepositoryTest {

    @Autowired
    private JdbcGenreRepository jdbcGenreRepository;

    @DisplayName("должен находить книгу по id")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    void shouldFindGenreById(Genre expectedGenre) {
        long expectedGenreId = expectedGenre.getId();
        Genre actualGenre = jdbcGenreRepository.findById(expectedGenreId).orElseThrow(() -> new EntityNotFoundException("Genre with id " + expectedGenreId + " not found"));

        assertEquals(expectedGenre, actualGenre);
    }

    @DisplayName("должен загружать список всех жанров'")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> actualGenres = jdbcGenreRepository.findAll();
        List<Genre> expectedGenres = getDbGenres();

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
        actualGenres.forEach(System.out::println);
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

}
