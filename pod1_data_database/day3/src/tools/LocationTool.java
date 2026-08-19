package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationTool {

    private final Connection connection;

    public LocationTool(Connection connection) {
        this.connection = connection;
    }

    // Find one location by ID
    public String getLocationById(int id) {

        String sql =
                "SELECT id, name, type FROM Locations WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return "Location{id=" + rs.getInt("id")
                        + ", name='" + rs.getString("name")
                        + "', type='" + rs.getString("type")
                        + "'}";

            }

        } catch (Exception e) {

            System.out.println("Error finding location.");
            e.printStackTrace();

        }

        return null;
    }


    // Get all locations
    public List<String> getAllLocations() {

        List<String> locations = new ArrayList<>();

        String sql =
                "SELECT id, name, type FROM Locations ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String location =
                        "Location{id=" + rs.getInt("id")
                        + ", name='" + rs.getString("name")
                        + "', type='" + rs.getString("type")
                        + "'}";

                locations.add(location);
            }

        } catch (Exception e) {

            System.out.println("Error retrieving locations.");
            e.printStackTrace();

        }

        return locations;
    }


    // Count locations
    public int countLocations() {

        String sql = "SELECT COUNT(*) FROM Locations";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error counting locations.");
            e.printStackTrace();

        }

        return 0;
    }
}