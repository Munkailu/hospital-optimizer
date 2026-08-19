package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL =
            "jdbc:sqlite:pod1_data_database/day2/database/hospital_optimizer.db";

    // =====================================================
    // Get Database Connection
    // =====================================================

    public static Connection getConnection() {

        try {

            Connection conn = DriverManager.getConnection(DB_URL);

            // Enable foreign key constraints
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }

            System.out.println("✅ Connected to SQLite database.");

            // Create required tables
            createTables(conn);

            return conn;

        } catch (SQLException e) {

            System.out.println("❌ Failed to connect to database.");

            e.printStackTrace();

            return null;
        }
    }

    // =====================================================
    // Create Database Tables
    // =====================================================

    private static void createTables(Connection conn) throws SQLException {

        try (Statement stmt = conn.createStatement()) {

            // -------------------------------------------------
            // Locations
            // -------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Locations (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL" +
                ")"
            );

            // -------------------------------------------------
            // Roads
            // -------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Roads (" +
                "id INTEGER PRIMARY KEY, " +
                "from_location_id INTEGER NOT NULL, " +
                "to_location_id INTEGER NOT NULL, " +
                "distance REAL NOT NULL, " +
                "FOREIGN KEY (from_location_id) REFERENCES Locations(id), " +
                "FOREIGN KEY (to_location_id) REFERENCES Locations(id)" +
                ")"
            );

            // -------------------------------------------------
            // Resources
            // -------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Resources (" +
                "id INTEGER PRIMARY KEY, " +
                "type TEXT NOT NULL, " +
                "availability_status TEXT NOT NULL, " +
                "current_location_id INTEGER NOT NULL, " +
                "FOREIGN KEY (current_location_id) REFERENCES Locations(id)" +
                ")"
            );

            // -------------------------------------------------
            // Requests
            // -------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Requests (" +
                "id INTEGER PRIMARY KEY, " +
                "type TEXT NOT NULL, " +
                "urgency_level INTEGER NOT NULL CHECK (urgency_level BETWEEN 1 AND 5), " +
                "submitted_time TEXT NOT NULL, " +
                "origin_location_id INTEGER NOT NULL, " +
                "destination_location_id INTEGER NOT NULL, " +
                "status TEXT NOT NULL, " +
                "FOREIGN KEY (origin_location_id) REFERENCES Locations(id), " +
                "FOREIGN KEY (destination_location_id) REFERENCES Locations(id)" +
                ")"
            );

            // -------------------------------------------------
            // Activity Log
            // -------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Activity_Log (" +
                "LogID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Action TEXT NOT NULL, " +
                "Timestamp TEXT NOT NULL, " +
                "User TEXT NOT NULL" +
                ")"
            );

            // -------------------------------------------------
            // Test Results
            // -------------------------------------------------

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Test_Results (" +
                "TestID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Module TEXT NOT NULL, " +
                "Runtime REAL, " +
                "Result TEXT NOT NULL" +
                ")"
            );

            System.out.println("✅ Database tables verified/created.");

        }
    }

    // =====================================================
    // Close Database Connection
    // =====================================================

    public static void closeConnection(Connection conn) {

        if (conn != null) {

            try {

                conn.close();

                System.out.println("✅ Database connection closed.");

            } catch (SQLException e) {

                System.out.println("❌ Error closing database connection.");

                e.printStackTrace();

            }
        }
    }
}