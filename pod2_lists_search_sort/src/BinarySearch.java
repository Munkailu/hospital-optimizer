package pod2_lists_search_sort.src;
public class BinarySearch {

    public static int search(int[] numbers, int target) {

        if (numbers == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        if (!isSorted(numbers)) {
            throw new IllegalArgumentException(
                "Binary search requires a sorted array"
            );
        }

        int left = 0;
        int right = numbers.length - 1;

        while (left <= right) {

            int middle = left + (right - left) / 2;

            if (numbers[middle] == target) {
                return middle;
            }

            if (numbers[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }


    private static boolean isSorted(int[] numbers) {

        for (int i = 1; i < numbers.length; i++) {

            if (numbers[i] < numbers[i - 1]) {
                return false;
            }
        }

        return true;
    }
}