package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Map.of("id", id);

        String query = "SELECT " +
                "b.id AS book_id, " +
                "b.title AS book_title, " +
                "b.author_id AS author_id, " +
                "b.genre_id AS genre_id, " +
                "a.full_name AS author_full_name, " +
                "g.name AS genre_name " +
                "FROM books b " +
                "LEFT OUTER JOIN authors a ON a.id = b.author_id " +
                "LEFT OUTER JOIN genres g on g.id = b.genre_id " +
                "WHERE b.id = :id";

        List<Book> books = jdbc.query(query, params, new BookRowMapper());
        return books.isEmpty() ? Optional.empty() : Optional.of(books.get(0));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT " +
                "b.id AS book_id, " +
                "b.title AS book_title, " +
                "b.author_id AS author_id, " +
                "b.genre_id AS genre_id, " +
                "a.full_name AS author_full_name, " +
                "g.name AS genre_name " +
                "FROM books b " +
                "LEFT OUTER JOIN authors a ON a.id = b.author_id " +
                "LEFT OUTER JOIN genres g on g.id = b.genre_id ",
                new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        String query = "DELETE FROM books WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update(query, params);
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        jdbc.update("INSERT INTO books (title, author_id, genre_id) " +
                        "VALUES (:title, :author_id, :genre_id)", params, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        String query = "UPDATE books SET title = :title, author_id = :authorId, genre_id = :genreId where id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("title", book.getTitle());
        params.put("genreId", book.getGenre().getId());
        params.put("authorId", book.getAuthor().getId());

        int affectedRows = jdbc.update(query, params);
        if (affectedRows == 0) {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }

        return book;
    }

    @RequiredArgsConstructor
    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("book_id"));
            book.setTitle(rs.getString("book_title"));

            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            Genre genre = new Genre(genreId, genreName);
            book.setGenre(genre);

            long authorId = rs.getLong("author_id");
            String authorFullName = rs.getString("author_full_name");
            Author author = new Author(authorId, authorFullName);
            book.setAuthor(author);

            return book;
        }
    }
}
