package validation;

import java.io.*;
import java.util.*;

public class Validator {

    // -------------------------
    // Validate Locations
    // -------------------------
    public static boolean validateLocations(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int floor = Integer.parseInt(data[3]);

                if (!ids.add(id)) {
                    System.out.println("Duplicate Location ID: " + id);
                    return false;
                }

                if (name.isBlank()) {
                    System.out.println("Empty location name.");
                    return false;
                }

                if (floor < 0) {
                    System.out.println("Invalid floor number.");
                    return false;
                }

            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

        return true;
    }

    // -------------------------
    // Validate Roads
    // -------------------------
    public static boolean validateRoads(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                double distance = Double.parseDouble(data[3]);
                int time = Integer.parseInt(data[4]);

                if (!ids.add(id)) {

                    System.out.println("Duplicate Road ID: " + id);

                    return false;

                }

                if (distance <= 0) {

                    System.out.println("Invalid distance.");

                    return false;

                }

                if (time <= 0) {

                    System.out.println("Invalid travel time.");

                    return false;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

        return true;

    }

    // -------------------------
    // Validate Resources
    // -------------------------
    public static boolean validateResources(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);

                String name = data[2];

                String status = data[3];

                if (!ids.add(id)) {

                    System.out.println("Duplicate Resource ID.");

                    return false;

                }

                if (name.isBlank()) {

                    System.out.println("Resource name missing.");

                    return false;

                }

                if (!(status.equalsIgnoreCase("Available")
                        || status.equalsIgnoreCase("Busy"))) {

                    System.out.println("Invalid availability status.");

                    return false;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

        return true;

    }

    // -------------------------
    // Validate Requests
    // -------------------------
    public static boolean validateRequests(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line = br.readLine();

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);

                int urgency = Integer.parseInt(data[6]);

                if (!ids.add(id)) {

                    System.out.println("Duplicate Request ID.");

                    return false;

                }

                if (urgency < 1 || urgency > 5) {

                    System.out.println("Urgency must be between 1 and 5.");

                    return false;

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

        return true;

    }

}
