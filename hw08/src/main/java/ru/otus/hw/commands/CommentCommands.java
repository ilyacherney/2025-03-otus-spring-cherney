package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by ID", key = "cbid")
    public String findCommentById(String id) {
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    // cbbid 1
    @ShellMethod(value = "Find comment by book ID", key = "cbbid")
    public String findCommentsByBookId(String bookId) {
        return commentService.findAllByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    // cins --text "Very good book" --bookId 1
    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String text, String bookId) {
        Comment savedComment = commentService.insertComment(text, bookId);
        return commentConverter.commentToString(savedComment);
    }

    // cdel --id 3
    @ShellMethod(value = "Delete comment", key = "cdel")
    public void deleteComment(String id) {
        commentService.deleteComment(id);
    }

    // cupd 1 "The best book ever"
    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String commentId, String text) {
        Comment comment = commentService.updateComment(commentId, text);
        return commentConverter.commentToString(comment);
    }
}
