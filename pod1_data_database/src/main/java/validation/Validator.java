package validation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * File-based validation for Pod 1's CSV dataset. Checks normal, edge and
 * bad-input cases and collects human-readable problem descriptions.
 *
 * The CSV files keep Pod 1's internal extra columns (floor, estimated_time,
 * resource_name, patient_id, resource_id), so a few checks below apply to
 * columns that are NOT part of the locked docs/interfaces.md schema. Those
 * extra columns are intentionally not loaded into the database.
 * Owned by Pod 1.
 */
public class Validator {

    // -------------------------
    // Public API
    // -------------------------

    public static boolean validateLocations(String filePath) {
        return validateLocations(filePath, null);
    }

    public static boolean validateRoads(String filePath) {
        return validateRoads(filePath, null);
    }

    public static boolean validateResources(String filePath) {
        return validateResources(filePath, null);
    }

    public static boolean validateRequests(String filePath) {
        return validateRequests(filePath, null);
    }

    /** Validates the four CSVs in one pass and reports a single verdict. */
    public static boolean validateAll(String dataFolder, List<String> problems) {
        boolean locations = validateLocations(dataFolder + "locations.csv", problems);
        boolean roads = validateRoads(dataFolder + "roads.csv", problems);
        boolean resources = validateResources(dataFolder + "resources.csv", problems);
        boolean requests = validateRequests(dataFolder + "requests.csv", problems);
        boolean references = validateReferences(dataFolder, problems);

        String[] names = {"Locations", "Roads", "Resources", "Requests", "References"};
        boolean[] results = {locations, roads, resources, requests, references};

        boolean allOk = true;
        for (int i = 0; i < results.length; i++) {
            if (!results[i]) {
                allOk = false;
                if (problems != null) {
                    problems.add(names[i] + " validation failed");
                }
            }
        }
        return allOk;
    }

    // -------------------------
    // Locations (id,name,type,floor)
    // -------------------------

