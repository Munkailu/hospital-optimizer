package tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RequestTool {

    private final Connection connection;

    public RequestTool(Connection connection) {
        this.connection = connection;
    }


    // Find one request by ID
    public String getRequestById(int id) {

        String sql =
                "SELECT id, type, urgency_level, submitted_time, "
                + "origin_location_id, destination_location_id, status "
                + "FROM Requests WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return "Request{id=" + rs.getInt("id")
                        + ", type='" + rs.getString("type")
                        + "', urgency=" + rs.getInt("urgency_level")
                        + ", submitted='" + rs.getString("submitted_time")
                        + "', origin=" + rs.getInt("origin_location_id")
                        + ", destination="
                        + rs.getInt("destination_location_id")
                        + ", status='" + rs.getString("status")
                        + "'}";

            }

        } catch (Exception e) {

            System.out.println("Error finding request.");
            e.printStackTrace();

        }

        return null;
    }


    // Get all requests
    public List<String> getAllRequests() {

        List<String> requests = new ArrayList<>();

        String sql =
                "SELECT id, type, urgency_level, submitted_time, "
                + "origin_location_id, destination_location_id, status "
                + "FROM Requests ORDER BY id";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String request =
                        "Request{id=" + rs.getInt("id")
                        + ", type='" + rs.getString("type")
                        + "', urgency=" + rs.getInt("urgency_level")
                        + ", submitted='" + rs.getString("submitted_time")
                        + "', origin="
                        + rs.getInt("origin_location_id")
                        + ", destination="
                        + rs.getInt("destination_location_id")
                        + ", status='" + rs.getString("status")
                        + "'}";

                requests.add(request);
            }

        } catch (Exception e) {

            System.out.println("Error retrieving requests.");
            e.printStackTrace();

        }

        return requests;
    }


    // Count requests
    public int countRequests() {

        String sql = "SELECT COUNT(*) FROM Requests";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error counting requests.");
            e.printStackTrace();

        }

        return 0;
    }
}
