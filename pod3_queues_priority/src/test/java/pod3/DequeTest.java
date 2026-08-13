package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;

public class DequeTest {

    @Test
    void newDequeIsEmpty() {
        Deque<Integer> d = new Deque<>();
        assertTrue(d.isEmpty());
    }

    @Test
    void addFrontAndAddBackWorkTogether() {
        Deque<Integer> d = new Deque<>();
        d.addBack(2);
        d.addBack(3);
        d.addFront(1);
        assertEquals(1, d.peekFront());
        assertEquals(3, d.peekBack());
        assertEquals(1, d.removeFront());
        assertEquals(2, d.removeFront());
        assertEquals(3, d.removeFront());
        assertTrue(d.isEmpty());
    }

    @Test
    void removeBackWorks() {
        Deque<Integer> d = new Deque<>();
        d.addBack(1);
        d.addBack(2);
        d.addBack(3);
        assertEquals(3, d.removeBack());
        assertEquals(2, d.removeBack());
        assertEquals(1, d.removeBack());
        assertTrue(d.isEmpty());
    }

    @Test
    void singleItemDequeWorksFromBothEnds() {
        Deque<String> d = new Deque<>();
        d.addFront("only");
        assertEquals("only", d.peekFront());
        assertEquals("only", d.peekBack());
        assertEquals("only", d.removeBack());
        assertTrue(d.isEmpty());
    }

    @Test
    void removeFrontOnEmptyThrows() {
        Deque<Integer> d = new Deque<>();
        assertThrows(NoSuchElementException.class, d::removeFront);
    }

    @Test
    void removeBackOnEmptyThrows() {
        Deque<Integer> d = new Deque<>();
        assertThrows(NoSuchElementException.class, d::removeBack);
    }
}
