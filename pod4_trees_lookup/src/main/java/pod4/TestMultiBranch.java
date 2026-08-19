package pod4;

public class TestMultiBranch {
    public static void main(String[] args) {
        MultiBranchTree tree = new MultiBranchTree();

        // MAX_KEYS is 3, so a single node can hold up to 3 keys before it must split.
        tree.add(10);
        tree.add(20);
        tree.add(30);
        System.out.println("After inserting 10, 20, 30 (node full, no split yet): " + tree.inorder());

        // Inserting a 4th key forces a split: the root is already full,
        // so it splits BEFORE this key is placed.
        tree.add(40);
        System.out.println("After inserting 40 (this insert triggers the split): " + tree.inorder());

        // --- Search example ---
        int target = 30;
        System.out.println("Searching for " + target + ": " + (tree.search(target) ? "FOUND" : "NOT FOUND"));

        int missing = 99;
        System.out.println("Searching for " + missing + ": " + (tree.search(missing) ? "FOUND" : "NOT FOUND"));
    }
}