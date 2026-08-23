package pod2_lists_search_sort.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomDoublyLinkedList<T> implements Iterable<T> {
    private static class Node<E> {
        E data;
        Node<E> prev;
        Node<E> next;

        Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public CustomDoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element, null, head);
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;
        size++;
    }

    public void addLast(T element) {
        Node<T> newNode = new Node<>(element, tail, null);
        if (tail != null) {
            tail.next = newNode;
        } else {
            head = newNode;
        }
        tail = newNode;
        size++;
    }

    public void insertAfter(int index, T element) {
        Node<T> target = getNode(index);
        Node<T> nextNode = target.next;
        Node<T> newNode = new Node<>(element, target, nextNode);
        target.next = newNode;
        if (nextNode != null) {
            nextNode.prev = newNode;
        } else {
            tail = newNode;
        }
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
        if (index < (size >> 1)) {
            Node<T> curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
            return curr;
        } else {
            Node<T> curr = tail;
            for (int i = size - 1; i > index; i--) curr = curr.prev;
            return curr;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T item = current.data;
                current = current.next;
                return item;
            }
        };
    }
}
