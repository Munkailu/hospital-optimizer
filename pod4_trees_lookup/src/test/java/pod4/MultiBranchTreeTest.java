package pod4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MultiBranchTreeTest {

    @Test
    void searchOnEmptyTreeReturnsFalse() {
        MultiBranchTree tree = new MultiBranchTree();
        assertFalse(tree.search(5));
        assertTrue(tree.isEmpty());
    }

    @Test
    void addAndSearchSingleItem() {
        MultiBranchTree tree = new MultiBranchTree();
        tree.add(10);
        assertTrue(tree.search(10));
        assertFalse(tree.search(99));
    }

    @Test
    void inorderReturnsSortedListAfterManyInserts() {
        MultiBranchTree tree = new MultiBranchTree();
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int v : values) tree.add(v);

        assertEquals(List.of(5, 6, 7, 10, 12, 17, 20, 30), tree.inorder());
    }

    @Test
    void splitHappensWhenNodeOverflows() {
        MultiBranchTree tree = new MultiBranchTree();
        for (int i = 1; i <= 10; i++) tree.add(i);

        for (int i = 1; i <= 10; i++) {
            assertTrue(tree.search(i), "Expected to find " + i + " after splits");
        }
    }

    @Test
    void duplicateInsertStillFindable() {
        MultiBranchTree tree = new MultiBranchTree();
        tree.add(5);
        tree.add(5);
        assertTrue(tree.search(5));
    }
}
