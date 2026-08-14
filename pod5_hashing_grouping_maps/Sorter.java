// Owner: Ibrahim Hidayat (Pod 5)
// This contract lets the speed-testing tool work with any sorting algorithm,
// whether mine or a teammate's, without changing the testing code.
public interface Sorter {
    void sort(int[] arr);
    String getName();
}
