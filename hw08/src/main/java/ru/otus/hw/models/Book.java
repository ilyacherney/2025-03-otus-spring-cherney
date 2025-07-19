package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

//@NamedEntityGraph(
//        name = "book-entity-graph-with-author-genre",
//        attributeNodes = {
//                @NamedAttributeNode("author"),
//                @NamedAttributeNode("genre")})
//@Entity
//@Table(name = "books")
@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private String id;

    private String title;

//    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;
//
//    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}
