package ru.otus.hw.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@Import(GenreServiceImpl.class)
class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("genre");

        mongoTemplate.save(new Genre("1", "Genre_1"));
        mongoTemplate.save(new Genre("2", "Genre_2"));
        mongoTemplate.save(new Genre("3", "Genre_3"));
    }

    @Test
    void shouldFindAllGenres() {
        List<Genre> genres = genreService.findAll();

        Assertions.assertThat(genres).hasSize(3);
        Assertions.assertThat(genres)
                .extracting(Genre::getName)
                .containsExactlyInAnyOrder("Genre_1", "Genre_2", "Genre_3");
    }

    @Test
    void shouldFindGenreById() {
        Optional<Genre> genreOpt = genreService.findById("2");

        Assertions.assertThat(genreOpt).isPresent();
        Assertions.assertThat(genreOpt.get().getName()).isEqualTo("Genre_2");
    }

    @Test
    void shouldReturnEmptyWhenGenreNotFound() {
        Optional<Genre> genreOpt = genreService.findById("nonexistent");

        Assertions.assertThat(genreOpt).isEmpty();
    }
}
