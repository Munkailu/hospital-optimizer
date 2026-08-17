package app;

import database.AppPaths;
import database.DatabaseManager;
import loader.CSVLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Entry point for Pod 1's pipeline: create schema (locked docs/interfaces.md),
 * validate the CSVs, load them into SQLite, then show a couple of sanity
 * queries and the Activity_Log / Test_Results contents. Owned by Pod 1.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("============================================");
        System.out.println(" Hospital Smart Service Operations Optimizer");
        System.out.println(" Pod 1 - Data, Database & Delivery");
        System.out.println("============================================");
        System.out.println();

        CSVLoader.loadAll();

        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            return;
        }

        try {
            System.out.println();
            System.out.println("Sanity checks");
            System.out.println("--------------------------------------------");
            System.out.println("Database file   : " + AppPaths.databaseFilePath());

            query(conn, "SELECT COUNT(*) AS n FROM Locations", "Locations");
            query(conn, "SELECT COUNT(*) AS n FROM Roads", "Roads");
            query(conn, "SELECT COUNT(*) AS n FROM Resources", "Resources");
            query(conn, "SELECT COUNT(*) AS n FROM Requests", "Requests");
            query(conn, "SELECT COUNT(*) AS n FROM Activity_Log", "Activity_Log rows");
            query(conn, "SELECT COUNT(*) AS n FROM Test_Results", "Test_Results rows");

            System.out.println();
            System.out.println("Sample rows");
            System.out.println("--------------------------------------------");
            queryRows(conn, "SELECT id, name, type FROM Locations ORDER BY id LIMIT 3");
            queryRows(conn, "SELECT id, type, urgency_level, status FROM Requests ORDER BY id LIMIT 3");

        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    private static void query(Connection conn, String sql, String label) throws Exception {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                System.out.printf("%-18s: %d%n", label, rs.getInt("n"));
            }
        }
    }

    private static void queryRows(Connection conn, String sql) throws Exception {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                StringBuilder sb = new StringBuilder("  ");
                for (int i = 1; i <= cols; i++) {
                    if (i > 1) sb.append(" | ");
                    sb.append(rs.getString(i));
                }
                System.out.println(sb);
            }
        }
    }
}