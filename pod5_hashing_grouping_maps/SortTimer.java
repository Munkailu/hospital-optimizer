// Owner: Ibrahim Hidayat (Pod 5)
// Speed-testing tool: times any Sorter implementation against random arrays
// of varying size. Plug in teammates' sorts by implementing Sorter.
import java.util.Random;

public class SortTimer {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 50000};

        Sorter[] sorters = {
            new MergeSort(),
            new QuickSort()
                // Add teammates' sorting algorithms here when they are ready.
// For example: new BubbleSort() or new SelectionSort()
        };

        for (int size : sizes) {
            System.out.println("Array size: " + size);
            int[] baseArray = generateRandomArray(size);

            for (Sorter sorter : sorters) {
                int[] copy = baseArray.clone();
                long elapsed = timeSort(sorter, copy);
                System.out.printf("  %-15s %,d ns (%.3f ms)%n",
                        sorter.getName(), elapsed, elapsed / 1_000_000.0);

                if (!isSorted(copy)) {
                    System.out.println("  WARNING: " + sorter.getName() + " did not sort correctly!");
                }
            }
            System.out.println();
        }
    }

    private static long timeSort(Sorter sorter, int[] arr) {
        long start = System.nanoTime();
        sorter.sort(arr);
        long end = System.nanoTime();
        return end - start;
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1_000_000);
        }
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }
}
