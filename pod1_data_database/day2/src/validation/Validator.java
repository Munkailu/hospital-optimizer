package validation;

import java.io.*;
import java.util.*;

public class Validator {

    // -------------------------
    // Validate Locations
    // Schema:
    // id, name, type
    // -------------------------
    public static boolean validateLocations(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            br.readLine(); // Skip header

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                // Expected: id, name, type
                if (data.length != 3) {
                    System.out.println(
                            "Invalid location record: expected 3 fields."
                    );
                    return false;
                }

                int id = Integer.parseInt(data[0]);
                String name = data[1].trim();
                String type = data[2].trim();

                if (!ids.add(id)) {
                    System.out.println(
                            "Duplicate Location ID: " + id
                    );
                    return false;
                }

                if (name.isBlank()) {
                    System.out.println(
                            "Empty location name."
                    );
                    return false;
                }

                if (type.isBlank()) {
                    System.out.println(
                            "Empty location type."
                    );
                    return false;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Location validation error."
            );

            e.printStackTrace();

            return false;
        }

        return true;
    }


    // -------------------------
    // Validate Roads
    // Schema:
    // id, from_location_id,
    // to_location_id, distance
    // -------------------------
    public static boolean validateRoads(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            br.readLine(); // Skip header

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                // Expected: 4 fields
                if (data.length != 4) {
                    System.out.println(
                            "Invalid road record: expected 4 fields."
                    );
                    return false;
                }

                int id = Integer.parseInt(data[0]);
                int fromLocation = Integer.parseInt(data[1]);
                int toLocation = Integer.parseInt(data[2]);
                double distance = Double.parseDouble(data[3]);

                if (!ids.add(id)) {

                    System.out.println(
                            "Duplicate Road ID: " + id
                    );

                    return false;
                }

                if (fromLocation <= 0 || toLocation <= 0) {

                    System.out.println(
                            "Invalid road location ID."
                    );

                    return false;
                }

                if (distance <= 0) {

                    System.out.println(
                            "Invalid distance."
                    );

                    return false;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Road validation error."
            );

            e.printStackTrace();

            return false;
        }

        return true;
    }


    // -------------------------
    // Validate Resources
    // Schema:
    // id, type,
    // availability_status,
    // current_location_id
    // -------------------------
    public static boolean validateResources(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            br.readLine(); // Skip header

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                // Expected: 4 fields
                if (data.length != 4) {
                    System.out.println(
                            "Invalid resource record: expected 4 fields."
                    );
                    return false;
                }

                int id = Integer.parseInt(data[0]);
                String type = data[1].trim();
                String status = data[2].trim();
                int locationId = Integer.parseInt(data[3]);

                if (!ids.add(id)) {

                    System.out.println(
                            "Duplicate Resource ID: " + id
                    );

                    return false;
                }

                if (type.isBlank()) {

                    System.out.println(
                            "Resource type missing."
                    );

                    return false;
                }

                if (!(status.equalsIgnoreCase("Available")
                        || status.equalsIgnoreCase("Busy"))) {

                    System.out.println(
                            "Invalid availability status."
                    );

                    return false;
                }

                if (locationId <= 0) {

                    System.out.println(
                            "Invalid current location ID."
                    );

                    return false;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Resource validation error."
            );

            e.printStackTrace();

            return false;
        }

        return true;
    }


    // -------------------------
    // Validate Requests
    // Schema:
    // id, type, urgency_level,
    // submitted_time,
    // origin_location_id,
    // destination_location_id,
    // status
    // -------------------------
    public static boolean validateRequests(String filePath) {

        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br =
                     new BufferedReader(new FileReader(filePath))) {

            br.readLine(); // Skip header

            String line;

            while ((line = br.readLine()) != null) {

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(",");

                // Expected: 7 fields
                if (data.length != 7) {
                    System.out.println(
                            "Invalid request record: expected 7 fields."
                    );
                    return false;
                }

                int id = Integer.parseInt(data[0]);
                String type = data[1].trim();
                int urgency = Integer.parseInt(data[2]);
                String submittedTime = data[3].trim();
                int originLocation = Integer.parseInt(data[4]);
                int destinationLocation = Integer.parseInt(data[5]);
                String status = data[6].trim();

                if (!ids.add(id)) {

                    System.out.println(
                            "Duplicate Request ID: " + id
                    );

                    return false;
                }

                if (type.isBlank()) {

                    System.out.println(
                            "Request type missing."
                    );

                    return false;
                }

                if (urgency < 1 || urgency > 5) {

                    System.out.println(
                            "Urgency must be between 1 and 5."
                    );

                    return false;
                }

                if (submittedTime.isBlank()) {

                    System.out.println(
                            "Submitted time missing."
                    );

                    return false;
                }

                if (originLocation <= 0
                        || destinationLocation <= 0) {

                    System.out.println(
                            "Invalid request location ID."
                    );

                    return false;
                }

                if (status.isBlank()) {

                    System.out.println(
                            "Request status missing."
                    );

                    return false;
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Request validation error."
            );

            e.printStackTrace();

            return false;
        }

        return true;
    }
}