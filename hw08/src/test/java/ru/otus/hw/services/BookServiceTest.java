package ru.otus.hw.services;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;

@DataMongoTest
@Import(BookServiceImpl.class)
public class BookServiceTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void initData() {
        // Очистка коллекций
        mongoTemplate.dropCollection("author");
        mongoTemplate.dropCollection("genre");
        mongoTemplate.dropCollection("book");
        mongoTemplate.dropCollection("comment");

        // Авторы
        Author author1 = new Author("1", "Leo Tolstoy");
        Author author2 = new Author("2", "Alexander Pushkin");
        Author author3 = new Author("3", "Author_3");

        mongoTemplate.save(author1);
        mongoTemplate.save(author2);
        mongoTemplate.save(author3);

        // Жанры
        Genre genre1 = new Genre("1", "Novel");
        Genre genre2 = new Genre("2", "Fairytale");
        Genre genre3 = new Genre("3", "Genre_3");

        mongoTemplate.save(genre1);
        mongoTemplate.save(genre2);
        mongoTemplate.save(genre3);

        // Книги
        Book book1 = new Book("1", "War And Peace", author1, genre1);
        Book book2 = new Book("2", "Eugene Onegin", author2, genre1);
        Book book3 = new Book("3", "BookTitle_3", author3, genre3);

        mongoTemplate.save(book1);
        mongoTemplate.save(book2);
        mongoTemplate.save(book3);

        // Комментарии
        Comment comment1 = new Comment("1", "Great book!", book1);
        Comment comment2 = new Comment("2", "Very interesting...", book2);

        mongoTemplate.save(comment1);
        mongoTemplate.save(comment2);
    }

    @Test
    void shouldFindBookById() {
        Book foundBook = bookService.findById("1").orElseThrow();

        Assertions.assertThat(foundBook.getId()).isEqualTo("1");
        Assertions.assertThat(foundBook.getTitle()).isEqualTo("War And Peace");
        Assertions.assertThat(foundBook.getAuthor().getId()).isEqualTo("1");
        Assertions.assertThat(foundBook.getAuthor().getFullName()).isEqualTo("Leo Tolstoy");
        Assertions.assertThat(foundBook.getGenre().getId()).isEqualTo("1");
        Assertions.assertThat(foundBook.getGenre().getName()).isEqualTo("Novel");
    }

    @Test
    void shouldFindAllBooks() {
        List<Book> expectedBooks = List.of(
                new Book("1", "War And Peace",
                        new Author("1", "Leo Tolstoy"),
                        new Genre("1", "Novel")
                ),
                new Book("2", "Eugene Onegin",
                        new Author("2", "Alexander Pushkin"),
                        new Genre("1", "Novel")
                ),
                new Book("3", "BookTitle_3",
                        new Author("3", "Author_3"),
                        new Genre("3", "Genre_3")
                )
        );

        List<Book> foundBooks = bookService.findAll();

        Assertions.assertThat(foundBooks)
                .usingRecursiveComparison()
                .isEqualTo(expectedBooks);
    }

    @Test
    void shouldInsertBookCorrectly() {
        String newTitle = "New Book Title";
        String authorId = "1";
        String genreId = "1";

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
        String bookId = "1";
        String updatedTitle = "Updated Title";
        String updatedAuthorId = "2";
        String updatedGenreId = "2";

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
        String bookIdToDelete = "1";

        bookService.deleteById(bookIdToDelete);

        Assertions.assertThat(bookService.findById(bookIdToDelete)).isEmpty();
    }

}
