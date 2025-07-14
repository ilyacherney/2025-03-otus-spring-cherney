package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long id);

    List<Comment> findAllByBookId(Long bookId);

    Comment insertComment(String text, Long bookId);

    void deleteComment(Long id);

    Comment updateComment(Long commentId, String text);
}
