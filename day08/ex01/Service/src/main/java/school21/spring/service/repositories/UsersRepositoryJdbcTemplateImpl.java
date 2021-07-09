package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    final String findByIdQuery = "SELECT * FROM chat.users WHERE id = ?";
    final String findAllQuery = "SELECT * FROM chat.users";
    final String updateQuery = "UPDATE chat.users SET email = ? WHERE id = ?";
    final String saveQuery = "INSERT INTO chat.users(email) VALUES (?)";
    final String deleteQuery = "DELETE FROM chat.users WHERE id = ?";
    final String findByEmailQuery = "SELECT * FROM chat.users WHERE email = ?";

    public UsersRepositoryJdbcTemplateImpl(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return new User(resultSet.getLong("id"), resultSet.getString("email"));
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject(findByIdQuery, this::mapRowToUser, id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(findAllQuery, this::mapRowToUser);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(saveQuery, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(updateQuery, entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(deleteQuery, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(findByEmailQuery, this::mapRowToUser, email));
    }
}
