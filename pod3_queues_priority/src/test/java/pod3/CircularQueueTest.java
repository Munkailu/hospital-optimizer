package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CircularQueueTest {

    @Test
    void newQueueIsEmptyNotFull() {
        CircularQueue<Integer> q = new CircularQueue<>(3);
        assertTrue(q.isEmpty());
        assertFalse(q.isFull());
    }

    @Test
    void enqueueDequeueIsFIFO() {
        CircularQueue<Integer> q = new CircularQueue<>(3);
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        assertTrue(q.isFull());
        assertEquals(1, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
        assertTrue(q.isEmpty());
    }

    @Test
    void wraparoundActuallyWraps() {
        // capacity 3: fill, drain some, refill past the physical end of the array
        CircularQueue<Integer> q = new CircularQueue<>(3);
        q.enqueue(1); q.enqueue(2); q.enqueue(3);
        assertEquals(1, q.dequeue());  // front moves from 0 -> 1
        assertEquals(2, q.dequeue());  // front moves from 1 -> 2
        q.enqueue(4);                  // rear wraps from 2 -> 0
        q.enqueue(5);                  // rear wraps from 0 -> 1
        assertTrue(q.isFull());
        assertEquals(3, q.dequeue());
        assertEquals(4, q.dequeue());
        assertEquals(5, q.dequeue());
        assertTrue(q.isEmpty());
    }

    @Test
    void enqueueOnFullQueueThrows() {
        CircularQueue<Integer> q = new CircularQueue<>(2);
        q.enqueue(1);
        q.enqueue(2);
        assertThrows(IllegalStateException.class, () -> q.enqueue(3));
    }

    @Test
    void dequeueOnEmptyQueueThrows() {
        CircularQueue<Integer> q = new CircularQueue<>(2);
        assertThrows(java.util.NoSuchElementException.class, q::dequeue);
    }

    @Test
    void invalidCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new CircularQueue<Integer>(0));
    }
}
