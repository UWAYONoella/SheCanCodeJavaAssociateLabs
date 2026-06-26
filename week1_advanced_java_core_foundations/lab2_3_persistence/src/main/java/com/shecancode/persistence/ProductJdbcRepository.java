package com.shecancode.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductJdbcRepository {
    public void save(Product product) {

        String sql = """
            INSERT INTO products(id, name, stock, price)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, product.getId());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getStock());
            statement.setDouble(4, product.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product findById(String id) {

        String sql = """
            SELECT * FROM products
            WHERE id = ?
            """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Product(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}