package loader;

import database.AppPaths;
import database.DatabaseManager;
import database.SchemaInitializer;
import validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Pod 1's CSV dataset into SQLite using the LOCKED schema from
 * docs/interfaces.md. Tables are created first (so a fresh checkout no longer
 * crashes with "no such table") and every run is recorded in Activity_Log and
 * Test_Results. CSV layout keeps Pod 1's internal extras that are NOT loaded
 * into the database:
 *   locations: id,name,type,floor
 *   roads:     id,from_location_id,to_location_id,distance,estimated_time
 *   resources: id,type,resource_name,availability_status,current_location_id
 *   requests:  id,patient_id,resource_id,origin,destination,type,urgency,status,submitted_time
 * Owned by Pod 1.
 */
public class CSVLoader {

    public static void loadAll() throws Exception {
        run(AppPaths.dataFolder(), AppPaths.databaseFilePath(), System.out);
    }

    public static LoadResult run(String dataFolder, String dbPath, PrintStream out) throws Exception {
        long start = System.currentTimeMillis();
        if (out == null) {
            out = new PrintStream(java.io.OutputStream.nullOutputStream());
        }

        Connection conn = DatabaseManager.getConnection(dbPath);
        if (conn == null) {
            throw new RuntimeException("Cannot connect to database: " + dbPath);
        }

        try {
            SchemaInitializer.createTables(conn);

            List<String> problems = new ArrayList<>();
            boolean valid = Validator.validateAll(dataFolder, problems);

            if (!valid) {
                for (String problem : problems) {
                    out.println("  - " + problem);
                }
                logActivity(conn, "CSV load aborted: validation failed", "validator");
                throw new RuntimeException("Validation failed; nothing was loaded.");
            }
            out.println("Validation passed (" + problems.size() + " problem(s) logged).");

            clearData(conn);
            logActivity(conn, "Cleared old data", "loader");

            int locations = loadLocations(conn, dataFolder);
            int roads = loadRoads(conn, dataFolder);
            int resources = loadResources(conn, dataFolder);
            int requests = loadRequests(conn, dataFolder);

            logActivity(conn, "Loaded " + (locations + roads + resources + requests) + " records", "loader");

            long elapsed = System.currentTimeMillis() - start;
            recordTestResult(conn, "CSV full load", elapsed, "PASS");

            printSummary(out, dbPath, locations, roads, resources, requests, elapsed);

            return new LoadResult(locations, roads, resources, requests, elapsed);

        } finally {
            DatabaseManager.closeConnection(conn);
        }
    }

    // ------------------------ loaders (locked-schema columns only) ------------------------

    private static int loadLocations(Connection conn, String dataFolder) throws Exception {
        String sql = "INSERT INTO Locations (id, name, type) VALUES (?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(dataFolder + "locations.csv"));
             PreparedStatement ps = conn.prepareStatement(sql)) {
            br.readLine(); // skip header: id,name,type,floor
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                ps.setInt(1, Integer.parseInt(d[0].trim()));
                ps.setString(2, d[1].trim());
                ps.setString(3, d[2].trim());
                ps.executeUpdate();
                count++;
            }
            return count;
        }
    }

    private static int loadRoads(Connection conn, String dataFolder) throws Exception {
        String sql = "INSERT INTO Roads (id, from_location_id, to_location_id, distance) VALUES (?, ?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(dataFolder + "roads.csv"));
             PreparedStatement ps = conn.prepareStatement(sql)) {
            br.readLine(); // id,from_location_id,to_location_id,distance,estimated_time
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                ps.setInt(1, Integer.parseInt(d[0].trim()));
                ps.setInt(2, Integer.parseInt(d[1].trim()));
                ps.setInt(3, Integer.parseInt(d[2].trim()));
                ps.setDouble(4, Double.parseDouble(d[3].trim()));
                ps.executeUpdate();
                count++;
            }
            return count;
        }
    }

    private static int loadResources(Connection conn, String dataFolder) throws Exception {
        String sql = "INSERT INTO Resources (id, type, availability_status, current_location_id) VALUES (?, ?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(dataFolder + "resources.csv"));
             PreparedStatement ps = conn.prepareStatement(sql)) {
            br.readLine(); // id,type,resource_name,availability_status,current_location_id
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                ps.setInt(1, Integer.parseInt(d[0].trim()));
                ps.setString(2, d[1].trim());
                ps.setString(3, d[3].trim());
                ps.setInt(4, Integer.parseInt(d[4].trim()));
                ps.executeUpdate();
                count++;
            }
            return count;
        }
    }

    private static int loadRequests(Connection conn, String dataFolder) throws Exception {
        String sql = "INSERT INTO Requests (id, type, urgency_level, submitted_time, "
                + "origin_location_id, destination_location_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (BufferedReader br = new BufferedReader(new FileReader(dataFolder + "requests.csv"));
             PreparedStatement ps = conn.prepareStatement(sql)) {
            br.readLine(); // skip header
            int count = 0;
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                ps.setInt(1, Integer.parseInt(d[0].trim()));    // id
                ps.setString(2, d[5].trim());                   // type
                ps.setInt(3, Integer.parseInt(d[6].trim()));    // urgency_level
                ps.setString(4, d[8].trim());                   // submitted_time
                ps.setInt(5, Integer.parseInt(d[3].trim()));    // origin_location_id
                ps.setInt(6, Integer.parseInt(d[4].trim()));    // destination_location_id
                ps.setString(7, d[7].trim());                   // status
                ps.executeUpdate();
                count++;
            }
            return count;
        }
    }

    // ------------------------ database housekeeping ------------------------

    private static void clearData(Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Requests");) {
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Resources");) {
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Roads");) {
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Locations");) {
            ps.executeUpdate();
        }
    }

    public static void logActivity(Connection conn, String action, String user) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Activity_Log (action, user) VALUES (?, ?)")) {
            ps.setString(1, action);
            ps.setString(2, user == null ? "unknown" : user);
            ps.executeUpdate();
        }
    }

    public static void recordTestResult(Connection conn, String module, long runtimeMs, String result)
            throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO Test_Results (module, runtime, result) VALUES (?, ?, ?)")) {
            ps.setString(1, module);
            ps.setDouble(2, runtimeMs / 1000.0);
            ps.setString(3, result);
            ps.executeUpdate();
        }
    }

    private static void printSummary(PrintStream out, String dbPath, int locations, int roads,
                                     int resources, int requests, long elapsed) {
        out.println();
        out.println("Records loaded into " + dbPath);
        out.println("--------------------------------------------");
        out.println("Locations : " + locations);
        out.println("Roads     : " + roads);
        out.println("Resources : " + resources);
        out.println("Requests  : " + requests);
        out.println("--------------------------------------------");
        out.println("Total     : " + (locations + roads + resources + requests));
        out.println("Elapsed   : " + DatabaseManager.formatMillis(elapsed));
        out.println("--------------------------------------------");
    }

    /** Immutable result of a load run. */
    public static class LoadResult {
        public final int locations;
        public final int roads;
        public final int resources;
        public final int requests;
        public final long elapsedMs;

        public LoadResult(int locations, int roads, int resources, int requests, long elapsedMs) {
            this.locations = locations;
            this.roads = roads;
            this.resources = resources;
            this.requests = requests;
            this.elapsedMs = elapsedMs;
        }
    }
}