package pod3;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Generic LIFO stack. Built from scratch (backed by an ArrayList as raw
 * storage only — all push/pop/peek logic is custom, not java.util.Stack).
 */
public class Stack<T> {

    private final List<T> items = new ArrayList<>();

    public void push(T item) {
        items.add(item);
    }

    public T pop() {
        if (isEmpty()) throw new EmptyStackException();
        return items.remove(items.size() - 1);
    }

    public T peek() {
        if (isEmpty()) throw new EmptyStackException();
        return items.get(items.size() - 1);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}
