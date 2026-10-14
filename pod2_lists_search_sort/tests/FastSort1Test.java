package pod2_lists_search_sort.tests;

import pod2_lists_search_sort.src.FastSort1;

public class FastSort1Test {

    public static void main(String[] args) {

        testNormalArray();
        testEmptyArray();
        testOneItem();
        testAlreadySorted();
        testReverseOrder();
        testDuplicates();
        testNullArray();

        System.out.println("All Fast Sort #1 tests passed!");
    }

    private static void testNormalArray() {
        int[] numbers = {38, 27, 43, 3, 9, 82, 10};

        FastSort1.sort(numbers);

        int[] expected = {3, 9, 10, 27, 38, 43, 82};

        check(numbers, expected, "normal array");
    }

    private static void testEmptyArray() {
        int[] numbers = {};

        FastSort1.sort(numbers);

        int[] expected = {};

        check(numbers, expected, "empty array");
    }

    private static void testOneItem() {
        int[] numbers = {50};

        FastSort1.sort(numbers);

        int[] expected = {50};

        check(numbers, expected, "one item");
    }

    private static void testAlreadySorted() {
        int[] numbers = {1, 2, 3, 4, 5};

        FastSort1.sort(numbers);

        int[] expected = {1, 2, 3, 4, 5};

        check(numbers, expected, "already sorted");
    }

    private static void testReverseOrder() {
        int[] numbers = {5, 4, 3, 2, 1};

        FastSort1.sort(numbers);

        int[] expected = {1, 2, 3, 4, 5};

        check(numbers, expected, "reverse order");
    }

    private static void testDuplicates() {
        int[] numbers = {5, 2, 5, 1, 2, 5};

        FastSort1.sort(numbers);

        int[] expected = {1, 2, 2, 5, 5, 5};

        check(numbers, expected, "duplicates");
    }

    private static void testNullArray() {
        FastSort1.sort(null);

        System.out.println("Null array handled correctly");
    }

    private static void check(
            int[] actual,
            int[] expected,
            String testName) {

        if (actual.length != expected.length) {
            throw new AssertionError(
                    testName + " failed: different lengths");
        }

        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError(
                        testName + " failed at index " + i);
            }
        }

        System.out.println(testName + " passed");
    }
}