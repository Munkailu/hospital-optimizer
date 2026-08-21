package tests;

import database.DatabaseManager;
import tools.LocationTool;
import tools.RoadTool;
import tools.RequestTool;
import tools.ResourceTool;

import java.sql.Connection;

public class PipelineTest {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("============================================");
        System.out.println(" Day 3 - Pod 1 Pipeline Test");
        System.out.println("============================================");
        System.out.println();

        Connection conn = DatabaseManager.getConnection();

        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }

        try {

            // Create the four data-access tools
            LocationTool locationTool =
                    new LocationTool(conn);

            RoadTool roadTool =
                    new RoadTool(conn);

            RequestTool requestTool =
                    new RequestTool(conn);

            ResourceTool resourceTool =
                    new ResourceTool(conn);

            // ============================================
            // Count records
            // ============================================

            int locations =
                    locationTool.countLocations();

            int roads =
                    roadTool.countRoads();

            int requests =
                    requestTool.countRequests();

            int resources =
                    resourceTool.countResources();

            // ============================================
            // Display counts
            // ============================================

            System.out.println("Database Records");
            System.out.println("--------------------------------------------");

            System.out.println("Locations : " + locations);
            System.out.println("Roads     : " + roads);
            System.out.println("Resources : " + resources);
            System.out.println("Requests  : " + requests);

            System.out.println("--------------------------------------------");

            int total =
                    locations
                    + roads
                    + resources
                    + requests;

            System.out.println("Total     : " + total);

            // ============================================
            // Basic verification
            // ============================================

            System.out.println();
            System.out.println("Verification");
            System.out.println("--------------------------------------------");

            if (locations == 50) {
                System.out.println("✓ Locations test passed.");
            } else {
                System.out.println(
                        "✗ Locations test failed. Expected 50."
                );
            }

            if (roads == 100) {
                System.out.println("✓ Roads test passed.");
            } else {
                System.out.println(
                        "✗ Roads test failed. Expected 100."
                );
            }

            if (resources == 30) {
                System.out.println("✓ Resources test passed.");
            } else {
                System.out.println(
                        "✗ Resources test failed. Expected 30."
                );
            }

            if (requests == 300) {
                System.out.println("✓ Requests test passed.");
            } else {
                System.out.println(
                        "✗ Requests test failed. Expected 300."
                );
            }

            // ============================================
            // Test individual lookups
            // ============================================

            System.out.println();
            System.out.println("Sample Lookups");
            System.out.println("--------------------------------------------");

            System.out.println(
                    locationTool.getLocationById(1)
            );

            System.out.println(
                    roadTool.getRoadById(1)
            );

            System.out.println(
                    requestTool.getRequestById(1)
            );

            System.out.println(
                    resourceTool.getResourceById(1)
            );

            System.out.println();
            System.out.println("============================================");
            System.out.println(" Pipeline test completed.");
            System.out.println("============================================");

        } catch (Exception e) {

            System.out.println("Pipeline test failed.");
            e.printStackTrace();

        } finally {

            DatabaseManager.closeConnection(conn);

        }
    }
}