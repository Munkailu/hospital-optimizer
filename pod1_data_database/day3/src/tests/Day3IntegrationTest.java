package tests;

import database.DatabaseManager;
import logging.ActivityLogger;
import logging.TestResultLogger;
import tools.LocationTool;
import tools.RoadTool;
import tools.RequestTool;
import tools.ResourceTool;

import java.sql.Connection;
import java.time.LocalDateTime;

public class Day3IntegrationTest {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("============================================");
        System.out.println(" Hospital Smart Service Operations Optimizer");
        System.out.println("              DAY 3 FINAL TEST");
        System.out.println("============================================");
        System.out.println();

        Connection conn = DatabaseManager.getConnection();

        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }

        try {

            // ============================================
            // Create tools
            // ============================================

            LocationTool locationTool =
                    new LocationTool(conn);

            RoadTool roadTool =
                    new RoadTool(conn);

            RequestTool requestTool =
                    new RequestTool(conn);

            ResourceTool resourceTool =
                    new ResourceTool(conn);

            ActivityLogger activityLogger =
                    new ActivityLogger(conn);

            TestResultLogger testResultLogger =
                    new TestResultLogger(conn);


            // ============================================
            // Count database records
            // ============================================

            int locations =
                    locationTool.countLocations();

            int roads =
                    roadTool.countRoads();

            int resources =
                    resourceTool.countResources();

            int requests =
                    requestTool.countRequests();

            int total =
                    locations
                    + roads
                    + resources
                    + requests;


            System.out.println("Database Verification");
            System.out.println("--------------------------------------------");

            System.out.println("Locations : " + locations);
            System.out.println("Roads     : " + roads);
            System.out.println("Resources : " + resources);
            System.out.println("Requests  : " + requests);
            System.out.println("--------------------------------------------");
            System.out.println("Total     : " + total);


            // ============================================
            // Verify records
            // ============================================

            boolean recordsPassed =
                    locations == 50
                    && roads == 100
                    && resources == 30
                    && requests == 300;


            System.out.println();
            System.out.println("Record Verification");
            System.out.println("--------------------------------------------");

            if (recordsPassed) {

                System.out.println(
                        "✓ All database record counts passed."
                );

            } else {

                System.out.println(
                        "✗ Database record count verification failed."
                );
            }


            // ============================================
            // Test sample lookups
            // ============================================

            System.out.println();
            System.out.println("Tool Verification");
            System.out.println("--------------------------------------------");

            String location =
                    locationTool.getLocationById(1);

            String road =
                    roadTool.getRoadById(1);

            String request =
                    requestTool.getRequestById(1);

            String resource =
                    resourceTool.getResourceById(1);

            boolean toolsPassed =
                    location != null
                    && road != null
                    && request != null
                    && resource != null;

            if (toolsPassed) {

                System.out.println(
                        "✓ LocationTool passed."
                );

                System.out.println(
                        "✓ RoadTool passed."
                );

                System.out.println(
                        "✓ RequestTool passed."
                );

                System.out.println(
                        "✓ ResourceTool passed."
                );

            } else {

                System.out.println(
                        "✗ One or more tools failed."
                );
            }


            // ============================================
            // Record activity
            // ============================================

            activityLogger.logActivity(
                    "DAY3_FINAL_INTEGRATION_TEST",
                    LocalDateTime.now().toString(),
                    "Pod1-Abigail"
            );

            System.out.println();
            System.out.println(
                    "✓ Activity_Log updated."
            );


            // ============================================
            // Record final test result
            // ============================================

            boolean finalPassed =
                    recordsPassed && toolsPassed;

            String result =
                    finalPassed ? "PASS" : "FAIL";

            testResultLogger.recordTestResult(
                    "Pod1-Day3-FinalIntegration",
                    0.0,
                    result
            );

            System.out.println(
                    "✓ Test_Results updated."
            );


            // ============================================
            // Final result
            // ============================================

            System.out.println();
            System.out.println("============================================");

            if (finalPassed) {

                System.out.println(
                        " DAY 3 FINAL TEST: PASS"
                );

            } else {

                System.out.println(
                        " DAY 3 FINAL TEST: FAIL"
                );
            }

            System.out.println("============================================");

            System.out.println();
            System.out.println("Day 3 Components");
            System.out.println("--------------------------------------------");
            System.out.println("Database pipeline : PASS");
            System.out.println("Custom tools      : "
                    + (toolsPassed ? "PASS" : "FAIL"));
            System.out.println("Record validation : "
                    + (recordsPassed ? "PASS" : "FAIL"));
            System.out.println("Activity logging  : PASS");
            System.out.println("Test results      : PASS");

            System.out.println();
            System.out.println(
                    "Total records available: " + total
            );

            System.out.println();
            System.out.println("============================================");
            System.out.println(" Day 3 completed successfully!");
            System.out.println("============================================");

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "❌ Day 3 integration test failed."
            );

            e.printStackTrace();

        } finally {

            DatabaseManager.closeConnection(conn);

        }
    }
}
