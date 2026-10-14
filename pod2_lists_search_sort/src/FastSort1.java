package pod2_lists_search_sort.src;

public class FastSort1 {

    public static void sort(int[] numbers) {
        if (numbers == null || numbers.length < 2) {
            return;
        }

        int[] temp = new int[numbers.length];
        mergeSort(numbers, temp, 0, numbers.length - 1);
    }

    private static void mergeSort(int[] numbers, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(numbers, temp, left, middle);
        mergeSort(numbers, temp, middle + 1, right);

        merge(numbers, temp, left, middle, right);
    }

    private static void merge(
            int[] numbers,
            int[] temp,
            int left,
            int middle,
            int right) {

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (numbers[i] <= numbers[j]) {
                temp[k] = numbers[i];
                i++;
            } else {
                temp[k] = numbers[j];
                j++;
            }

            k++;
        }

        while (i <= middle) {
            temp[k] = numbers[i];
            i++;
            k++;
        }

        while (j <= right) {
            temp[k] = numbers[j];
            j++;
            k++;
        }

        for (int index = left; index <= right; index++) {
            numbers[index] = temp[index];
        }
    }
}