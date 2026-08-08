/**
 * CircularQueue<T>
 * ----------------
 * A fixed-capacity, array-based FIFO queue where the front and back
 * indices "wrap around" back to 0 once they hit the end of the array,
 * instead of the array having to shift or grow. This is the trickiest
 * part of Pod 3's Day 2 work — the brief specifically calls out that
 * examiners test wrap-around handling hardest, so this class is
 * deliberately careful about the two hard edge cases:
 *   - the queue is completely FULL (front and back "meet" from a full line)
 *   - the queue is completely EMPTY (front and back "meet" from an empty line)
 *
 * Both cases can make front == back look identical unless you track
 * size separately, which is what this class does.
 */
public class CircularQueue<T> {

    private final Object[] items;
    private final int capacity;
    private int front;   // index of the next item to remove
    private int back;    // index where the next item will be added
    private int size;    // how many items are currently stored

    public CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.capacity = capacity;
        this.items = new Object[capacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    /** Add an item to the back of the line. O(1).
     *  Throws if the queue is already full — required edge case. */
    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("Cannot enqueue: the circular queue is full (capacity " + capacity + ").");
        }
        items[back] = item;
        back = (back + 1) % capacity; // <-- the wrap-around step
        size++;
    }

    /** Remove and return the item at the front of the line. O(1).
     *  Throws if the queue is empty — required edge case. */
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue: the circular queue is empty.");
        }
        T value = (T) items[front];
        items[front] = null; // avoid holding a stale reference
        front = (front + 1) % capacity; // <-- the wrap-around step
        size--;
        return value;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek: the circular queue is empty.");
        }
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