package ru.otus.hw.models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.NamedAttributeNode;
//import jakarta.persistence.NamedEntityGraph;
//import jakarta.persistence.Table;
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
//@AllArgsConstructor
//@NoArgsConstructor
public class Book {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Book)) {
//            return false;
//        }
//        Book book = (Book) o;
//        return id != 0 && id == book.id;
//    }
//
//    @Override
//    public int hashCode() {
//        return Long.hashCode(id);
//    }
}
