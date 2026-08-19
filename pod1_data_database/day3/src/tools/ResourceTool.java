package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResourceTool {

    private final Connection connection;

    public ResourceTool(Connection connection) {
        this.connection = connection;
    }


    // Find one resource by ID
    public String getResourceById(int id) {

        String sql =
                "SELECT id, type, availability_status, current_location_id "
                + "FROM Resources WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return "Resource{id=" + rs.getInt("id")
                        + ", type='" + rs.getString("type")
                        + "', status='"
                        + rs.getString("availability_status")
                        + "', location="
                        + rs.getInt("current_location_id")
                        + "}";

            }

        } catch (Exception e) {

            System.out.println("Error finding resource.");
            e.printStackTrace();

        }

        return null;
    }


    // Get all resources
    public List<String> getAllResources() {

        List<String> resources = new ArrayList<>();

        String sql =
                "SELECT id, type, availability_status, current_location_id "
                + "FROM Resources ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String resource =
                        "Resource{id=" + rs.getInt("id")
                        + ", type='" + rs.getString("type")
                        + "', status='"
                        + rs.getString("availability_status")
                        + "', location="
                        + rs.getInt("current_location_id")
                        + "}";

                resources.add(resource);
            }

        } catch (Exception e) {

            System.out.println("Error retrieving resources.");
            e.printStackTrace();

        }

        return resources;
    }


    // Count resources
    public int countResources() {

        String sql = "SELECT COUNT(*) FROM Resources";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error counting resources.");
            e.printStackTrace();

        }

        return 0;
    }
}