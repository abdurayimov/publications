create table article(
    id bigint auto_increment primary key,
    title varchar(100) not null,
    author varchar(255) not null,
    content text not null,
    dateOfPublishing varchar(100)
);