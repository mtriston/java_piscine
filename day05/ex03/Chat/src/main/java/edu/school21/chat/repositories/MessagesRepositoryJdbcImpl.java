package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import org.postgresql.util.PSQLException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource ds;
    private static final String INSERT_WITH_ID;
    private static final String INSERT_WITHOUT_ID;
    private static final String UPDATE_STATEMENT;

    static {
        INSERT_WITH_ID = "insert into chat.messages(id, author, room, text, date) values (?, ?, ?, ?, ?);";
        INSERT_WITHOUT_ID = "insert into chat.messages(author, room, text, date) values (?, ?, ?, ?) RETURNING id;";
        UPDATE_STATEMENT = "UPDATE chat.messages SET author = ?, room = ?, text = ?, date = ? WHERE id = ?;";
    }

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.ds = dataSource;
    }

    @Override
    public void save(Message msg) throws SQLException {
        if (msg.getAuthor() == null || msg.getRoom() == null)
            throw new NotSavedSubEntityException("author and room must be non-null");
        Connection c = ds.getConnection();
        PreparedStatement stmt;
        if (msg.getId() == null) {
            stmt = c.prepareStatement(INSERT_WITHOUT_ID);
            stmt.setLong(1, msg.getAuthor().getId());
            stmt.setLong(2, msg.getRoom().getId());
            stmt.setString(3, msg.getText());
            stmt.setTimestamp(4, msg.getDateTime());
        } else {
            stmt = c.prepareStatement(INSERT_WITH_ID);
            stmt.setLong(1, msg.getId());
            stmt.setLong(2, msg.getAuthor().getId());
            stmt.setLong(3, msg.getRoom().getId());
            stmt.setString(4, msg.getText());
            stmt.setTimestamp(5, msg.getDateTime());
        }
        try {
            stmt.execute();
        } catch (PSQLException e) {
            stmt.close();
            c.close();
            throw new NotSavedSubEntityException(e.getLocalizedMessage());
        }
        ResultSet resultSet = stmt.getResultSet();
        if (resultSet.next()) {
            msg.setId(resultSet.getLong(1));
        }
        resultSet.close();
        stmt.close();
        c.close();
    }

    @Override
    public void update(Message msg) throws SQLException {
        Connection c = ds.getConnection();
        Long authorId = msg.getAuthor() != null ? msg.getAuthor().getId() : null;
        Long roomId = msg.getRoom() != null ? msg.getRoom().getId() : null;
        PreparedStatement stmt = c.prepareStatement(UPDATE_STATEMENT);
        stmt.setObject(1, authorId);
        stmt.setObject(2, roomId);
        stmt.setObject(3, msg.getText());
        stmt.setObject(4, msg.getDateTime());
        stmt.setObject(5, msg.getId());
        try {
            stmt.execute();
        } catch (SQLException e) {
            throw new NotSavedSubEntityException(e.getMessage());
        } finally {
            stmt.close();
            c.close();
        }
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        Connection c = ds.getConnection();
        long authorId, roomId;
        String text;
        Timestamp date;
        User user = null;
        Room room = null;

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
            room = new Room(roomId, res.getString("name"), user, null);
        }
        res.close();
        c.close();
        return Optional.of(new Message(id, user, room, text, date));
    }
}
