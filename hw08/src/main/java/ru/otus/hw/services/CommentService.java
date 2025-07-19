package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(String id);

    List<Comment> findAllByBookId(String bookId);

    Comment insertComment(String text, String bookId);

    void deleteComment(String id);

    Comment updateComment(String commentId, String text);
}
