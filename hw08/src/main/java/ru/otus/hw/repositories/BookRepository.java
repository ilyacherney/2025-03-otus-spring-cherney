package ru.otus.hw.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, Long> {

    @Override
//    @EntityGraph(value = "book-entity-graph-with-author-genre", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findById(Long id);

//    @EntityGraph(value = "book-entity-graph-with-author-genre", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();
}
