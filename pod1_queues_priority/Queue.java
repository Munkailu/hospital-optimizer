/**
 * Queue<T>
 * --------
 * A hand-built FIFO (first-in, first-out) line. This does NOT use
 * java.util.LinkedList or java.util.Queue — it's built from plain
 * linked nodes, since the brief requires custom-built structures.
 *
 * Operations: enqueue (add to back), dequeue (remove from front),
 * peek (look at front without removing), isEmpty, size.
 *
 * "First come, first served" (Member 7's rule in Pod 3) is exactly
 * this class used directly: whoever enqueued first, dequeues first.
 */
public class Queue<T> {

    // A single link in the chain
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    private Node<T> front; // where we remove from
    private Node<T> back;  // where we add to
    private int size;

    public Queue() {
        front = null;
        back = null;
        size = 0;
    }

    /** Add an item to the back of the line. O(1). */
    public void enqueue(T item) {
        Node<T> node = new Node<>(item);
        if (isEmpty()) {
            front = node;
            back = node;
        } else {
            back.next = node;
            back = node;
        }
        size++;
    }

    /** Remove and return the item at the front of the line. O(1).
     *  Throws if the line is empty — this is the required "bad input"
     *  / empty-container edge case. */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue: the queue is empty.");
        }
        T value = front.value;
        front = front.next;
        if (front == null) {
            back = null; // the line is now empty
        }
        size--;
        return value;
    }

    /** Look at the front item without removing it. */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek: the queue is empty.");
        }
        return front.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}