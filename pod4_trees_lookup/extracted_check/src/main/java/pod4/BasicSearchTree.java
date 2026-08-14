package pod4;

public class BasicSearchTree {

    private Node root;

    private static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    public void add(int key) {
        root = addRecursive(root, key);
    }

    private Node addRecursive(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = addRecursive(node.left, key);
        } else if (key > node.key) {
            node.right = addRecursive(node.right, key);
        }
        return node;
    }

    public boolean search(int key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(Node node, int key) {
        if (node == null) return false;
        if (key == node.key) return true;
        return key < node.key
                ? searchRecursive(node.left, key)
                : searchRecursive(node.right, key);
    }

    public java.util.List<Integer> inorder() {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        inorderRecursive(root, result);
        return result;
    }

    private void inorderRecursive(Node node, java.util.List<Integer> result) {
        if (node == null) return;
        inorderRecursive(node.left, result);
        result.add(node.key);
        inorderRecursive(node.right, result);
    }

    public boolean isEmpty() {
        return root == null;
    }
}