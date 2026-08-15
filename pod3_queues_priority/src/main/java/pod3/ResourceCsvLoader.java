package pod3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Pod 1's real resources.csv into pod3.Resource objects.
 * Column order: id, type, resource_name, availability_status, current_location_id
 * (resource_name is Pod 1's internal extra, not part of the locked Resource schema, so it's dropped here.)
 */
public class ResourceCsvLoader {

    public static final String DEFAULT_PATH = "pod1_data_database/day2/data/resources.csv";

    public static List<Resource> load(String path) throws Exception {
        List<Resource> resources = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] d = line.split(",");

                int id = Integer.parseInt(d[0].trim());
                String type = d[1].trim();
                String availabilityStatus = d[3].trim();
                int currentLocationId = Integer.parseInt(d[4].trim());

                resources.add(new Resource(id, type, availabilityStatus, currentLocationId));
            }
        }

        return resources;
    }

    public static List<Resource> loadDefault() throws Exception {
        return load(DEFAULT_PATH);
    }

    /** Convenience: only resources currently marked "Available". */
    public static List<Resource> loadAvailable(String path) throws Exception {
        List<Resource> all = load(path);
        List<Resource> available = new ArrayList<>();
        for (Resource r : all) {
            if ("Available".equalsIgnoreCase(r.getAvailabilityStatus())) {
                available.add(r);
            }
        }
        return available;
    }
}
