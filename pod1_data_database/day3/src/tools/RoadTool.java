package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoadTool {

    private final Connection connection;

    public RoadTool(Connection connection) {
        this.connection = connection;
    }

    // Find one road by ID
    public String getRoadById(int id) {

        String sql =
                "SELECT id, from_location_id, to_location_id, distance "
                + "FROM Roads WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return "Road{id=" + rs.getInt("id")
                        + ", from=" + rs.getInt("from_location_id")
                        + ", to=" + rs.getInt("to_location_id")
                        + ", distance=" + rs.getDouble("distance")
                        + "}";
            }

        } catch (Exception e) {

            System.out.println("Error finding road.");
            e.printStackTrace();
        }

        return null;
    }

    // Get all roads
    public List<String> getAllRoads() {

        List<String> roads = new ArrayList<>();

        String sql =
                "SELECT id, from_location_id, to_location_id, distance "
                + "FROM Roads ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String road =
                        "Road{id=" + rs.getInt("id")
                        + ", from=" + rs.getInt("from_location_id")
                        + ", to=" + rs.getInt("to_location_id")
                        + ", distance=" + rs.getDouble("distance")
                        + "}";

                roads.add(road);
            }

        } catch (Exception e) {

            System.out.println("Error retrieving roads.");
            e.printStackTrace();
        }

        return roads;
    }

    // Count roads
    public int countRoads() {

        String sql = "SELECT COUNT(*) FROM Roads";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error counting roads.");
            e.printStackTrace();
        }

        return 0;
    }
}
