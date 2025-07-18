package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(long id);

    List<Comment> findAllByBookId(long bookId);

    Comment insertComment(String text, long bookId);

    void deleteComment(long id);

    Comment updateComment(long commentId, String text);
}
