package database;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pod 1: verifies the locked docs/interfaces.md schema (plus the two log
 * tables) is created correctly and idempotently. Owned by Pod 1.
 */
class SchemaInitializerTest {

    @Test
    void createsAllLockedTables() throws Exception {
        Path db = Files.createTempFile("pod1-schema", ".db");
        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            SchemaInitializer.createTables(conn);

            assertTrue(SchemaInitializer.tableExists(conn, "Locations"));
            assertTrue(SchemaInitializer.tableExists(conn, "Roads"));
            assertTrue(SchemaInitializer.tableExists(conn, "Resources"));
            assertTrue(SchemaInitializer.tableExists(conn, "Requests"));
            assertTrue(SchemaInitializer.tableExists(conn, "Activity_Log"));
            assertTrue(SchemaInitializer.tableExists(conn, "Test_Results"));
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void noPatientsTableInLockedSchema() throws Exception {
        Path db = Files.createTempFile("pod1-schema", ".db");
        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            SchemaInitializer.createTables(conn);
            assertFalse(SchemaInitializer.tableExists(conn, "Patients"));
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void lockedSchemaColumnsAreExact() throws Exception {
        Path db = Files.createTempFile("pod1-schema", ".db");
        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            SchemaInitializer.createTables(conn);

            assertTrue(columnExists(conn, "Locations", "name"));
            assertTrue(columnExists(conn, "Locations", "type"));
            assertFalse(columnExists(conn, "Locations", "floor"));

            assertTrue(columnExists(conn, "Roads", "from_location_id"));
            assertTrue(columnExists(conn, "Roads", "distance"));
            assertFalse(columnExists(conn, "Roads", "estimated_time"));

            assertTrue(columnExists(conn, "Resources", "availability_status"));
            assertFalse(columnExists(conn, "Resources", "resource_name"));

            assertTrue(columnExists(conn, "Requests", "urgency_level"));
            assertTrue(columnExists(conn, "Requests", "submitted_time"));
            assertTrue(columnExists(conn, "Requests", "origin_location_id"));
            assertFalse(columnExists(conn, "Requests", "patient_id"));
            assertFalse(columnExists(conn, "Requests", "resource_id"));
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void schemaIsIdempotent() throws Exception {
        Path db = Files.createTempFile("pod1-schema", ".db");
        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            SchemaInitializer.createTables(conn);
            SchemaInitializer.createTables(conn);

            List<String> tables = SchemaInitializer.listTables(conn);
            long tablesNamed = tables.stream()
                    .filter(name -> List.of("Locations", "Roads", "Resources", "Requests",
                                    "Activity_Log", "Test_Results")
                            .contains(name))
                    .count();
            assertTrue(tablesNamed == 6, "Expected exactly 6 tables, found " + tables);
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    private static boolean columnExists(Connection conn, String table, String column) throws Exception {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("PRAGMA table_info(" + table + ")")) {
            while (rs.next()) {
                if (column.equalsIgnoreCase(rs.getString("name"))) {
                    return true;
                }
            }
        }
        return false;
    }
}