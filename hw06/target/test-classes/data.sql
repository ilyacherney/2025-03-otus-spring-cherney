insert into authors (full_name) values ( 'Leo Tolstoy');
insert into authors (full_name) values ('Alexander Pushkin');
insert into genres (name) values ('Novel');
insert into genres (name) values ('Fairytale');
insert into books (title, author_id, genre_id) values ( 'War And Peace', 1, 1);
insert into books (title, author_id, genre_id) values ( 'Eugene Onegin', 2, 1);
insert into comments (book_id, text) values ( 1, 'Good book!');