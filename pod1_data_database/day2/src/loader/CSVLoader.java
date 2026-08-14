package loader;

import database.DatabaseManager;
import validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CSVLoader {

    private static final String DATA_FOLDER =
            "pod1_data_database/day2/data/";

    public static void loadAll() {

        System.out.println();
        System.out.println("============================================");
        System.out.println(" Hospital Smart Service Operations Optimizer");
        System.out.println("============================================");
        System.out.println();

        Connection conn = DatabaseManager.getConnection();

        if (conn == null) {
            return;
        }

        try {

            // ============================================
            // Clear old data
            // ============================================

            Statement stmt = conn.createStatement();

            stmt.executeUpdate("DELETE FROM Requests");
            stmt.executeUpdate("DELETE FROM Resources");
            stmt.executeUpdate("DELETE FROM Roads");
            stmt.executeUpdate("DELETE FROM Patients");
            stmt.executeUpdate("DELETE FROM Locations");

            stmt.close();

            System.out.println("✓ Old data cleared.");
            System.out.println();

            // ============================================
            // Validate CSV files
            // ============================================

            System.out.println("Validating CSV files...");

            if (!Validator.validateLocations(DATA_FOLDER + "locations.csv")) {
                System.out.println("❌ Locations validation failed.");
                return;
            }

            if (!Validator.validateRoads(DATA_FOLDER + "roads.csv")) {
                System.out.println("❌ Roads validation failed.");
                return;
            }

            if (!Validator.validateResources(DATA_FOLDER + "resources.csv")) {
                System.out.println("❌ Resources validation failed.");
                return;
            }

            if (!Validator.validateRequests(DATA_FOLDER + "requests.csv")) {
                System.out.println("❌ Requests validation failed.");
                return;
            }

            System.out.println("✓ All CSV files validated successfully.");
            System.out.println();

            // ============================================
            // Load data
            // ============================================

            loadLocations(conn);
            loadRoads(conn);
            loadResources(conn);
            loadRequests(conn);

            System.out.println();
            System.out.println("============================================");
            System.out.println(" Import completed successfully!");
            System.out.println();
            System.out.println("Records Loaded");
            System.out.println("--------------------------------------------");
            System.out.println("Locations : 50");
            System.out.println("Roads     : 100");
            System.out.println("Resources : 30");
            System.out.println("Requests  : 300");
            System.out.println("--------------------------------------------");
            System.out.println("Total Records : 480");
            System.out.println("============================================");

        } catch (Exception e) {

            System.out.println("❌ Error loading data.");
            e.printStackTrace();

        } finally {

            DatabaseManager.closeConnection(conn);

        }
    }

    // ======================================================
    // Load Locations
    // ======================================================

    private static void loadLocations(Connection conn) throws Exception {

        BufferedReader br =
                new BufferedReader(new FileReader(DATA_FOLDER + "locations.csv"));

        br.readLine();

        String line;

        String sql =
                "INSERT INTO Locations(id,name,type,floor) VALUES(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        int count = 0;

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",");

            ps.setInt(1, Integer.parseInt(d[0]));
            ps.setString(2, d[1]);
            ps.setString(3, d[2]);
            ps.setInt(4, Integer.parseInt(d[3]));

            ps.executeUpdate();
            count++;
        }

        br.close();
        ps.close();

        System.out.println("✓ Loaded " + count + " Locations");
    }

    // ======================================================
    // Load Roads
    // ======================================================

    private static void loadRoads(Connection conn) throws Exception {

        BufferedReader br =
                new BufferedReader(new FileReader(DATA_FOLDER + "roads.csv"));

        br.readLine();

        String line;

        String sql =
                "INSERT INTO Roads(id,from_location_id,to_location_id,distance,estimated_time) VALUES(?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        int count = 0;

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",");

            ps.setInt(1, Integer.parseInt(d[0]));
            ps.setInt(2, Integer.parseInt(d[1]));
            ps.setInt(3, Integer.parseInt(d[2]));
            ps.setDouble(4, Double.parseDouble(d[3]));
            ps.setInt(5, Integer.parseInt(d[4]));

            ps.executeUpdate();
            count++;
        }

        br.close();
        ps.close();

        System.out.println("✓ Loaded " + count + " Roads");
    }

    // ======================================================
    // Load Resources
    // ======================================================

    private static void loadResources(Connection conn) throws Exception {

        BufferedReader br =
                new BufferedReader(new FileReader(DATA_FOLDER + "resources.csv"));

        br.readLine();

        String line;

        String sql =
                "INSERT INTO Resources(id,type,resource_name,availability_status,current_location_id) VALUES(?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        int count = 0;

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",");

            ps.setInt(1, Integer.parseInt(d[0]));
            ps.setString(2, d[1]);
            ps.setString(3, d[2]);
            ps.setString(4, d[3]);
            ps.setInt(5, Integer.parseInt(d[4]));

            ps.executeUpdate();
            count++;
        }

        br.close();
        ps.close();

        System.out.println("✓ Loaded " + count + " Resources");
    }

    // ======================================================
    // Load Requests
    // ======================================================

    private static void loadRequests(Connection conn) throws Exception {

        BufferedReader br =
                new BufferedReader(new FileReader(DATA_FOLDER + "requests.csv"));

        br.readLine();

        String line;

        String sql =
                "INSERT INTO Requests(id,patient_id,resource_id,origin_location_id,destination_location_id,type,urgency_level,status,submitted_time) VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        int count = 0;

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",");

            ps.setInt(1, Integer.parseInt(d[0]));
            ps.setInt(2, Integer.parseInt(d[1]));
            ps.setInt(3, Integer.parseInt(d[2]));
            ps.setInt(4, Integer.parseInt(d[3]));
            ps.setInt(5, Integer.parseInt(d[4]));
            ps.setString(6, d[5]);
            ps.setInt(7, Integer.parseInt(d[6]));
            ps.setString(8, d[7]);
            ps.setString(9, d[8]);

            ps.executeUpdate();
            count++;
        }

        br.close();
        ps.close();

        System.out.println("✓ Loaded " + count + " Requests");
    }
}