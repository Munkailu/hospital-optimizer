// Owner: Dogbe Nicole Eyram (Pod 1)

import java.util.List;

public class QueueDemo {
    public static void main(String[] args) {
        testPlainQueue();
        testCircularQueueWrapAround();
        testEdgeCases();
        compareFcfsVsMostUrgentFirst();
    }

    static void testPlainQueue() {
        System.out.println("== Plain Queue: FIFO order ==");
        Queue<String> q = new Queue<>();
        q.enqueue("R001");
        q.enqueue("R002");
        q.enqueue("R003");
        System.out.println("Dequeue order: " + q.dequeue() + ", " + q.dequeue() + ", " + q.dequeue());
        System.out.println("Expected:      R001, R002, R003\n");
    }

    static void testCircularQueueWrapAround() {
        System.out.println("== CircularQueue: wrap-around ==");
        CircularQueue<String> cq = new CircularQueue<>(3);

        cq.enqueue("A"); cq.enqueue("B"); cq.enqueue("C");
        System.out.println("Removed: " + cq.dequeue());
        System.out.println("Removed: " + cq.dequeue());

        cq.enqueue("D");
        cq.enqueue("E");
        System.out.println("Size after wrap-around inserts: " + cq.size() + " (expected 3: C, D, E)");
        System.out.println("Removed: " + cq.dequeue());
        System.out.println("Removed: " + cq.dequeue());
        System.out.println("Removed: " + cq.dequeue());
        System.out.println("Expected order: C, D, E\n");
    }

    static void testEdgeCases() {
        System.out.println("== Edge cases ==");

        Queue<String> emptyQ = new Queue<>();
        try {
            emptyQ.dequeue();
            System.out.println("FAIL: expected an exception on empty dequeue");
        } catch (IllegalStateException e) {
            System.out.println("OK: empty Queue.dequeue() correctly threw: " + e.getMessage());
        }

        Queue<String> oneItem = new Queue<>();
        oneItem.enqueue("only-one");
        System.out.println("OK: single-item queue peek = " + oneItem.peek());

        CircularQueue<String> fullCq = new CircularQueue<>(2);
        fullCq.enqueue("X");
        fullCq.enqueue("Y");
        try {
            fullCq.enqueue("Z");
            System.out.println("FAIL: expected an exception on enqueue to a full circular queue");
        } catch (IllegalStateException e) {
            System.out.println("OK: full CircularQueue.enqueue() correctly threw: " + e.getMessage());
        }

        CircularQueue<String> emptyCq = new CircularQueue<>(2);
        try {
            emptyCq.dequeue();
            System.out.println("FAIL: expected an exception on empty circular dequeue");
        } catch (IllegalStateException e) {
            System.out.println("OK: empty CircularQueue.dequeue() correctly threw: " + e.getMessage());
        }
        System.out.println();
    }

    static void compareFcfsVsMostUrgentFirst() {
        System.out.println("== FCFS vs Most-urgent-first (same data) ==");

        Queue<Request> incomingForFcfs = new Queue<>();
        Queue<Request> incomingForUrgent = new Queue<>();

        Request[] sample = {
            new Request("R001", "pharmacy_run",      2, 1000, "L001", "L002", "PENDING"),
            new Request("R002", "patient_transfer",  5, 1010, "L003", "L004", "PENDING"),
            new Request("R003", "equipment_delivery", 3, 1020, "L005", "L006", "PENDING"),
            new Request("R004", "patient_transfer",  5, 1005, "L007", "L008", "PENDING"),
            new Request("R005", "pharmacy_run",      1, 1030, "L001", "L009", "PENDING"),
        };
        for (Request r : sample) {
            incomingForFcfs.enqueue(r);
            incomingForUrgent.enqueue(r);
        }

        System.out.print("FCFS order:            ");
        while (!incomingForFcfs.isEmpty()) {
            System.out.print(incomingForFcfs.dequeue().id + " ");
        }
        System.out.println();

        List<Request> urgentOrder = MostUrgentFirstScheduler.dispatchOrder(incomingForUrgent);
        System.out.print("Most-urgent-first order: ");
        for (Request r : urgentOrder) {
            System.out.print(r.id + "(u" + r.urgencyLevel + ") ");
        }
        System.out.println();
        System.out.println("Note how R004 beats R002 despite arriving later -- same urgency (5),");
        System.out.println("but R004 was submitted earlier, so the tie-break favors it.");
    }
}