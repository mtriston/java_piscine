drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.users (
     id serial primary key,
     login text unique not null,
     password text not null
);

create table if not exists chat.rooms (
    id serial primary key,
    name text unique not null,
    owner int references chat.users(id) not null
);

create table if not exists chat.messages (
    id serial primary key,
    author int references chat.users(id),
    room int references chat.rooms(id),
    text text,
    date TIMESTAMP default CURRENT_TIMESTAMP
);