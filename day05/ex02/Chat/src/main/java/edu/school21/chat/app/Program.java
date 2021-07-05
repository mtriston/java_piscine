package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Program {
    private static final String DB_URL = "jdbc:postgresql://localhost/chat";
    private static final String DB_USER = "mtriston";
    private static final String DB_PASSWORD = "mtriston";
    private static final String DB_SCHEMA = "/schema.sql";
    private static final String DB_DATA = "/data.sql";
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASSWORD);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void execQueries(Connection connection, String file) {
        try (InputStream in = Objects.requireNonNull(Program.class.getResourceAsStream(file))) {
            StringBuilder data = new StringBuilder();
            new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.toList()).forEach(data::append);
            connection.createStatement().execute(data.toString());
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        execQueries(connection, DB_SCHEMA);
        execQueries(connection, DB_DATA);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(ds);

        User creator = new User(1L, "user", "user", new ArrayList<>(), new ArrayList<>());
        Room room = new Room(2L, "room", creator, new ArrayList<>());

        Message message = new Message(null, creator, room, "Hello!",new Timestamp(System.currentTimeMillis()));
        messagesRepository.save(message);
        System.out.printf("message.id = %d\n", message.getId());

        creator.setId(100L);
        Message message1 = new Message(null, creator, room, "Hello!",new Timestamp(System.currentTimeMillis()));
        messagesRepository.save(message1);
        System.out.printf("message.id = %d\n", message1.getId());
    }
}