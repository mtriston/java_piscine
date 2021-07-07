package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String query = "SELECT * FROM products;";
        List<Product> products = new LinkedList<>();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.execute();
        ResultSet resultSet = stmt.getResultSet();
        while (resultSet.next()) {
            products.add(new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getFloat("price")
            ));
        }
        stmt.close();
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        Product product = null;
        String query = "SELECT * FROM products WHERE id = ?;";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setObject(1, id);
        stmt.execute();
        ResultSet resultSet = stmt.getResultSet();
        if (resultSet.next()) {
            product = new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getFloat("price")
            );
        }
        stmt.close();
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) throws SQLException {
        String query = "UPDATE products SET name = ?, price = ? WHERE id = ?;";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setObject(1, product.getName());
        stmt.setObject(2, product.getPrice());
        stmt.setObject(3, product.getId());
        stmt.execute();
        stmt.close();
    }

    @Override
    public void save(Product product) throws SQLException {
        String query = "INSERT INTO products (name, price) VALUES (?, ?);";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setObject(1, product.getName());
        stmt.setObject(2, product.getPrice());
        stmt.execute();
        ResultSet resultSet = connection.createStatement().executeQuery("CALL IDENTITY();");
        if (resultSet.next()) {
            product.setId(resultSet.getLong(1));
        }
        stmt.close();
    }

    @Override
    public void delete(Long id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?;";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setObject(1, id);
        stmt.execute();
        stmt.close();
    }
}
