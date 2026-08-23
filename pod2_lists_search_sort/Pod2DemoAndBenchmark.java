package pod2_lists_search_sort;

import pod2_lists_search_sort.algorithms.SearchEngine;
import pod2_lists_search_sort.algorithms.SortEngine;
import pod2_lists_search_sort.structures.CustomArrayList;
import pod2_lists_search_sort.structures.CustomDoublyLinkedList;

import java.util.Comparator;
import java.util.Random;

public class Pod2DemoAndBenchmark {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  POD 2: DATA STRUCTURES & SEARCH/SORT VERIFIER  ");
        System.out.println("=================================================");

        System.out.println("\n[1] TESTING DYNAMIC ARRAY & RESIZING");
        CustomArrayList<Integer> arr = new CustomArrayList<>(2);
        arr.add(10);
        arr.add(20);
        arr.add(30);
        System.out.println("Capacity after resize: " + arr.capacity() + ", Size: " + arr.size());

        System.out.println("\n[2] TESTING DOUBLY LINKED LIST & ITERATOR");
        CustomDoublyLinkedList<String> dlist = new CustomDoublyLinkedList<>();
        dlist.addFirst("Pharmacy");
        dlist.addLast("ICU");
        dlist.insertAfter(0, "Emergency");
        System.out.print("Traversing: ");
        for (String item : dlist) System.out.print("[" + item + "] -> ");
        System.out.println("END");

        System.out.println("\n[3] SEARCH ENGINE & COUNTEREXAMPLE DEMO");
        CustomArrayList<Integer> sortedList = new CustomArrayList<>();
        int[] sortedVals = {101, 108, 204, 308, 412, 505};
        for (int v : sortedVals) sortedList.add(v);
        Comparator<Integer> cmp = Integer::compareTo;
        System.out.println("Binary search on sorted list: Found 308 at index " + SearchEngine.binarySearch(sortedList, 308, cmp));

        CustomArrayList<Integer> unsortedList = new CustomArrayList<>();
        int[] unsortedVals = {505, 101, 412, 108, 308, 204};
        for (int v : unsortedVals) unsortedList.add(v);
        System.out.println("COUNTEREXAMPLE (Unsorted): Search for 308 returned " + SearchEngine.binarySearch(unsortedList, 308, cmp));

        System.out.println("\n[4] EMPIRICAL RUNTIME BENCHMARK");
        int[] sizes = {100, 500, 1000, 5000, 10000};
        Random rng = new Random(42);
        System.out.printf("%-10s | %-12s | %-12s | %-12s | %-12s\n", "Size (N)", "Selection(ms)", "Insertion(ms)", "MergeSort(ms)", "QuickSort(ms)");
        System.out.println("----------------------------------------------------------------------");

        for (int n : sizes) {
            CustomArrayList<Integer> base = new CustomArrayList<>(n);
            for (int i = 0; i < n; i++) base.add(rng.nextInt(100000));

            long t1 = measure(() -> SortEngine.selectionSort(copy(base), cmp));
            long t2 = measure(() -> SortEngine.insertionSort(copy(base), cmp));
            long t3 = measure(() -> SortEngine.mergeSort(copy(base), cmp));
            long t4 = measure(() -> SortEngine.quickSort(copy(base), cmp));

            System.out.printf("%-10d | %-12.3f | %-12.3f | %-12.3f | %-12.3f\n", n, t1 / 1e6, t2 / 1e6, t3 / 1e6, t4 / 1e6);
        }
    }

    private static CustomArrayList<Integer> copy(CustomArrayList<Integer> src) {
        CustomArrayList<Integer> dst = new CustomArrayList<>(src.size());
        for (Integer val : src) dst.add(val);
        return dst;
    }

    private static long measure(Runnable action) {
        long start = System.nanoTime();
        action.run();
        return System.nanoTime() - start;
    }
}
