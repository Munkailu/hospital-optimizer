package pod3;

import java.util.NoSuchElementException;

/**
 * Generic FIFO queue, built from scratch as a singly linked list
 * (no java.util.Queue/LinkedList used for the core logic).
 * Used for the first-come-first-served scheduling rule.
 */
public class SimpleQueue<T> {

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    private Node<T> head, tail;
    private int size = 0;

    public void enqueue(T item) {
        Node<T> node = new Node<>(item);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        T value = head.value;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        return head.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
