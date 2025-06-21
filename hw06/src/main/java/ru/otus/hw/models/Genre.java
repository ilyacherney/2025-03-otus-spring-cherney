package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private String name;
}
