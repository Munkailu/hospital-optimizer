package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class SimpleQueueTest {

    @Test
    void newQueueIsEmpty() {
        SimpleQueue<Integer> q = new SimpleQueue<>();
        assertTrue(q.isEmpty());
    }

    @Test
    void enqueueThenDequeueIsFIFO() {
        SimpleQueue<Integer> q = new SimpleQueue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        assertEquals(1, q.dequeue());
        assertEquals(2, q.dequeue());
        assertEquals(3, q.dequeue());
        assertTrue(q.isEmpty());
    }

    @Test
    void dequeueOnEmptyThrows() {
        SimpleQueue<Integer> q = new SimpleQueue<>();
        assertThrows(NoSuchElementException.class, q::dequeue);
    }

    @Test
    void singleItemQueueWorks() {
        SimpleQueue<String> q = new SimpleQueue<>();
        q.enqueue("only");
        assertEquals("only", q.peek());
        assertEquals("only", q.dequeue());
        assertTrue(q.isEmpty());
    }
}
