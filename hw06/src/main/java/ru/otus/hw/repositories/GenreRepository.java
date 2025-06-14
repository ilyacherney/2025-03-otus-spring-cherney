package ru.otus.hw.repositories;

import jakarta.persistence.PersistenceContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository {
//
//    // todo: это реализация Spring Data JPA, а нужно просто Spring JPA
    List<Genre> findAll();
//
    Optional<Genre> findById(long id);
}
