package pod2_lists_search_sort.algorithms;

import pod2_lists_search_sort.structures.CustomArrayList;
import java.util.Comparator;

public class SearchEngine {

    public static <T> int linearSearch(CustomArrayList<T> list, T target, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            if (comparator.compare(list.get(i), target) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static <T> int binarySearch(CustomArrayList<T> list, T target, Comparator<T> comparator) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparator.compare(list.get(mid), target);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
