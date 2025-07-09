package ru.otus.hw.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    @Transactional
    public Comment insertComment(String text, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));
        Comment comment = new Comment(null, text, book);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + id + " not found"));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Long commentId, String text) {
        Comment comment = findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id " + commentId + " not found"));
        comment.setText(text);
        return commentRepository.save(comment);
    }
}
