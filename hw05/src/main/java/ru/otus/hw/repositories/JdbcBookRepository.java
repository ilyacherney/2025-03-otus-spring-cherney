package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>();
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
        // todo: ...
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

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return null;
        }
    }
}
