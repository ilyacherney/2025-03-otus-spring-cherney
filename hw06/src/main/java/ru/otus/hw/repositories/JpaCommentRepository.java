package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> findById(long id) {
        Query query = em.createQuery(
                "SELECT c FROM Comment c " +
                "WHERE c.id = :id",
                Comment.class);

        query.setParameter("id", id);
        Comment comment = (Comment) query.getSingleResult();
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAllByBookId(long bookId) {
        Query query = em.createQuery(
                "SELECT c FROM Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Comment saveComment(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteComment(long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
    }
}
