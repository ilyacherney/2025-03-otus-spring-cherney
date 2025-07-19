package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    private String id;

    private String fullName;

    public Author(String fullName) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
    }
}
