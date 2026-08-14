package pod4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SelfBalancingTreeTest {

    @Test
    void searchOnEmptyTreeReturnsFalse() {
        SelfBalancingTree tree = new SelfBalancingTree();
        assertFalse(tree.search(5));
        assertTrue(tree.isEmpty());
    }

    @Test
    void addAndSearchSingleItem() {
        SelfBalancingTree tree = new SelfBalancingTree();
        tree.add(10);
        assertTrue(tree.search(10));
        assertFalse(tree.search(99));
    }

    @Test
    void inorderReturnsSortedList() {
        SelfBalancingTree tree = new SelfBalancingTree();
        int[] values = {5, 10, 3, 8, 1};
        for (int v : values) tree.add(v);
        assertEquals(List.of(1, 3, 5, 8, 10), tree.inorder());
    }

    @Test
    void treeStaysBalancedWithSequentialInserts() {
        SelfBalancingTree tree = new SelfBalancingTree();
        for (int i = 1; i <= 7; i++) tree.add(i);

        assertEquals(3, tree.treeHeight());
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7), tree.inorder());
    }

    @Test
    void duplicateInsertDoesNotBreakTree() {
        SelfBalancingTree tree = new SelfBalancingTree();
        tree.add(5);
        tree.add(5);
        assertEquals(List.of(5), tree.inorder());
    }
}