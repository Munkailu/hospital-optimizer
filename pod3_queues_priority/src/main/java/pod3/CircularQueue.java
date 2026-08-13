package pod3;

import java.util.NoSuchElementException;

/**
 * Fixed-capacity, array-backed queue with wraparound indexing.
 * This is the part examiners typically test hardest — front/rear indices
 * wrap using modulo arithmetic instead of shifting elements, and full vs.
 * empty are distinguished explicitly via a size counter (not by comparing
 * front/rear alone, which is ambiguous in a circular buffer).
 */
public class CircularQueue<T> {

    private final Object[] items;
    private final int capacity;
    private int front = 0;
    private int rear = -1;
    private int size = 0;

    public CircularQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be positive");
        this.capacity = capacity;
        this.items = new Object[capacity];
    }

    public void enqueue(T item) {
        if (isFull()) throw new IllegalStateException("Queue is full");
        rear = (rear + 1) % capacity;
        items[rear] = item;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T value = (T) items[front];
        items[front] = null;
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return (T) items[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }
}
