package pod3;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the three request-handling rules required by the project brief
 * (Day 4 deliverable), all operating on the same real Request data so their
 * results can be compared side by side in the report.
 */
public class SchedulingSystem {

    /** Rule 1: strict arrival order, ignoring urgency entirely. */
    public static List<Request> firstComeFirstServed(List<Request> requests) {
        SimpleQueue<Request> queue = new SimpleQueue<>();
        for (Request r : requests) queue.enqueue(r);

        List<Request> order = new ArrayList<>();
        while (!queue.isEmpty()) order.add(queue.dequeue());
        return order;
    }

    /**
     * Rule 2: normal arrival order, but any request with urgency_level >= 4
     * jumps to the front of the line ahead of everything already waiting.
     * Implemented with a Deque: urgent requests go to the front, everything
     * else joins the back, in arrival order within each group.
     */
    public static List<Request> urgentJumpsTheLine(List<Request> requests) {
        Deque<Request> deque = new Deque<>();
        for (Request r : requests) {
            if (r.getUrgencyLevel() >= 4) {
                deque.addFront(r);
            } else {
                deque.addBack(r);
            }
        }

        List<Request> order = new ArrayList<>();
        while (!deque.isEmpty()) order.add(deque.removeFront());
        return order;
    }

    /** Rule 3: always serve the single most urgent request next, regardless of arrival order. */
    public static List<Request> mostImportantFirst(List<Request> requests) {
        PriorityQueue<Request> pq = new PriorityQueue<>(Request.BY_URGENCY_DESC);
        for (Request r : requests) pq.add(r);

        List<Request> order = new ArrayList<>();
        while (!pq.isEmpty()) order.add(pq.removeHighestPriority());
        return order;
    }
}
