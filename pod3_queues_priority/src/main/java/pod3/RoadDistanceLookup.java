package pod3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Implements GreedyAssignment.DistanceLookup using Pod 1's real roads.csv.
 * Roads are treated as undirected (a road A->B can be travelled either way).
 * If no direct road exists between two locations, a large fallback penalty
 * is returned instead of failing outright — proper multi-hop shortest-path
 * routing is Pod 5's job (they build on top of this pod's priority tool),
 * not Pod 3's. This is a direct-road-only lookup, sufficient for Pod 3's
 * own greedy assignment demo.
 */
public class RoadDistanceLookup implements GreedyAssignment.DistanceLookup {

    private static final double NO_DIRECT_ROAD_PENALTY = 100_000.0;

    private final Map<Long, Double> distances = new HashMap<>();

    public RoadDistanceLookup(String roadsCsvPath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(roadsCsvPath))) {
            br.readLine(); // skip header: id,from_location_id,to_location_id,distance,estimated_time

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] d = line.split(",");

                int from = Integer.parseInt(d[1].trim());
                int to = Integer.parseInt(d[2].trim());
                double distance = Double.parseDouble(d[3].trim());

                distances.put(key(from, to), distance);
                distances.put(key(to, from), distance); // undirected
            }
        }
    }

    @Override
    public double distanceBetween(int locationIdA, int locationIdB) {
        if (locationIdA == locationIdB) return 0.0;
        return distances.getOrDefault(key(locationIdA, locationIdB), NO_DIRECT_ROAD_PENALTY);
    }

    private long key(int a, int b) {
        return ((long) a << 32) | (b & 0xFFFFFFFFL);
    }
}
