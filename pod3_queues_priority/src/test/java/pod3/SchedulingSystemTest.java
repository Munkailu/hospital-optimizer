package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SchedulingSystemTest {

    private List<Request> sampleRequests() {
        // Same small set of requests used across all three rules, so the
        // report can show side by side how differently each rule decides.
        return List.of(
                new Request(1, "patient_transfer", 2, "09:00", 1, 2, "pending"),  // arrives 1st, low urgency
                new Request(2, "equipment_delivery", 5, "09:05", 3, 4, "pending"), // arrives 2nd, critical
                new Request(3, "pharmacy_run", 1, "09:10", 5, 6, "pending"),       // arrives 3rd, lowest urgency
                new Request(4, "patient_transfer", 4, "09:15", 7, 8, "pending")    // arrives 4th, high urgency
        );
    }

    @Test
    void fcfsKeepsStrictArrivalOrderRegardlessOfUrgency() {
        List<Request> order = SchedulingSystem.firstComeFirstServed(sampleRequests());
        List<Integer> ids = order.stream().map(Request::getId).toList();
        assertEquals(List.of(1, 2, 3, 4), ids);
    }

    @Test
    void urgentJumpsTheLinePutsHighUrgencyRequestsFirst() {
        List<Request> order = SchedulingSystem.urgentJumpsTheLine(sampleRequests());
        List<Integer> ids = order.stream().map(Request::getId).toList();
        // Requests 2 (urgency 5) and 4 (urgency 4) jump ahead of 1 and 3.
        // addFront reverses arrival order among urgent ones: 4 arrives after 2,
        // so 4 ends up in front of 2. Non-urgent 1, 3 keep their arrival order at the back.
        assertEquals(List.of(4, 2, 1, 3), ids);
    }

    @Test
    void mostImportantFirstIgnoresArrivalOrderEntirely() {
        List<Request> order = SchedulingSystem.mostImportantFirst(sampleRequests());
        List<Integer> urgencies = order.stream().map(Request::getUrgencyLevel).toList();
        assertEquals(List.of(5, 4, 2, 1), urgencies);
    }

    @Test
    void emptyRequestListProducesEmptyOrderForAllThreeRules() {
        assertTrue(SchedulingSystem.firstComeFirstServed(List.of()).isEmpty());
        assertTrue(SchedulingSystem.urgentJumpsTheLine(List.of()).isEmpty());
        assertTrue(SchedulingSystem.mostImportantFirst(List.of()).isEmpty());
    }
}
