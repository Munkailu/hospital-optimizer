package pod3;
import java.util.List;

public class SchedulingComparison {

    public static void runComparison(List<Request> subset) {
        System.out.println("--- First-Come-First-Served ---");
        CircularQueue<Request> fcfs = new CircularQueue<>(subset.size());
        for (Request r : subset) fcfs.enqueue(r);
        while (!fcfs.isEmpty()) {
            System.out.println(fcfs.dequeue());
        }

        System.out.println("\n--- Urgent-Jumps-The-Line ---");
        Deque<Request> deque = new Deque<>();
        for (Request r : subset) {
            // Assuming urgency >= 4 is "urgent"
            if (r.getUrgencyLevel() >= 4) deque.addFront(r);
            else deque.addBack(r);
        }
        while (!deque.isEmpty()) {
            System.out.println(deque.removeFront());
        }

        System.out.println("\n--- Most-Important-First ---");
        PriorityQueue<Request> pq = new PriorityQueue<>(Request.BY_URGENCY_DESC);
        for (Request r : subset) pq.add(r);
        while (!pq.isEmpty()) {
            System.out.println(pq.removeHighestPriority());
        }
    }
}