package pod4;

import java.util.ArrayList;
import java.util.List;


public class MultiBranchTree {

    private static final int MAX_KEYS = 3;

    private Node root;

    private static class Node {
        List<Integer> keys = new ArrayList<>();
        List<Node> children = new ArrayList<>();
        boolean isLeaf = true;
    }

    public MultiBranchTree() {
        root = new Node();
    }

    
    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(Node node, int key) {
        int i = 0;
        while (i < node.keys.size() && key > node.keys.get(i)) {
            i++;
        }
        if (i < node.keys.size() && node.keys.get(i) == key) {
            return true;
        }
        if (node.isLeaf) {
            return false;
        }
        return searchRecursive(node.children.get(i), key);
    }

    
    public void add(int key) {
        if (root.keys.size() == MAX_KEYS) {
            Node newRoot = new Node();
            newRoot.isLeaf = false;
            newRoot.children.add(root);
            splitChild(newRoot, 0);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    private void insertNonFull(Node node, int key) {
        int i = node.keys.size() - 1;

        if (node.isLeaf) {
            node.keys.add(0);
            while (i >= 0 && key < node.keys.get(i)) {
                node.keys.set(i + 1, node.keys.get(i));
                i--;
            }
            node.keys.set(i + 1, key);
        } else {
            while (i >= 0 && key < node.keys.get(i)) {
                i--;
            }
            i++;
            if (node.children.get(i).keys.size() == MAX_KEYS) {
                splitChild(node, i);
                if (key > node.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(node.children.get(i), key);
        }
    }

    
    private void splitChild(Node parent, int index) {
        Node fullChild = parent.children.get(index);
        Node newChild = new Node();
        newChild.isLeaf = fullChild.isLeaf;

        int midIndex = MAX_KEYS / 2;
        int midKey = fullChild.keys.get(midIndex);

        newChild.keys.addAll(fullChild.keys.subList(midIndex + 1, fullChild.keys.size()));
        fullChild.keys.subList(midIndex, fullChild.keys.size()).clear();

        if (!fullChild.isLeaf) {
            newChild.children.addAll(fullChild.children.subList(midIndex + 1, fullChild.children.size()));
            fullChild.children.subList(midIndex + 1, fullChild.children.size()).clear();
        }

        parent.children.add(index + 1, newChild);
        parent.keys.add(index, midKey);
    }

    
    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(Node node, List<Integer> result) {
        int i;
        for (i = 0; i < node.keys.size(); i++) {
            if (!node.isLeaf) {
                inorderRecursive(node.children.get(i), result);
            }
            result.add(node.keys.get(i));
        }
        if (!node.isLeaf) {
            inorderRecursive(node.children.get(i), result);
        }
    }

    public boolean isEmpty() {
        return root.keys.isEmpty();
    }
}
