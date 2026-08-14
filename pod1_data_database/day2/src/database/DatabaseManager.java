package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DB_URL =
            "jdbc:sqlite:pod1_data_database/day2/database/hospital_optimizer.db";

    public static Connection getConnection() {

        try {
            Connection conn = DriverManager.getConnection(DB_URL);

            System.out.println("✅ Connected to SQLite database.");

            return conn;

        } catch (SQLException e) {

            System.out.println("❌ Failed to connect to database.");

            e.printStackTrace();

            return null;
        }
    }

    public static void closeConnection(Connection conn) {

        try {

            if (conn != null) {

                conn.close();

                System.out.println("✅ Database connection closed.");

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}