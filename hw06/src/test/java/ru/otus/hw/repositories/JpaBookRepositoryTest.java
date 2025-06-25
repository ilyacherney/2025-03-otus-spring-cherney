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
        Book bookToSave = new Book();
        tem.persistAndFlush(bookToSave);

        Book foundBook = bookRepository.findById(bookToSave.getId()).orElseThrow();

        Assertions.assertThat(foundBook).isEqualTo(bookToSave);
    }

    @Test
    void shouldFindAll() {
        Author author1 = new Author();
        tem.persistAndFlush(author1);

        Genre genre1 = new Genre();
        tem.persistAndFlush(genre1);

        Book book1 = new Book();
        book1.setAuthor(author1);
        book1.setGenre(genre1);
        tem.persistAndFlush(book1);

        Book book2 = new Book();
        book2.setAuthor(author1);
        book2.setGenre(genre1);
        tem.persistAndFlush(book2);

        List<Book> savedBooks = List.of(book1, book2);

        List<Book> foundBooks = bookRepository.findAll();

        Assertions.assertThat(foundBooks).containsAll(savedBooks);
    }

    @Test
    void shouldSaveBook() {
        Author author1 = new Author();
        tem.persistAndFlush(author1);

        Genre genre1 = new Genre();
        tem.persistAndFlush(genre1);

        Book bookToSave = new Book();
        bookToSave.setAuthor(author1);
        bookToSave.setGenre(genre1);

        bookRepository.save(bookToSave);

        Book foundBook = tem.find(Book.class, bookToSave.getId());

        Assertions.assertThat(foundBook)
                .isNotNull()
                .isEqualTo(bookToSave);
    }

    @Test
    void shouldDeleteBookById() {
        Author author1 = new Author();
        tem.persistAndFlush(author1);

        Genre genre1 = new Genre();
        tem.persistAndFlush(genre1);

        Book bookToSave = new Book();
        bookToSave.setAuthor(author1);
        bookToSave.setGenre(genre1);
        tem.persistAndFlush(bookToSave);

        bookRepository.deleteById(bookToSave.getId());

        Book foundBook = tem.find(Book.class, bookToSave.getId());

        Assertions.assertThat(foundBook).isNull();
    }
}
