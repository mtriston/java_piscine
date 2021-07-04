insert into chat.users (login, password) values ('user1', '111');
insert into chat.users (login, password) values ('user2', '222');
insert into chat.users (login, password) values ('user3', '333');
insert into chat.users (login, password) values ('user4', '444');
insert into chat.users (login, password) values ('user5', '555');

insert into chat.rooms (name, owner) values ('room1', 1);
insert into chat.rooms (name, owner) values ('room2', 2);
insert into chat.rooms (name, owner) values ('room3', 3);
insert into chat.rooms (name, owner) values ('room4', 4);
insert into chat.rooms (name, owner) values ('room5', 5);

insert into chat.messages (author, room, text, date)
values(1, 1, 'message1', current_timestamp);
insert into chat.messages (author, room, text, date)
values(2, 2, 'message2', current_timestamp);
insert into chat.messages (author, room, text, date)
values(3, 3, 'message3', current_timestamp);
insert into chat.messages (author, room, text, date)
values(4, 4, 'message4', current_timestamp);
insert into chat.messages (author, room, text, date)
values(5, 5, 'message5', current_timestamp);