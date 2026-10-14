package pod2_lists_search_sort.tests;

import pod2_lists_search_sort.src.LinkedList;

public class LinkedListTest {

    public static void main(String[] args) {

        System.out.println("=== Linked List Tests ===");

        LinkedList list = new LinkedList();

        System.out.println("\nTest 1: Empty list");
        System.out.println("Is empty? " + list.isEmpty());
        System.out.println("Size: " + list.size());

        System.out.println("\nTest 2: Add to front");
        list.addFirst(20);
        list.addFirst(10);
        System.out.println(list);
        System.out.println("Expected: 10 -> 20");

        System.out.println("\nTest 3: Add to back");
        list.addLast(30);
        list.addLast(40);
        System.out.println(list);
        System.out.println("Expected: 10 -> 20 -> 30 -> 40");

        System.out.println("\nTest 4: Insert in the middle");
        list.insert(2, 25);
        System.out.println(list);
        System.out.println("Expected: 10 -> 20 -> 25 -> 30 -> 40");

        System.out.println("\nTest 5: Get item");
        System.out.println("Item at index 2: " + list.get(2));
        System.out.println("Expected: 25");

        System.out.println("\nTest 6: Remove item");
        int removed = list.remove(2);
        System.out.println("Removed: " + removed);
        System.out.println(list);
        System.out.println("Expected: 10 -> 20 -> 30 -> 40");

        System.out.println("\nTest 7: Remove first");
        removed = list.removeFirst();
        System.out.println("Removed: " + removed);
        System.out.println(list);
        System.out.println("Expected: 20 -> 30 -> 40");

        System.out.println("\nTest 8: Walk through");
        System.out.print("Walking through list: ");
        list.walkThrough();

        System.out.println("\nTest 9: Size");
        System.out.println("Size: " + list.size());
        System.out.println("Expected: 3");

        System.out.println("\nTest 10: Invalid index");

        try {
            list.get(100);
            System.out.println("FAILED: Invalid index was accepted.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASSED: Invalid index was rejected.");
        }

        System.out.println("\nTest 11: Remove everything");

        list.removeFirst();
        list.removeFirst();
        list.removeFirst();

        System.out.println("List: " + list);
        System.out.println("Is empty? " + list.isEmpty());
        System.out.println("Expected: true");

        System.out.println("\nTest 12: Remove from empty list");

        try {
            list.removeFirst();
            System.out.println("FAILED: Empty list allowed removal.");
        } catch (IllegalStateException e) {
            System.out.println("PASSED: Empty list rejected removal.");
        }

        System.out.println("\n=== All Linked List Tests Completed ===");
    }
}