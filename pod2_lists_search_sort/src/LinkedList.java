package pod2_lists_search_sort.src;

public class LinkedList {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public void addFirst(int data) {
        Node newNode = new Node(data);

        newNode.next = head;
        head = newNode;
        size++;
    }

    public void addLast(int data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            size++;
            return;
        }

        Node current = head;

        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
        size++;
    }

    public void insert(int index, int data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        Node newNode = new Node(data);
        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    public int removeFirst() {
        if (head == null) {
            throw new IllegalStateException(
                "Cannot remove from an empty list."
            );
        }

        int removedData = head.data;
        head = head.next;
        size--;

        return removedData;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == 0) {
            return removeFirst();
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }

        int removedData = current.next.data;
        current.next = current.next.next;
        size--;

        return removedData;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void walkThrough() {
        Node current = head;

        while (current != null) {
            System.out.print(current.data);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        Node current = head;

        while (current != null) {
            result.append(current.data);

            if (current.next != null) {
                result.append(" -> ");
            }

            current = current.next;
        }

        return result.toString();
    }
}