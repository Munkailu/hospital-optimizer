package tests;

import database.DatabaseManager;
import logging.ActivityLogger;
import logging.TestResultLogger;

import java.sql.Connection;
import java.time.LocalDateTime;

public class LoggingTest {

    public static void main(String[] args) {

        System.out.println();
        System.out.println("============================================");
        System.out.println(" Day 3 - Activity & Test Results Logging");
        System.out.println("============================================");
        System.out.println();

        Connection conn = DatabaseManager.getConnection();

        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }

        try {

            ActivityLogger activityLogger =
                    new ActivityLogger(conn);

            TestResultLogger testResultLogger =
                    new TestResultLogger(conn);

            String timestamp =
                    LocalDateTime.now().toString();

            // ============================================
            // Record an activity
            // ============================================

            activityLogger.logActivity(
                    "DAY3_PIPELINE_TEST",
                    timestamp,
                    "Pod1-Abigail"
            );

            System.out.println(
                    "✓ Activity log recorded."
            );

            // ============================================
            // Measure a database operation
            // ============================================

            long startTime =
                    System.nanoTime();

            activityLogger.countActivities();

            long endTime =
                    System.nanoTime();

            double runtime =
                    (endTime - startTime) / 1_000_000.0;

            // ============================================
            // Record test result
            // ============================================

            testResultLogger.recordTestResult(
                    "Pod1-Day3-ActivityLog",
                    runtime,
                    "PASS"
            );

            System.out.println(
                    "✓ Test result recorded."
            );

            // ============================================
            // Display Activity_Log
            // ============================================

            System.out.println();
            System.out.println("Activity_Log");
            System.out.println("--------------------------------------------");

            activityLogger.displayActivities();

            // ============================================
            // Display Test_Results
            // ============================================

            System.out.println();
            System.out.println("Test_Results");
            System.out.println("--------------------------------------------");

            testResultLogger.displayTestResults();

            // ============================================
            // Summary
            // ============================================

            System.out.println();
            System.out.println("Logging Summary");
            System.out.println("--------------------------------------------");

            System.out.println(
                    "Activity records : "
                    + activityLogger.countActivities()
            );

            System.out.println(
                    "Test records     : "
                    + testResultLogger.countTestResults()
            );

            System.out.println(
                    "Database operation runtime : "
                    + runtime
                    + " ms"
            );

            System.out.println();
            System.out.println("============================================");
            System.out.println(" Logging test completed successfully!");
            System.out.println("============================================");

        } catch (Exception e) {

            System.out.println(
                    "❌ Logging test failed."
            );

            e.printStackTrace();

        } finally {

            DatabaseManager.closeConnection(conn);

        }
    }
}