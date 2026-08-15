package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueueTest {

    @Test
    void newPriorityQueueIsEmpty() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        assertTrue(pq.isEmpty());
    }

    @Test
    void removesInAscendingOrderForNaturalComparator() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        int[] values = {5, 1, 8, 3, 9, 2};
        for (int v : values) pq.add(v);

        int previous = Integer.MIN_VALUE;
        while (!pq.isEmpty()) {
            int next = pq.removeHighestPriority();
            assertTrue(next >= previous);
            previous = next;
        }
    }

    @Test
    void requestsComeOutMostUrgentFirst() {
        PriorityQueue<Request> pq = new PriorityQueue<>(Request.BY_URGENCY_DESC);
        pq.add(new Request(1, "patient_transfer", 2, "09:00", 1, 2, "pending"));
        pq.add(new Request(2, "patient_transfer", 5, "09:05", 3, 4, "pending"));
        pq.add(new Request(3, "patient_transfer", 3, "09:10", 5, 6, "pending"));

        assertEquals(5, pq.removeHighestPriority().getUrgencyLevel());
        assertEquals(3, pq.removeHighestPriority().getUrgencyLevel());
        assertEquals(2, pq.removeHighestPriority().getUrgencyLevel());
        assertTrue(pq.isEmpty());
    }

    @Test
    void removeOnEmptyThrows() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        assertThrows(NoSuchElementException.class, pq::removeHighestPriority);
    }

    @Test
    void peekDoesNotRemove() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.naturalOrder());
        pq.add(5);
        pq.add(1);
        assertEquals(1, pq.peek());
        assertEquals(2, pq.size());
    }
}
