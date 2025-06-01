package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    // todo [ ] genre repo findAll():
    @Override
    public List<Genre> findAll() {
        List<Genre> genres = new ArrayList<>();

        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2", "sa", "");
            System.out.println(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return genres;
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.empty();
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            // todo [ ]:
            return null;
        }
    }
}
