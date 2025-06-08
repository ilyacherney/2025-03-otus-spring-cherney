package ru.otus.hw.repositories;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@DisplayName("Dao для работы с жанрами")
@JdbcTest
@Import({JdbcGenreRepository.class})
public class JdbcGenreRepositoryTest {

//    @Autowired
//    private final JdbcGenreRepository jdbcGenreRepository;

//    @Test
//    void testFindAll() {
//    }

}
