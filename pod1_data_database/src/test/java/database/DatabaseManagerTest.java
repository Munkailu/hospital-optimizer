package database;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pod 1: verifies DatabaseManager can open connections against a fresh path,
 * creating parent folders along the way (the loader has to run on a fresh
 * checkout where the database folder does not exist yet). Owned by Pod 1.
 */
class DatabaseManagerTest {

    @Test
    void connectsToFreshDatabaseFile() throws Exception {
        Path sub = Files.createTempDirectory("pod1-db");
        String dbPath = sub.resolve("nested").resolve("folder").resolve("test.db").toString();

        Connection conn = DatabaseManager.getConnection(dbPath);
        assertNotNull(conn, "Connection should open (and create folders) on a fresh path");
        assertTrue(conn.isValid(2));
        DatabaseManager.closeConnection(conn);
    }
}