package pod3;

import java.util.List;

/**
 * Demonstrates all of Pod 3's tools running on Pod 1's real, merged dataset
 * (300 requests, 30 resources, 100 roads) instead of synthetic test samples.
 *
 * This satisfies the Day 3/4 requirement to "produce a first real example
 * showing which request comes out first, in order" using actual data, and
 * proves the priority tool + scheduling rules + greedy method genuinely
 * work end-to-end against Pod 1's data before handing the priority tool to
 * Pod 4/5.
 */
public class SchedulingDemo {

    public static void main(String[] args) throws Exception {

        List<Request> requests = RequestCsvLoader.loadDefault();
        List<Resource> resources = ResourceCsvLoader.loadAvailable(ResourceCsvLoader.DEFAULT_PATH);
        RoadDistanceLookup distances = new RoadDistanceLookup("pod1_data_database/day2/data/roads.csv");

        System.out.println("Loaded " + requests.size() + " real requests and "
                + resources.size() + " available resources.\n");

        printFirstN("FIRST-COME-FIRST-SERVED (first 5)",
                SchedulingSystem.firstComeFirstServed(requests), 5);

        printFirstN("URGENT-JUMPS-THE-LINE (first 5)",
                SchedulingSystem.urgentJumpsTheLine(requests), 5);

        printFirstN("MOST-IMPORTANT-FIRST (first 5)",
                SchedulingSystem.mostImportantFirst(requests), 5);

        System.out.println("\nGREEDY ASSIGNMENT (grab the best option now) — first 5 assignments");
        System.out.println("------------------------------------------------------------------");
        List<GreedyAssignment.Assignment> assignments =
                GreedyAssignment.assign(requests, resources, distances);

        for (int i = 0; i < Math.min(5, assignments.size()); i++) {
            GreedyAssignment.Assignment a = assignments.get(i);
            System.out.printf("Request #%d (urgency %d) -> Resource #%d, distance %.1f%n",
                    a.getRequest().getId(), a.getRequest().getUrgencyLevel(),
                    a.getResource().getId(), a.getDistance());
        }

        System.out.printf("%nTotal requests assigned: %d / %d%n", assignments.size(), requests.size());
        System.out.printf("Total distance across all assignments: %.1f%n",
                GreedyAssignment.totalDistance(assignments));

        // Activity log demo: record each assignment as an undoable action
        ActivityLog log = new ActivityLog();
        for (GreedyAssignment.Assignment a : assignments) {
            log.record("Assigned resource " + a.getResource().getId()
                    + " to request " + a.getRequest().getId());
        }
        System.out.println("\nActivity log recorded " + log.size() + " actions.");
        System.out.println("Most recent action (undoable): " + log.peekLast());
    }

    private static void printFirstN(String title, List<Request> ordered, int n) {
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int i = 0; i < Math.min(n, ordered.size()); i++) {
            Request r = ordered.get(i);
            System.out.printf("  #%d  id=%-4d urgency=%d  type=%-20s status=%s%n",
                    i + 1, r.getId(), r.getUrgencyLevel(), r.getType(), r.getStatus());
        }
        System.out.println();
    }
}
