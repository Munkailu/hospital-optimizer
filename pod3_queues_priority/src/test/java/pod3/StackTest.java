package pod3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.EmptyStackException;

public class StackTest {

    @Test
    void newStackIsEmpty() {
        Stack<Integer> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushThenPopReturnsLastItemFirst() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekDoesNotRemove() {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        assertEquals("a", stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void popOnEmptyStackThrows() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void peekOnEmptyStackThrows() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(EmptyStackException.class, stack::peek);
    }

    @Test
    void activityLogRecordsAndUndoesInReverseOrder() {
        ActivityLog log = new ActivityLog();
        log.record("Request 1 created");
        log.record("Resource assigned");
        log.record("Request 1 completed");

        assertEquals("Request 1 completed", log.undoLast());
        assertEquals("Resource assigned", log.undoLast());
        assertEquals("Request 1 created", log.undoLast());
        assertTrue(log.isEmpty());
    }
}
