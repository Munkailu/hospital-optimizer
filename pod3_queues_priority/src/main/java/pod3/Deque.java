package pod3;

import java.util.NoSuchElementException;

/**
 * Double-ended line, built from scratch as a doubly linked list.
 * Supports add/remove from either end — this is what lets urgent requests
 * jump ahead of the line (addFront) while normal requests join the back
 * (addBack), used by the "urgent requests jump the line" scheduling rule.
 */
public class Deque<T> {

    private static class Node<T> {
        T value;
        Node<T> prev, next;
        Node(T value) { this.value = value; }
    }

    private Node<T> head, tail;
    private int size = 0;

    public void addFront(T item) {
        Node<T> node = new Node<>(item);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void addBack(T item) {
        Node<T> node = new Node<>(item);
        if (tail == null) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public T removeFront() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        T value = head.value;
        head = head.next;
        if (head == null) tail = null; else head.prev = null;
        size--;
        return value;
    }

    public T removeBack() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        T value = tail.value;
        tail = tail.prev;
        if (tail == null) head = null; else tail.next = null;
        size--;
        return value;
    }

    public T peekFront() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        return head.value;
    }

    public T peekBack() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        return tail.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
