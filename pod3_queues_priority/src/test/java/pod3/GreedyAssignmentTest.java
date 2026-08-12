package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class GreedyAssignmentTest {

    @Test
    void assignsNearestAvailableResourceToMostUrgentRequestFirst() {
        List<Request> requests = List.of(
                new Request(1, "patient_transfer", 5, "09:00", 100, 0, "pending"),
                new Request(2, "patient_transfer", 2, "09:05", 200, 0, "pending")
        );
        List<Resource> resources = List.of(
                new Resource(1, "ambulance", "available", 100), // exactly at R1's origin
                new Resource(2, "ambulance", "available", 205)  // near R2's origin
        );
        GreedyAssignment.DistanceLookup distances = (a, b) -> (double) Math.abs(a - b);

        List<GreedyAssignment.Assignment> result = GreedyAssignment.assign(requests, resources, distances);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRequest().getId());   // urgency 5 processed first
        assertEquals(1, result.get(0).getResource().getId());  // gets the resource at its own location
        assertEquals(0.0, result.get(0).getDistance());
    }

    @Test
    void greedyCanProduceAWorseResultThanOptimalMatching() {
        // Required "when it fails" example for the greedy method (Section 5/8 of the brief).
        //
        // Setup: R1 is more urgent, so it is assigned first. Resource A is R1's
        // best choice (distance 1) but only slightly better than Resource B
        // (distance 2). Resource A, however, is the ONLY viable option for R2 —
        // Resource B is disastrously far from R2 (distance 100).
        //
        // Greedy gives R1 its slightly-preferred resource (A) without knowing
        // R2 desperately needs it, forcing R2 onto the far resource (B).
        Request r1 = new Request(1, "patient_transfer", 5, "09:00", 1, 0, "pending");  // higher urgency, processed first
        Request r2 = new Request(2, "patient_transfer", 3, "09:05", 2, 0, "pending");  // lower urgency, processed second
        Resource resourceA = new Resource(10, "ambulance", "available", 10);
        Resource resourceB = new Resource(20, "ambulance", "available", 20);

        GreedyAssignment.DistanceLookup distances = (locationA, locationB) -> {
            if (locationA == 1 && locationB == 10) return 1.0;   // R1 <-> A
            if (locationA == 1 && locationB == 20) return 2.0;   // R1 <-> B
            if (locationA == 2 && locationB == 10) return 1.0;   // R2 <-> A
            if (locationA == 2 && locationB == 20) return 100.0; // R2 <-> B  (catastrophically far)
            throw new IllegalArgumentException("Unexpected location pair in test");
        };

        List<GreedyAssignment.Assignment> greedyResult =
                GreedyAssignment.assign(List.of(r1, r2), List.of(resourceA, resourceB), distances);
        double greedyTotal = GreedyAssignment.totalDistance(greedyResult);

        // Greedy: R1 takes A (dist 1, its slightly-preferred option),
        // leaving R2 stuck with B (dist 100). Total = 101.
        assertEquals(101.0, greedyTotal, 0.001);

        // The smarter, globally optimal pairing swaps them:
        // R1 -> B (dist 2), R2 -> A (dist 1). Total = 3.
        double optimalTotal = distances.distanceBetween(1, 20) + distances.distanceBetween(2, 10);
        assertEquals(3.0, optimalTotal, 0.001);

        // The required proof: greedy's result is clearly, dramatically worse.
        assertTrue(greedyTotal > optimalTotal,
                "Greedy total (" + greedyTotal + ") should be worse than the optimal total (" + optimalTotal + ")");
    }

    @Test
    void noResourcesAvailableProducesNoAssignments() {
        List<Request> requests = List.of(new Request(1, "patient_transfer", 3, "09:00", 1, 0, "pending"));
        GreedyAssignment.DistanceLookup distances = (a, b) -> 5.0;

        List<GreedyAssignment.Assignment> result = GreedyAssignment.assign(requests, List.of(), distances);
        assertTrue(result.isEmpty());
    }

    @Test
    void emptyRequestListProducesNoAssignments() {
        List<Resource> resources = List.of(new Resource(1, "ambulance", "available", 1));
        GreedyAssignment.DistanceLookup distances = (a, b) -> 5.0;

        List<GreedyAssignment.Assignment> result = GreedyAssignment.assign(List.of(), resources, distances);
        assertTrue(result.isEmpty());
    }
}
