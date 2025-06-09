package ru.otus.hw.repositories;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Repository
@AllArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public List<Author> findAll() {
        return jdbc.query("SELECT id, full_name FROM authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Optional<Author> authorOptional;

        try {
            authorOptional = Optional.of(jdbc.queryForObject(
                    "SELECT id, full_name " +
                        "FROM authors " +
                        "WHERE id = :id", params, new AuthorRowMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }

        return authorOptional;
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
