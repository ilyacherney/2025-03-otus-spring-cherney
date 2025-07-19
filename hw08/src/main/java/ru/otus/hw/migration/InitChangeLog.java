//package ru.otus.hw.migration;
//
//import com.github.cloudyrock.mongock.ChangeLog;
//import com.github.cloudyrock.mongock.ChangeSet;
//import ru.otus.hw.models.Author;
//import ru.otus.hw.repositories.AuthorRepository;
//import ru.otus.hw.repositories.BookRepository;
//
//@ChangeLog(order = "001")
//public class InitChangeLog {
//    @ChangeSet(order = "001", id = "seedAuthorsAndGenres", author = "you")
//    public void seedAuthorsAndGenres(AuthorRepository ar
////            ,
////                                     GenreRepository gr
//    ) {
//        ar.save(new Author("Author_1"));
//        ar.save(new Author("Author_2"));
////        gr.save(new Genre("Genre_1"));
////        gr.save(new Genre("Genre_2"));
//    }
//
//    @ChangeSet(order = "002", id = "seedBooksAndComments", author = "you")
//    public void seedBooksAndComments(BookRepository br
////            , CommentRepository cr
//            , AuthorRepository ar
////            , GenreRepository gr
//    ) {
//        Author a1 = ar.findByFullName("Author_1").orElseThrow();
////        Genre g1 = gr.findByName("Genre_1").orElseThrow();
////        Book b1 = br.save(new Book("BookTitle_1", a1, g1));
////        cr.save(new Comment("Great book!", b1));
//    }
//}a