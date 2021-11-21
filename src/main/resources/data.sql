create table article(
    id bigserial not null,
    title varchar(100) not null,
    author varchar(255) not null,
    content text not null,
    date_of_publishing date
);