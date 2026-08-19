package pod4;

public class TestSelfBalancing {
    public static void main(String[] args) {
        SelfBalancingTree tree = new SelfBalancingTree();

        // Insert 30, then 20, then 10.
        // Before rebalancing, this would form a left-leaning chain: 30 -> 20 -> 10
        // The tree should automatically rotate to stay balanced.
        tree.add(30);
        tree.add(20);
        tree.add(10);

        System.out.println("In-order after inserts: " + tree.inorder());
        System.out.println("Tree height: " + tree.treeHeight());
        // Expected: In-order [10, 20, 30], height 2 (not 3)
        // Height 2 proves the rotation happened -- a straight chain of 3 nodes
        // would have height 3 if no rebalancing occurred.
    }
}