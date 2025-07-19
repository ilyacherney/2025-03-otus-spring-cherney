package ru.otus.hw.models;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity
@Document(collection = "genre")
//@Table(name = "genres")
public class Genre {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String id;

    private String name;
}
