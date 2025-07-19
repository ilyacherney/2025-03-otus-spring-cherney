package ru.otus.hw.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;

import java.util.List;

@DataMongoTest
@Import(AuthorServiceImpl.class)
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("author");

        mongoTemplate.save(new Author("1", "Author_1"));
        mongoTemplate.save(new Author("2", "Author_2"));
        mongoTemplate.save(new Author("3", "Author_3"));
    }

    @Test
    void shouldFindAllAuthors() {
        List<Author> authors = authorService.findAll();

        Assertions.assertThat(authors).hasSize(3);
        Assertions.assertThat(authors)
                .extracting(Author::getFullName)
                .containsExactlyInAnyOrder("Author_1", "Author_2", "Author_3");
    }
}
