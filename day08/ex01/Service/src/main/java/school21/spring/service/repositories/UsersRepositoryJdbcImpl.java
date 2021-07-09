package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource ds;

    public UsersRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try (Connection c = ds.getConnection()) {
            String query = "SELECT * FROM chat.users WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setObject(1, id);
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email")
                        );
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> products = new LinkedList<>();
        try (Connection c = ds.getConnection()){
            String query = "SELECT * FROM chat.users;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                products.add(new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email")
                ));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return products;
    }

    @Override
    public void save(User entity) {
        try (Connection c = ds.getConnection()) {
            String query = "INSERT INTO chat.users (email) VALUES (?) RETURNING id;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setObject(1, entity.getEmail());
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void update(User entity) {
        try (Connection c = ds.getConnection()) {
            String query = "UPDATE chat.users SET email = ? WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setObject(1, entity.getEmail());
            stmt.execute();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection c = ds.getConnection()) {
            String query = "DELETE FROM chat.users WHERE id = ?;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setObject(1, id);
            stmt.execute();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try (Connection c = ds.getConnection()) {
            String query = "SELECT * FROM chat.users WHERE email = ?;";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setObject(1, email);
            stmt.execute();
            ResultSet resultSet = stmt.getResultSet();
            if (resultSet.next()) {
                user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("email")
                );
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return Optional.ofNullable(user);
    }
}
