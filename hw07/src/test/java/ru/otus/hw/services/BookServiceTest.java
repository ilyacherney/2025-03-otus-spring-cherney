package ru.otus.hw.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NEVER)
@Import(BookServiceImpl.class)
public class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private PlatformTransactionManager txManager;

    private TransactionStatus txStatus;

    @BeforeEach
    void startTransaction() {
        txStatus = txManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void rollbackTransaction() {
        if (txStatus != null && !txStatus.isCompleted()) {
            txManager.rollback(txStatus);
        }
    }

    @Test
    void shouldFindBookById() {
        Book foundBook = bookService.findById(1L).orElseThrow();

        Assertions.assertThat(foundBook.getId()).isEqualTo(1L);
        Assertions.assertThat(foundBook.getTitle()).isEqualTo("War And Peace");
        Assertions.assertThat(foundBook.getAuthor().getId()).isEqualTo(1L);
        Assertions.assertThat(foundBook.getAuthor().getFullName()).isEqualTo("Leo Tolstoy");
        Assertions.assertThat(foundBook.getGenre().getId()).isEqualTo(1L);
        Assertions.assertThat(foundBook.getGenre().getName()).isEqualTo("Novel");
    }

    @Test
    void ShouldFindAllBooks() {
        List<Book> expectedBooks = List.of(
                new Book(1L, "War And Peace",
                new Author(1L, "Leo Tolstoy"),
                new Genre(1L, "Novel")
        ),
                new Book(2L, "Eugene Onegin",
                new Author(2L, "Alexander Pushkin"),
                new Genre(1L, "Novel")
        ));

        List<Book> foundBooks = bookService.findAll();

        Assertions.assertThat(foundBooks)
                .usingRecursiveComparison()
                .isEqualTo(expectedBooks);
    }

    @Test
    void shouldInsertBookCorrectly() {
        String newTitle = "New Book Title";
        long authorId = 1L;
        long genreId = 1L;

        Book insertedBook = bookService.insert(newTitle, authorId, genreId);

        Book expectedBook = new Book(insertedBook.getId(), newTitle,
                new Author(authorId, "Leo Tolstoy"),
                new Genre(genreId, "Novel")
        );

        Assertions.assertThat(insertedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    void shouldUpdateBookCorrectly() {
        long bookId = 1L;
        String updatedTitle = "Updated Title";
        long updatedAuthorId = 2L;
        long updatedGenreId = 2L;

        Book updatedBook = bookService.update(bookId, updatedTitle, updatedAuthorId, updatedGenreId);

        Book expectedBook = new Book(bookId, updatedTitle,
                new Author(updatedAuthorId, "Alexander Pushkin"),
                new Genre(updatedGenreId, "Fairytale")
        );

        Assertions.assertThat(updatedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    void shouldDeleteBookById() {
        long bookIdToDelete = 1L;

        bookService.deleteById(bookIdToDelete);

        Assertions.assertThat(bookService.findById(bookIdToDelete)).isEmpty();
    }

}
