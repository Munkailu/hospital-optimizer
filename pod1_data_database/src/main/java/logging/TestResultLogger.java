package logging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestResultLogger {

    private final Connection connection;

    public TestResultLogger(Connection connection) {
        this.connection = connection;
    }

    // =====================================================
    // Record a test result
    // =====================================================

    public void recordTestResult(
            String module,
            double runtime,
            String result) {

        String sql =
                "INSERT INTO Test_Results(module, runtime, result) "
                + "VALUES(?, ?, ?)";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, module);
            ps.setDouble(2, runtime);
            ps.setString(3, result);

            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println("Error recording test result.");

            e.printStackTrace();
        }
    }


    // =====================================================
    // Display all test results
    // =====================================================

    public void displayTestResults() {

        String sql =
                "SELECT id, module, runtime, result "
                + "FROM Test_Results "
                + "ORDER BY id";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println(
                        "TestID: " + rs.getInt("id")
                        + " | Module: "
                        + rs.getString("module")
                        + " | Runtime: "
                        + rs.getDouble("runtime")
                        + " | Result: "
                        + rs.getString("result")
                );
            }

        } catch (Exception e) {

            System.out.println("Error retrieving test results.");

            e.printStackTrace();
        }
    }


    // =====================================================
    // Count test results
    // =====================================================

    public int countTestResults() {

        String sql =
                "SELECT COUNT(*) FROM Test_Results";

        try (PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (Exception e) {

            System.out.println("Error counting test results.");

            e.printStackTrace();
        }

        return 0;
    }
}