    public static boolean validateLocations(String filePath, List<String> problems) {
        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");
                if (d.length < 4) {
                    report(problems, "Locations line " + lineNo + ": expected 4 columns, got " + d.length);
                    return false;
                }

                int id = parseInt(d[0].trim(), "Locations line " + lineNo + ": bad id", problems);
                if (id <= 0) {
                    report(problems, "Locations line " + lineNo + ": id must be positive");
                    return false;
                }
                if (!ids.add(id)) {
                    report(problems, "Duplicate Location id: " + id);
                    return false;
                }
                if (d[1].trim().isEmpty()) {
                    report(problems, "Locations line " + lineNo + ": missing name");
                    return false;
                }
                if (d[2].trim().isEmpty()) {
                    report(problems, "Locations line " + lineNo + ": missing type");
                    return false;
                }
                int floor = parseInt(d[3].trim(), "Locations line " + lineNo + ": bad floor", problems);
                if (floor < 0) {
                    report(problems, "Locations line " + lineNo + ": floor must be >= 0");
                    return false;
                }
            }

        } catch (Exception e) {
            report(problems, "Locations file error: " + e.getMessage());
            return false;
        }

        return true;
    }

    // -------------------------
    // Roads (id,from_location_id,to_location_id,distance,estimated_time)
    // -------------------------

    public static boolean validateRoads(String filePath, List<String> problems) {
        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");
                if (d.length < 5) {
                    report(problems, "Roads line " + lineNo + ": expected 5 columns, got " + d.length);
                    return false;
                }

                int id = parseInt(d[0].trim(), "Roads line " + lineNo + ": bad id", problems);
                int from = parseInt(d[1].trim(), "Roads line " + lineNo + ": bad from_location_id", problems);
                int to = parseInt(d[2].trim(), "Roads line " + lineNo + ": bad to_location_id", problems);
                double distance = parseDouble(d[3].trim(), "Roads line " + lineNo + ": bad distance", problems);

                if (id <= 0) {
                    report(problems, "Roads line " + lineNo + ": id must be positive");
                    return false;
                }
                if (!ids.add(id)) {
                    report(problems, "Duplicate Road id: " + id);
                    return false;
                }
                if (from <= 0 || to <= 0) {
                    report(problems, "Roads line " + lineNo + ": location ids must be positive");
                    return false;
                }
                if (from == to) {
                    report(problems, "Roads line " + lineNo + ": road cannot start and end at the same location");
                    return false;
                }
                if (distance <= 0) {
                    report(problems, "Roads line " + lineNo + ": distance must be positive");
                    return false;
                }
            }

        } catch (Exception e) {
            report(problems, "Roads file error: " + e.getMessage());
            return false;
        }

        return true;
    }

    // -------------------------
    // Resources (id,type,resource_name,availability_status,current_location_id)
    // -------------------------

    public static boolean validateResources(String filePath, List<String> problems) {
        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");
                if (d.length < 5) {
                    report(problems, "Resources line " + lineNo + ": expected 5 columns, got " + d.length);
                    return false;
                }

                int id = parseInt(d[0].trim(), "Resources line " + lineNo + ": bad id", problems);
                String status = d[3].trim();
                int currentLocationId = parseInt(d[4].trim(),
                        "Resources line " + lineNo + ": bad current_location_id", problems);

                if (id <= 0) {
                    report(problems, "Resources line " + lineNo + ": id must be positive");
                    return false;
                }
                if (!ids.add(id)) {
                    report(problems, "Duplicate Resource id: " + id);
                    return false;
                }
                if (d[1].trim().isEmpty()) {
                    report(problems, "Resources line " + lineNo + ": missing type");
                    return false;
                }
                if (!(status.equalsIgnoreCase("Available") || status.equalsIgnoreCase("Busy"))) {
                    report(problems, "Resources line " + lineNo
                            + ": invalid availability_status '" + status + "' (expected Available or Busy)");
                    return false;
                }
                if (currentLocationId <= 0) {
                    report(problems, "Resources line " + lineNo + ": current_location_id must be positive");
                    return false;
                }
            }

        } catch (Exception e) {
            report(problems, "Resources file error: " + e.getMessage());
            return false;
        }

        return true;
    }

    // -------------------------
    // Requests (id,patient_id,resource_id,origin_location_id,
    //           destination_location_id,type,urgency_level,status,submitted_time)
    // -------------------------

    public static boolean validateRequests(String filePath, List<String> problems) {
        Set<Integer> ids = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // skip header

            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");
                if (d.length < 9) {
                    report(problems, "Requests line " + lineNo + ": expected 9 columns, got " + d.length);
                    return false;
                }

                int id = parseInt(d[0].trim(), "Requests line " + lineNo + ": bad id", problems);
                int origin = parseInt(d[3].trim(), "Requests line " + lineNo + ": bad origin", problems);
                int destination = parseInt(d[4].trim(), "Requests line " + lineNo + ": bad destination", problems);
                int urgency = parseInt(d[6].trim(), "Requests line " + lineNo + ": bad urgency", problems);

                if (id <= 0) {
                    report(problems, "Requests line " + lineNo + ": id must be positive");
                    return false;
                }
                if (!ids.add(id)) {
                    report(problems, "Duplicate Request id: " + id);
                    return false;
                }
                if (origin <= 0 || destination <= 0) {
                    report(problems, "Requests line " + lineNo + ": location ids must be positive");
                    return false;
                }
                if (origin == destination) {
                    report(problems, "Requests line " + lineNo
                            + ": origin and destination cannot be the same location");
                    return false;
                }
                if (d[5].trim().isEmpty()) {
                    report(problems, "Requests line " + lineNo + ": missing type");
                    return false;
                }
                if (urgency < 1 || urgency > 5) {
                    report(problems, "Requests line " + lineNo + ": urgency must be between 1 and 5");
                    return false;
                }
                if (d[7].trim().isEmpty()) {
                    report(problems, "Requests line " + lineNo + ": missing status");
                    return false;
                }
                if (d[8].trim().isEmpty()) {
                    report(problems, "Requests line " + lineNo + ": missing submitted_time");
                    return false;
                }
            }

        } catch (Exception e) {
            report(problems, "Requests file error: " + e.getMessage());
            return false;
        }

        return true;
    }

    // -------------------------
    // Referential integrity across the four files
    // -------------------------

    /**
     * Ensures every road endpoint, resource location and request origin /
     * destination references a real Location id from locations.csv.
     */
    public static boolean validateReferences(String dataFolder, List<String> problems) {
        Set<Integer> locationIds = readLocations(dataFolder + "locations.csv", problems);
        if (locationIds == null) {
            return false;
        }

        if (!checkRoadsReferenceLocations(dataFolder + "roads.csv", locationIds, problems)) {
            return false;
        }
        if (!checkResourcesReferenceLocations(dataFolder + "resources.csv", locationIds, problems)) {
            return false;
        }
        return checkRequestsReferenceLocations(dataFolder + "requests.csv", locationIds, problems);
    }

    private static Set<Integer> readLocations(String filePath, List<String> problems) {
        Set<Integer> ids = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                ids.add(Integer.parseInt(line.split(",")[0].trim()));
            }
        } catch (IOException | NumberFormatException e) {
            report(problems, "Cannot read locations for reference check: " + e.getMessage());
            return null;
        }
        return ids;
    }

    private static boolean checkRoadsReferenceLocations(String filePath, Set<Integer> ids,
                                                        List<String> problems) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                int from = Integer.parseInt(d[1].trim());
                int to = Integer.parseInt(d[2].trim());
                if (!ids.contains(from)) {
                    report(problems, "Roads line " + lineNo + ": from_location_id " + from + " is not a Location id");
                    return false;
                }
                if (!ids.contains(to)) {
                    report(problems, "Roads line " + lineNo + ": to_location_id " + to + " is not a Location id");
                    return false;
                }
            }
        } catch (IOException | NumberFormatException e) {
            report(problems, "Roads reference check failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean checkResourcesReferenceLocations(String filePath, Set<Integer> ids,
                                                            List<String> problems) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                int locationId = Integer.parseInt(d[4].trim());
                if (!ids.contains(locationId)) {
                    report(problems, "Resources line " + lineNo + ": current_location_id "
                            + locationId + " is not a Location id");
                    return false;
                }
            }
        } catch (IOException | NumberFormatException e) {
            report(problems, "Resources reference check failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean checkRequestsReferenceLocations(String filePath, Set<Integer> ids,
                                                           List<String> problems) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                int origin = Integer.parseInt(d[3].trim());
                int destination = Integer.parseInt(d[4].trim());
                if (!ids.contains(origin)) {
                    report(problems, "Requests line " + lineNo + ": origin_location_id "
                            + origin + " is not a Location id");
                    return false;
                }
                if (!ids.contains(destination)) {
                    report(problems, "Requests line " + lineNo + ": destination_location_id "
                            + destination + " is not a Location id");
                    return false;
                }
            }
        } catch (IOException | NumberFormatException e) {
            report(problems, "Requests reference check failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    // -------------------------
    // Helpers
    // -------------------------

    private static int parseInt(String value, String message, List<String> problems) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            report(problems, message);
            return -1;
        }
    }

    private static double parseDouble(String value, String message, List<String> problems) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            report(problems, message);
            return -1;
        }
    }

    private static void report(List<String> problems, String message) {
        if (problems != null) {
            problems.add(message);
        }
    }
}