package pod2_lists_search_sort.tests;
import pod2_lists_search_sort.src.BinarySearch;
public class BinarySearchTest {

    public static void main(String[] args) {

        testFindExistingValue();
        testValueNotFound();
        testEmptyArray();
        testOneItem();
        testRepeatedValues();
        testUnsortedArray();
        testNullArray();

        System.out.println("All Binary Search tests passed!");
    }

    private static void testFindExistingValue() {

        int[] numbers = {10, 20, 30, 40, 50, 60, 70};

        int result = BinarySearch.search(numbers, 60);

        if (result != 5) {
            throw new AssertionError("Expected index 5 but got " + result);
        }
    }

    private static void testValueNotFound() {

        int[] numbers = {10, 20, 30, 40, 50};

        int result = BinarySearch.search(numbers, 99);

        if (result != -1) {
            throw new AssertionError("Expected -1 but got " + result);
        }
    }

    private static void testEmptyArray() {

        int[] numbers = {};

        int result = BinarySearch.search(numbers, 10);

        if (result != -1) {
            throw new AssertionError("Expected -1 for empty array");
        }
    }

    private static void testOneItem() {

        int[] numbers = {50};

        int result = BinarySearch.search(numbers, 50);

        if (result != 0) {
            throw new AssertionError("Expected index 0");
        }
    }

    private static void testRepeatedValues() {

        int[] numbers = {10, 20, 20, 20, 30};

        int result = BinarySearch.search(numbers, 20);

        if (result == -1) {
            throw new AssertionError("Expected to find 20");
        }
    }

    private static void testUnsortedArray() {

        int[] numbers = {10, 50, 20, 40, 30};

        try {
            BinarySearch.search(numbers, 40);

            throw new AssertionError(
                "Expected an error for an unsorted array"
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Correctly rejected unsorted array: "
                + e.getMessage()
            );
        }
    }

    private static void testNullArray() {

        try {
            BinarySearch.search(null, 10);

            throw new AssertionError(
                "Expected an error for null array"
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                "Correctly rejected null array: "
                + e.getMessage()
            );
        }
    }
}