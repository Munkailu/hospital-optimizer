package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the database tables from the locked schema shipped in
 * src/main/resources/database/schema.sql. This is the missing piece that made
 * the old loader crash on a fresh checkout ("no such table"). Owned by Pod 1.
 */
public class SchemaInitializer {

    public static final String SCHEMA_RESOURCE = "/database/schema.sql";

    /** Applies the locked schema (data tables + log tables) to the given connection. */
    public static void createTables(Connection conn) throws SQLException, IOException {
        String sql = loadSchema();
        try (Statement stmt = conn.createStatement()) {
            for (String statement : splitStatements(sql)) {
                stmt.executeUpdate(statement);
            }
        }
    }

    /** Runs the schema against the default database. */
    public static void createTablesDefault() throws SQLException, IOException {
        Connection conn = DatabaseManager.getConnection();
        try {
            createTables(conn);
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    /** Lists the user tables present in the connected database. */
    public static List<String> listTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM sqlite_master WHERE type = 'table' ORDER BY name")) {
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        }
        return tables;
    }

    /** True when a table exists in the connected database. */
    public static boolean tableExists(Connection conn, String tableName) throws SQLException {
        for (String name : listTables(conn)) {
            if (name.equalsIgnoreCase(tableName)) {
                return true;
            }
        }
        return false;
    }

    /** Returns the SQL Kitchen-Sink style semicolon splitter used on schema.sql. */
    static List<String> splitStatements(String sql) {
        List<String> statements = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (String line : sql.split("\r?\n")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("--")) {
                continue;
            }
            current.append(line).append('\n');
            if (trimmed.endsWith(";")) {
                statements.add(current.toString());
                current.setLength(0);
            }
        }
        return statements;
    }

    private static String loadSchema() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = SchemaInitializer.class.getResourceAsStream(SCHEMA_RESOURCE);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }
}