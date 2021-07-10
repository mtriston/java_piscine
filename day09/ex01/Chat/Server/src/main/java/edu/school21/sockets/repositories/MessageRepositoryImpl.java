package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private UsersRepository usersRepository;
    private JdbcTemplate jdbcTemplate;

    final String findByIdQuery = "SELECT * FROM chat.messages WHERE id = ?";
    final String findAllQuery = "SELECT * FROM chat.messages";
    final String updateQuery = "UPDATE chat.messages SET sender = ?, text = ?, time = ? WHERE id = ?";
    final String saveQuery = "INSERT INTO chat.messages(sender, text, time) VALUES (?, ?, ?)";
    final String deleteQuery = "DELETE FROM chat.messages WHERE id = ?";

    public MessageRepositoryImpl(DataSource dataSource, UsersRepository usersRepository) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        this.usersRepository = usersRepository;
    }

    private Message mapRowToMessage(ResultSet resultSet, int rowNum) throws SQLException {
        return new Message(
                resultSet.getLong("id"),
                usersRepository.findById(resultSet.getLong("sender")),
                resultSet.getString("text"),
                resultSet.getTimestamp("time")
        );
    }

    @Override
    public Message findById(Long id) {
        return jdbcTemplate.queryForObject(findByIdQuery, this::mapRowToMessage, id);
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query(findAllQuery, this::mapRowToMessage);
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update(saveQuery, entity.getSender().getId(), entity.getText(), entity.getTimestamp());
    }

    @Override
    public void update(Message entity) {
        jdbcTemplate.update(updateQuery, entity.getSender(), entity.getText(), entity.getTimestamp(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(deleteQuery, id);
    }
}
