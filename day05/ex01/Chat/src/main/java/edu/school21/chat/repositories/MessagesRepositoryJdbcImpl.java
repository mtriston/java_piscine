package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource ds;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.ds = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        Connection c = ds.getConnection();
        long authorId, roomId;
        String text;
        Timestamp date;
        User user = null;
        Chatroom chatroom = null;

        ResultSet res = c.createStatement().executeQuery(String.format("SELECT * FROM chat.messages WHERE id = %d;", id));
        if (res.next()) {
            authorId = res.getLong("author");
            roomId = res.getLong("room");
            text = res.getString("text");
            date = res.getTimestamp("date");
        } else {
            c.close();
            return Optional.empty();
        }
        res.close();
        res = c.createStatement().executeQuery(String.format("SELECT * FROM chat.users WHERE id = %d;", authorId));
        if (res.next()) {
            user = new User(
                    res.getLong("id"),
                    res.getString("login"),
                    res.getString("password"),
                    null,
                    null
            );
        }
        res.close();
        res = c.createStatement().executeQuery(String.format("SELECT * FROM chat.rooms WHERE id = %d;", roomId));
        if (res.next()) {
            chatroom = new Chatroom(roomId, res.getString("name"), user, null);
        }
        res.close();
        c.close();
        return Optional.of(new Message(id, user, chatroom, text, date));
    }
}
