package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return Optional.ofNullable(jdbc.queryForObject(
                "SELECT id, title, author_id, genre_id FROM books WHERE id = :id",
                params,
                new BookRowMapper(genreRepository, authorRepository)));
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query("SELECT id, title, author_id, genre_id FROM books",
                new BookRowMapper(genreRepository, authorRepository));
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
        // todo [x]: JdbcBookRepository.deleteById
        String query = "DELETE FROM books WHERE id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        jdbc.update(query, params);
    }

    private Book insert(Book book) {
        // todo: ...
        var keyHolder = new GeneratedKeyHolder();
        // todo: noinspection DataFlowIssue


        // todo: аналогично и в других репо так сделать через MapSqlParameterSource
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        jdbc.update("INSERT INTO books (title, author_id, genre_id) VALUES (:title, :author_id, :genre_id)", params, keyHolder, new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        // todo: ...
        // todo: Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        return book;
    }

    @RequiredArgsConstructor
    private static class BookRowMapper implements RowMapper<Book> {

        private final GenreRepository genreRepository;
        private final AuthorRepository authorRepository;

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();

            book.setId(rs.getLong("id"));

            book.setTitle(rs.getString("title"));

            long genreId = rs.getLong("genre_id");
            Genre genre = genreRepository.findById(genreId).orElseGet(null);
            book.setGenre(genre);

            long authorId = rs.getLong("author_id");
            Author author = authorRepository.findById(authorId).orElseGet(null);
            book.setAuthor(author);

            return book;
        }
    }
}
