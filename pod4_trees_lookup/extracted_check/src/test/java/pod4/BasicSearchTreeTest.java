package pod4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BasicSearchTreeTest {

    @Test
    void searchOnEmptyTreeReturnsFalse() {
        BasicSearchTree tree = new BasicSearchTree();
        assertFalse(tree.search(5));
        assertTrue(tree.isEmpty());
    }

    @Test
    void addAndSearchSingleItem() {
        BasicSearchTree tree = new BasicSearchTree();
        tree.add(10);
        assertTrue(tree.search(10));
        assertFalse(tree.search(99));
    }

    @Test
    void inorderReturnsSortedList() {
        BasicSearchTree tree = new BasicSearchTree();
        int[] values = {5, 10, 3, 8, 1};
        for (int v : values) tree.add(v);

        List<Integer> result = tree.inorder();
        assertEquals(List.of(1, 3, 5, 8, 10), result);
    }

    @Test
    void duplicateInsertDoesNotBreakTree() {
        BasicSearchTree tree = new BasicSearchTree();
        tree.add(5);
        tree.add(5);
        assertEquals(List.of(5), tree.inorder());
    }
}
