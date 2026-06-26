package com.shecancode.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL =
            "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {

        String sql = """
                CREATE TABLE IF NOT EXISTS products (
                    id VARCHAR(50) PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    stock INT NOT NULL,
                    price DOUBLE NOT NULL
                )
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);

            System.out.println("Products table created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
