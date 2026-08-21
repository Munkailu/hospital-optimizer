package logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ActivityLogger {

    private final Connection connection;

    public ActivityLogger(Connection connection) {
        this.connection = connection;
    }

    // =====================================================
    // Record an activity
    // =====================================================

    public void logActivity(String action, String timestamp, String user) {

        String sql =
                "INSERT INTO Activity_Log(action, timestamp, user) "
                + "VALUES(?, ?, ?)";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, action);
            ps.setString(2, timestamp);
            ps.setString(3, user);

            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println("Error recording activity.");

            e.printStackTrace();
        }
    }


    // =====================================================
    // Display all activity records
    // =====================================================

    public void displayActivities() {

        String sql =
                "SELECT id, action, timestamp, user "
                + "FROM Activity_Log "
                + "ORDER BY id";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "LogID: " + rs.getInt("id")
                        + " | Action: " + rs.getString("action")
                        + " | Timestamp: "
                        + rs.getString("timestamp")
                        + " | User: "
                        + rs.getString("user")
                );
            }

        } catch (Exception e) {

            System.out.println("Error retrieving activity logs.");

            e.printStackTrace();
        }
    }


    // =====================================================
    // Count activity records
    // =====================================================

    public int countActivities() {

        String sql =
                "SELECT COUNT(*) FROM Activity_Log";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error counting activity logs.");

            e.printStackTrace();
        }

        return 0;
    }
}
