package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        var graph = em.getEntityGraph("book-entity-graph-with-author-genre");
        Map<String, Object> props = Map.of("jakarta.persistence.fetchgraph", graph);
        return Optional.ofNullable(em.find(Book.class, id, props));
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery(
                "SELECT b FROM Book b", Book.class)
                .setHint("jakarta.persistence.fetchgraph", em.getEntityGraph("book-entity-graph-with-author-genre"))
                .getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
