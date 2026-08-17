package loader;

import database.AppPaths;
import database.DatabaseManager;
import database.SchemaInitializer;
import helper.TestFiles;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pod 1 end-to-end loader tests. A bad-input run must abort before anything
 * is written; the normal run loads the full locked schema and records log rows.
 * Owned by Pod 1.
 */
class CSVLoaderTest {

    private static final int EXPECTED_LOCATIONS = 50;
    private static final int EXPECTED_ROADS = 100;
    private static final int EXPECTED_RESOURCES = 30;
    private static final int EXPECTED_REQUESTS = 300;

    @Test
    void loadsRealDatasetIntoLockedSchema() throws Exception {
        Path db = Files.createTempFile("pod1-load", ".db");
        Files.delete(db); // let SQLite create it fresh

        CSVLoader.LoadResult result =
                CSVLoader.run(AppPaths.dataFolder(), db.toString(), null);

        assertEquals(EXPECTED_LOCATIONS, result.locations);
        assertEquals(EXPECTED_ROADS, result.roads);
        assertEquals(EXPECTED_RESOURCES, result.resources);
        assertEquals(EXPECTED_REQUESTS, result.requests);
        assertTrue(result.elapsedMs >= 0);

        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            assertEquals(EXPECTED_LOCATIONS, count(conn, "Locations"));
            assertEquals(EXPECTED_ROADS, count(conn, "Roads"));
            assertEquals(EXPECTED_RESOURCES, count(conn, "Resources"));
            assertEquals(EXPECTED_REQUESTS, count(conn, "Requests"));

            assertTrue(count(conn, "Activity_Log") >= 2, "activity log should have entries");
            assertEquals(1, count(conn, "Test_Results"), "one PASS row expected");

            // Spot-check a request row mapped from CSV extras -> locked columns.
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT type, urgency_level, status, submitted_time, "
                                 + "origin_location_id, destination_location_id "
                                 + "FROM Requests WHERE id = 1")) {
                assertTrue(rs.next());
                assertEquals("Equipment Delivery", rs.getString("type"));
                assertEquals(1, rs.getInt("urgency_level"));
                assertEquals("Completed", rs.getString("status"));

                // patient_id / resource_id extras must NOT be loadable.
                assertFalse(columnExists(conn, "Requests", "patient_id"));
                assertFalse(columnExists(conn, "Requests", "resource_id"));
            }
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void schemaTablesExistAfterLoad() throws Exception {
        Path db = Files.createTempFile("pod1-load", ".db");
        Files.delete(db);

        CSVLoader.run(AppPaths.dataFolder(), db.toString(), null);

        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            assertTrue(SchemaInitializer.tableExists(conn, "Activity_Log"));
            assertTrue(SchemaInitializer.tableExists(conn, "Test_Results"));
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void badInputAbortsBeforeLoadingAnything() throws Exception {
        Path folder = TestFiles.newTempFolder();
        TestFiles.write(folder, "locations.csv", TestFiles.validLocations());
        TestFiles.write(folder, "roads.csv", TestFiles.validRoads());
        TestFiles.write(folder, "resources.csv", TestFiles.validResources());
        TestFiles.write(folder, "requests.csv", TestFiles.badUrgencyRequests());

        Path db = Files.createTempFile("pod1-load", ".db");
        Files.delete(db);

        assertThrows(RuntimeException.class,
                () -> CSVLoader.run(folder.toString() + java.io.File.separator, db.toString(), null));

        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            assertEquals(0, count(conn, "Requests"));
            assertEquals(0, count(conn, "Locations"));
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void repeatedLoadDoesNotDuplicateRows() throws Exception {
        Path db = Files.createTempFile("pod1-load", ".db");
        Files.delete(db);

        String dataFolder = AppPaths.dataFolder();

        CSVLoader.run(dataFolder, db.toString(), null);
        CSVLoader.run(dataFolder, db.toString(), null);

        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            assertEquals(EXPECTED_LOCATIONS, count(conn, "Locations"));
            assertEquals(EXPECTED_ROADS, count(conn, "Roads"));
            assertEquals(EXPECTED_RESOURCES, count(conn, "Resources"));
            assertEquals(EXPECTED_REQUESTS, count(conn, "Requests"));
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    @Test
    void logTablesAreAppendOnly() throws Exception {
        Path db = Files.createTempFile("pod1-load", ".db");
        Files.delete(db);

        String dataFolder = AppPaths.dataFolder();

        CSVLoader.run(dataFolder, db.toString(), null);
        CSVLoader.run(dataFolder, db.toString(), null);

        Connection conn = DatabaseManager.getConnection(db.toString());
        try {
            assertEquals(2, count(conn, "Test_Results"));
            List<String> results = resultsOf(conn);
            assertTrue(results.stream().allMatch("PASS"::equals), "all load runs should be PASS");
        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    private static int count(Connection conn, String table) throws Exception {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS n FROM " + table)) {
            rs.next();
            return rs.getInt("n");
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

    private static List<String> resultsOf(Connection conn) throws Exception {
        List<String> results = new java.util.ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT result FROM Test_Results")) {
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        }
        return results;
    }
}