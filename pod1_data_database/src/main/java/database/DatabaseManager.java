package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Opens and closes the SQLite connection used by the whole pipeline.
 * The database file's parent folder is created on first use; the file itself
 * is created by SQLite on the first connection. Owned by Pod 1.
 */
public class DatabaseManager {

    public static Connection getConnection() {
        return getConnection(AppPaths.databaseFilePath());
    }

    public static Connection getConnection(String dbFilePath) {
        try {
            createParentFolder(dbFilePath);

            String url = "jdbc:sqlite:" + dbFilePath;

            System.out.println("Connected to SQLite database: " + dbFilePath);

            return DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + dbFilePath);
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createParentFolder(String dbFilePath) {
        int lastSlash = Math.max(dbFilePath.lastIndexOf('/'), dbFilePath.lastIndexOf('\\'));
        if (lastSlash < 0) {
            return;
        }
        java.io.File parent = new java.io.File(dbFilePath.substring(0, lastSlash));
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    /** Simple elapsed-time formatter used for the Test_Results runtime. */
    public static String formatMillis(long millis) {
        return String.format(Locale.ROOT, "%.3f s", millis / 1000.0);
    }
}