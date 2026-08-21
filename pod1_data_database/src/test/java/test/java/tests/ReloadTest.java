package tests;

import database.DatabaseManager;
import tools.LocationTool;
import tools.RoadTool;
import tools.RequestTool;
import tools.ResourceTool;

import java.sql.Connection;

public class ReloadTest {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("============================================");
        System.out.println(" Day 3 - Database Save & Reload Test");
        System.out.println("============================================");
        System.out.println();

        System.out.println("Opening existing SQLite database...");
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


            // Read existing records from SQLite

            int locations =
                    locationTool.countLocations();

            int roads =
                    roadTool.countRoads();

            int resources =
                    resourceTool.countResources();

            int requests =
                    requestTool.countRequests();


            // Display results

            System.out.println("Existing Database Records");
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


            // Verify persistence

            System.out.println();
            System.out.println("Persistence Verification");
            System.out.println("--------------------------------------------");

            boolean passed =
                    locations == 50
                    && roads == 100
                    && resources == 30
                    && requests == 300;

            if (passed) {

                System.out.println(
                        "✓ Save-and-reload test PASSED."
                );

                System.out.println(
                        "✓ All 480 records were successfully "
                        + "retrieved from SQLite."
                );

            } else {

                System.out.println(
                        "✗ Save-and-reload test FAILED."
                );

                System.out.println(
                        "Expected 480 records."
                );
            }


            System.out.println();
            System.out.println("============================================");
            System.out.println(" Reload test completed.");
            System.out.println("============================================");

        } catch (Exception e) {

            System.out.println(
                    "❌ Error during reload test."
            );

            e.printStackTrace();

        } finally {

            DatabaseManager.closeConnection(conn);

        }
    }
}
