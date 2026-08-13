package pod3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The priority tool: a binary min-heap ordered by the given Comparator.
 * "Most important first" means the Comparator should treat the most
 * important item as the smallest (e.g. Request.BY_URGENCY_DESC), so it
 * rises to the top and is returned first by removeHighestPriority().
 * Built from scratch (not java.util.PriorityQueue).
 */
public class PriorityQueue<T> {

    private final List<T> heap = new ArrayList<>();
    private final Comparator<T> comparator;

    public PriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void add(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public T removeHighestPriority() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is empty");
        T top = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return top;
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue is empty");
        return heap.get(0);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (comparator.compare(heap.get(i), heap.get(parent)) < 0) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int left = 2 * i + 1, right = 2 * i + 2, smallest = i;
            if (left < n && comparator.compare(heap.get(left), heap.get(smallest)) < 0) smallest = left;
            if (right < n && comparator.compare(heap.get(right), heap.get(smallest)) < 0) smallest = right;
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        T tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }
}
