package pod3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads Pod 1's real requests.csv into pod3.Request objects.
 *
 * Column order (locked schema, from Pod 1's docs/interfaces.md):
 *   id, patient_id, resource_id, origin_location_id, destination_location_id,
 *   type, urgency_level, status, submitted_time
 *
 * Only the columns in the locked Request schema are kept (id, type,
 * urgency_level, submitted_time, origin_location_id, destination_location_id,
 * status) — patient_id/resource_id are Pod 1's internal extras and aren't
 * part of what Pod 3 needs.
 *
 * NOTE: default path assumes Pod 1's current folder convention
 * (pod1_data_database/day2/data/). If Pod 1 restructures folders on a later
 * day (e.g. drops the "day2" subfolder), update DEFAULT_PATH accordingly —
 * flagged here so it's not a silent breakage.
 */
public class RequestCsvLoader {

    public static final String DEFAULT_PATH = "pod1_data_database/day2/data/requests.csv";

    public static List<Request> load(String path) throws Exception {
        List<Request> requests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] d = line.split(",");

                int id = Integer.parseInt(d[0].trim());
                int originLocationId = Integer.parseInt(d[3].trim());
                int destinationLocationId = Integer.parseInt(d[4].trim());
                String type = d[5].trim();
                int urgencyLevel = Integer.parseInt(d[6].trim());
                String status = d[7].trim();
                String submittedTime = d[8].trim();

                requests.add(new Request(id, type, urgencyLevel, submittedTime,
                        originLocationId, destinationLocationId, status));
            }
        }

        return requests;
    }

    public static List<Request> loadDefault() throws Exception {
        return load(DEFAULT_PATH);
    }
}
