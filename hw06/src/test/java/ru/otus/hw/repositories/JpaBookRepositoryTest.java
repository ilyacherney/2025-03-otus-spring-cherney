package ru.otus.hw.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

@DataJpaTest
@Import(JpaBookRepository.class)
public class JpaBookRepositoryTest {

    @Autowired
    private TestEntityManager tem;

    @Autowired
    private JpaBookRepository bookRepository;

    @Test
    void shouldFindById() {
        Book foundBook = bookRepository.findById(1L).orElseThrow();

        Assertions.assertThat(foundBook.getTitle()).isEqualTo("War And Peace");
    }

    @Test
    void shouldFindAll() {
        List<Book> foundBooks = bookRepository.findAll();

        Assertions.assertThat(foundBooks)
                .extracting(Book::getTitle)
                .containsExactlyInAnyOrder("Eugene Onegin", "War And Peace");
    }

    @Test
    void shouldSaveBook() {
        Author author = tem.find(Author.class, 1L); // данные из data.sql
        Genre genre = tem.find(Genre.class, 1L);

        Book bookToSave = new Book();
        bookToSave.setTitle("New unique title"); // Чтобы не было конфликтов
        bookToSave.setAuthor(author);
        bookToSave.setGenre(genre);

        bookRepository.save(bookToSave);

        tem.flush();
        tem.clear();

        Book foundBook = tem.find(Book.class, bookToSave.getId());

        Assertions.assertThat(foundBook)
                .isNotNull()
                .isEqualTo(bookToSave);
    }

    @Test
    void shouldDeleteBookById() {
        bookRepository.deleteById(1L);

        Book foundBook = tem.find(Book.class, 1L);

        Assertions.assertThat(foundBook).isNull();
    }
}
