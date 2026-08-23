package pod2_lists_search_sort.algorithms;

import pod2_lists_search_sort.structures.CustomArrayList;
import java.util.Comparator;

public class SortEngine {

    public static <T> void selectionSort(CustomArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIdx)) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(list, i, minIdx);
            }
        }
    }

    public static <T> void insertionSort(CustomArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            T key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    public static <T> void mergeSort(CustomArrayList<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) return;
        CustomArrayList<T> aux = new CustomArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) aux.add(null);
        mergeSortInternal(list, aux, 0, list.size() - 1, comparator);
    }

    private static <T> void mergeSortInternal(CustomArrayList<T> list, CustomArrayList<T> aux, int low, int high, Comparator<T> comp) {
        if (low >= high) return;
        int mid = low + (high - low) / 2;
        mergeSortInternal(list, aux, low, mid, comp);
        mergeSortInternal(list, aux, mid + 1, high, comp);
        merge(list, aux, low, mid, high, comp);
    }

    private static <T> void merge(CustomArrayList<T> list, CustomArrayList<T> aux, int low, int mid, int high, Comparator<T> comp) {
        for (int k = low; k <= high; k++) aux.set(k, list.get(k));
        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) list.set(k, aux.get(j++));
            else if (j > high) list.set(k, aux.get(i++));
            else if (comp.compare(aux.get(j), aux.get(i)) < 0) list.set(k, aux.get(j++));
            else list.set(k, aux.get(i++));
        }
    }

    public static <T> void quickSort(CustomArrayList<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) return;
        quickSortInternal(list, 0, list.size() - 1, comparator);
    }

    private static <T> void quickSortInternal(CustomArrayList<T> list, int low, int high, Comparator<T> comp) {
        if (low < high) {
            int pIndex = partition(list, low, high, comp);
            quickSortInternal(list, low, pIndex - 1, comp);
            quickSortInternal(list, pIndex + 1, high, comp);
        }
    }

    private static <T> int partition(CustomArrayList<T> list, int low, int high, Comparator<T> comp) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(list.get(j), pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    private static void swap(CustomArrayList<?> list, int i, int j) {
        swapHelper(list, i, j);
    }

    @SuppressWarnings("unchecked")
    private static <E> void swapHelper(CustomArrayList<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
